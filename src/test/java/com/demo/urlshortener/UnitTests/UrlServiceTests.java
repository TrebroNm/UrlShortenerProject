package com.demo.urlshortener.UnitTests;


import com.demo.urlshortener.RandomStringGenerator;
import com.demo.urlshortener.UrlService;
import com.demo.urlshortener.interfaces.IUrlRepository;
import org.jooq.exception.DataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UrlServiceTests {

    @Mock
    private IUrlRepository urlRepository;

    @InjectMocks
    private UrlService urlService;

    @Value("${app.baseUrl}")
    private String baseUrl;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(urlService, "baseUrl", baseUrl);
    }

    @Test
    public void testGetLongUrl() {
        String shortUrlId = "abc123";
        String longUrl = "http://example.com";
        when(urlRepository.getLongUrl(shortUrlId)).thenReturn(longUrl);

        String result = urlService.getLongUrl(shortUrlId);

        assertEquals(longUrl, result);
    }

    @Test
    public void testAddUrl() throws DataAccessException {
        String shortUrlId = "abc123";
        String longUrl = "http://example.com";
        when(urlRepository.checkIfUrlAdded(longUrl)).thenReturn(null);
        when(urlRepository.isIdUnique(anyString())).thenReturn(true);
        doNothing().when(urlRepository).addUrl(longUrl, shortUrlId);

        try (MockedStatic<RandomStringGenerator> mocked = mockStatic(RandomStringGenerator.class)) {
            mocked.when(() -> RandomStringGenerator.generate(anyInt())).thenReturn(shortUrlId);

            String result = urlService.addUrl(longUrl);

            assertEquals(baseUrl + "/" + shortUrlId, result);
            verify(urlRepository, times(1)).checkIfUrlAdded(longUrl);
            verify(urlRepository, times(1)).addUrl(longUrl, shortUrlId);
        }
    }

    @Test
    public void testAddUrlAlreadyExists() throws DataAccessException {
        String shortUrlId = "abc123";
        String longUrl = "http://example.com";
        when(urlRepository.checkIfUrlAdded(longUrl)).thenReturn(shortUrlId);

        String result = urlService.addUrl(longUrl);

        assertEquals(baseUrl + "/" + shortUrlId, result);
        verify(urlRepository, times(1)).checkIfUrlAdded(longUrl);
        verify(urlRepository, never()).addUrl(longUrl, shortUrlId);
    }
}
