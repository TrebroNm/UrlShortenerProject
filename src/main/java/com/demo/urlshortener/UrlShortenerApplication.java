package com.demo.urlshortener;

import com.demo.urlshortener.interfaces.IUrlRepository;
import com.demo.urlshortener.interfaces.IUrlService;
import org.jooq.DSLContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UrlShortenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlShortenerApplication.class, args);
    }
}
