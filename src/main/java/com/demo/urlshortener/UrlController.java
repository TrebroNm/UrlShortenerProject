package com.demo.urlshortener;

import com.demo.urlshortener.interfaces.IUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;


@RestController
@RequestMapping("/api")
public class UrlController {
    private final IUrlService urlService;

    @Autowired
    public UrlController(IUrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{shortUrlId}")
    public ResponseEntity<Void> redirectToUrl(@PathVariable String shortUrlId) {
        String longUrl = urlService.getLongUrl(shortUrlId);
        if(longUrl==null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "URL not found"
            );
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(longUrl));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @PostMapping("/addUrl")
    public ResponseEntity<String> addUrl(@RequestParam String longUrl) {
        if (longUrl == null) {
            return ResponseEntity.badRequest().body("Missing longUrl");
        }
        String shortUrl = urlService.addUrl(longUrl);
        return ResponseEntity.ok(shortUrl);
    }
}
