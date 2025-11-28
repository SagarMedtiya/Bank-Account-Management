package org.keen.bank.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.apache.camel.Header;

@Named("jsonUtil")
@ApplicationScoped
public class JsonUtil {
    public String errorResponse(@Header("errorMessage") String message) {
        return "{\"success\": false, \"message\": \"" + message + "\"}";
    }
}
