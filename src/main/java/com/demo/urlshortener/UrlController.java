package com.demo.urlshortener;

import com.demo.urlshortener.interfaces.IUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
@RequestMapping("/api")

public class UrlController {
    private final IUrlService urlService;

    @Autowired
    public UrlController(IUrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{shortUrlId}")
    public RedirectView redirectToUrl(@PathVariable int shortUrlId) {
        try{
            String longUrl = urlService.getLongUrl(shortUrlId);
            if(longUrl==null){
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "URL not found"
                );
            }
            return new RedirectView(longUrl);
        }
        catch(Exception ex){
            return new RedirectView("/error-page");
        }
    }

    @PostMapping("/addUrl")
    public ResponseEntity<String> addUrl(@RequestBody Map<String, String> body) {
        String longUrl = body.get("longUrl");
        if (longUrl == null) {
            return ResponseEntity.badRequest().body("Missing longUrl");
        }

        try{
            String shortUrl = urlService.addUrl(longUrl);
            return ResponseEntity.ok(shortUrl);
        }
        catch (Exception ex){
            return ResponseEntity.status(500).body("Could not generate short URL");
        }
    }
}
