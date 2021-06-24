package org.greeneyed.hotelf.configuration;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.annotation.Validated;

import com.hotelbeds.distribution.hotel_api_sdk.HotelApiClient;
import com.hotelbeds.distribution.hotel_api_sdk.types.HotelApiSDKException;
import com.hotelbeds.hotelapimodel.auto.messages.StatusRS;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Configuration
@ConfigurationProperties(prefix = "hotelbeds")
@Validated
@Data
@Slf4j
@Profile("!test")
public class HotelAPIClientConfiguration {

    @NotBlank
    private String apiKey;

    @NotBlank
    private String sharedSecret;

    @Bean
    public HotelApiClient hotelApiClient() {
        log.info("Initialising HotelApiClient...");
        HotelApiClient apiClient = new HotelApiClient(apiKey, sharedSecret);
        apiClient.setReadTimeout(40_000);
        apiClient.setDefaultLanguage("ENG");
        apiClient.init();
        try {
            StatusRS statusRS = apiClient.status();
            log.info("HotelAPI status: {} ", statusRS.getStatus());
        } catch (HotelApiSDKException e) {
            log.error("Error querying HotelAPI status: {}", e.getMessage());
        }
        return apiClient;
    }
}
