package org.greeneyed.hotelf;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.hotelbeds.distribution.hotel_api_sdk.HotelApiClient;

@SpringBootTest
@ActiveProfiles("test")
class ApplicationTest {

    @MockBean
    HotelApiClient hotelApiClient;

    @Test
    void contextLoads() {
    }

}
