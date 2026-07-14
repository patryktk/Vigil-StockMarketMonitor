package pl.tkaczyk.sheetsservice.config;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class SheetsClientConfig {

    private final GoogleSheetsProperties googleSheetsProperties;

    @Bean
    public Sheets sheetsService() throws Exception {

        Resource resource = new ClassPathResource("credentials/service-account-key.json");

        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream())
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));

        return new Sheets.Builder(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials)
        ).setApplicationName(googleSheetsProperties.getApplicationName())
                .build();
    }
}
