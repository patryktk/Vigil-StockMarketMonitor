package pl.tkaczyk.scraperservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private Instant timestamp;
    private String message;
    private String errorType;
    private int statusCode;
    private String serviceName;
    private String path;
    private List<String> errors;
}
