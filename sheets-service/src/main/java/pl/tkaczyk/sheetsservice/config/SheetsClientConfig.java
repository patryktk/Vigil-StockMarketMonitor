package pl.tkaczyk.sheetsservice.config;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pl.tkaczyk.sheetsservice.GoogleSheetsProperties;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SheetsClientConfig {

    private final GoogleSheetsProperties googleSheetsProperties;
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();


    @Value("${application.google.serivce-account-key}")
    private static String serviceAccountKey;

    private static final List<String> SCOPES =
            Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
//
//    @Bean
//    public Sheets googleSheetsClient() throws IOException, GeneralSecurityException {
//        InputStream credentialsStream = new ByteArrayInputStream(Base64.getDecoder().decode(serviceAccountKey));
//
//        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
//                .createScoped(Collections.singletonList(SheetsScopes.SPREADSHEETS));
//
//        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);
//
//        return new Sheets.Builder(
//                GoogleNetHttpTransport.newTrustedTransport(),
//                JacksonFactory.getDefaultInstance(),
//                requestInitializer
//        )
//                .setApplicationName(googleSheetsProperties.getApplicationName()) // Pobierane z properties
//                .build();
//    }


    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException{
        InputStream in = new ByteArrayInputStream(Base64.getDecoder().decode(serviceAccountKey));
        if(in == null){
            throw new FileNotFoundException("Service account key not found");
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new))

    }
}
