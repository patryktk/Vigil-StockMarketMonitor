package pl.tkaczyk.scraperservice.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import pl.tkaczyk.scraperservice.exception.HtmlFetchException;
import pl.tkaczyk.scraperservice.service.HtmlDocumentFetcher;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;

@Service
@Slf4j
public class JsoupHtmlFetcher implements HtmlDocumentFetcher {

    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36";

    @Override
    @CircuitBreaker(name = "jsoup")
    @Retry(name = "jsoup", fallbackMethod = "fallback")
    @RateLimiter(name = "jsoup")
    public Document getDocument(String url) {
        try {
            return Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .timeout(5000)
                    .sslContext(createCustomSSLContext())
                    .get();
        } catch (IOException e) {
            log.error("Error while fetching {}", url, e);
            throw new HtmlFetchException("Error while fetching: " + url, e);
        }
    }

    private Document fallback(String url, Exception e) {
        log.error("All retries exhausted for {}", url, e);
        throw new HtmlFetchException("Unable to fetch document after retires: " + url, e);
    }

    /**
     * SSLContext for unsecure sites
     *
     * @return SSLContext
     */
    private SSLContext insecureSslContext() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            return sslContext;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create insecure SSL context", e);
        }
    }


    /**
     * SSLContext for secure sites
     *
     * @return SSLContext
     */
    private SSLContext createCustomSSLContext() {
        try {
            // 1. Pobierz domyślnego menedżera Javy
            TrustManagerFactory defaultTmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            defaultTmf.init((KeyStore) null); // Loading default trust store
            X509TrustManager defaultTm = getX509TrustManager(defaultTmf);

            // 2. Przygotuj nasz pusty magazyn na własne certyfikaty
            KeyStore customKs = KeyStore.getInstance(KeyStore.getDefaultType());
            customKs.load(null, null);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            // 3. Dynamiczne skanowanie folderu certs/ (ładuje pliki .pem i .crt)
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath*:certs/*.*"); // Szuka wszystkich plików w folderze certs

            int loadedCerts = 0;
            for (Resource resource : resources) {
                String filename = resource.getFilename();
                // Opcjonalnie: upewnij się, że ładujesz tylko właściwe formaty
                if (filename != null && (filename.endsWith(".pem") || filename.endsWith(".crt"))) {
                    try (InputStream is = resource.getInputStream()) {
                        X509Certificate cert = (X509Certificate) cf.generateCertificate(is);
                        // Używamy nazwy pliku jako unikalnego aliasu w KeyStore
                        customKs.setCertificateEntry(filename, cert);
                        loadedCerts++;
                        log.info("Pomyślnie załadowano certyfikat: {}", filename);
                    } catch (Exception e) {
                        log.warn("Nie udało się załadować certyfikatu: {}", filename, e);
                    }
                }
            }

            if (loadedCerts == 0) {
                log.info("Nie znaleziono żadnych własnych certyfikatów w folderze resources/certs/");
            }

            // 4. Zainicjuj menedżera naszym załadowanym magazynem
            TrustManagerFactory customTmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            customTmf.init(customKs);
            X509TrustManager customTm = getX509TrustManager(customTmf);


            // 5. Stwórz menedżera kompozytowego (weryfikacja domyślna -> weryfikacja własna)
            X509TrustManager compositeTm = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return defaultTm.getAcceptedIssuers();
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    defaultTm.checkClientTrusted(chain, authType);
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    try {
                        // Najpierw spróbuj zweryfikować normalnie (np. dla google.com)
                        defaultTm.checkServerTrusted(chain, authType);
                    } catch (CertificateException e) {
                        // Jeśli weryfikacja zawiedzie, spróbuj użyć naszego pliku .pem (dla strefainwestorow.pl)
                        customTm.checkServerTrusted(chain, authType);
                    }
                }
            };

            // 6. Zwróć gotowy kontekst
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{compositeTm}, null);
            return sslContext;

        } catch (Exception e) {
            log.error("Błąd podczas budowania kompozytowego SSLContext", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Pobiera X509TrustManager z TrustManagerFactory
     *
     * @param tmf TrustManagerFactory
     * @return X509TrustManager
     */
    private X509TrustManager getX509TrustManager(TrustManagerFactory tmf) {
        return Arrays.stream(tmf.getTrustManagers())
                .filter(tm -> tm instanceof X509TrustManager)
                .map(tm -> (X509TrustManager) tm)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Brak X509TrustManager"));
    }


}
