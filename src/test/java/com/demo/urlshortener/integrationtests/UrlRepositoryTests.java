package com.demo.urlshortener.integrationtests;

import com.demo.urlshortener.UrlRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
public class UrlRepositoryTests {

    @Autowired
    private UrlRepository urlRepository;

    @Test
    public void testAddUrl() {
        String longUrl = "http://example.com";
        String id = "abc123";
        urlRepository.addUrl(longUrl, id);
        String result = urlRepository.getLongUrl(id);
        assertEquals(longUrl, result);
    }

    @Test
    public void testGetLongUrl() {
        String id = "abc123";
        String longUrl = "http://example.com";
        urlRepository.addUrl(longUrl, id);
        String result = urlRepository.getLongUrl(id);
        assertEquals(longUrl, result);
    }

    @Test
    public void testCheckIfUrlAdded() {
        String id = "abc123";
        String longUrl = "http://example.com";
        urlRepository.addUrl(longUrl, id);
        String result = urlRepository.checkIfUrlAdded(longUrl);
        assertEquals(id, result);
    }

    @Test
    public void testIsIdUnique() {
        String id = "abc123";
        String longUrl = "http://example.com";
        urlRepository.addUrl(longUrl, id);
        boolean isUnique = urlRepository.isIdUnique(id);
        assertFalse(isUnique);
    }
}