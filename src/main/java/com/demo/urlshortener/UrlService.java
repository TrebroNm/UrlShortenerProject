package com.demo.urlshortener;

import com.demo.urlshortener.interfaces.IUrlRepository;
import com.demo.urlshortener.interfaces.IUrlService;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UrlService implements IUrlService {

    private final IUrlRepository urlRepository;
    @Value("${app.baseUrl}")
    private String baseUrl;


    @Autowired
    public UrlService(IUrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String getLongUrl(String shortUrlId) throws DataAccessException {
        return urlRepository.getLongUrl(shortUrlId);
    }

    @Transactional
    public String addUrl(String longUrl) throws DataAccessException {
        //Check if url isn't already added
        String shortUrlId = urlRepository.checkIfUrlAdded(longUrl);


        //If it is not added, add it
        if (shortUrlId == null) {
            shortUrlId = GenerateUniqueStringId();
            urlRepository.addUrl(longUrl, shortUrlId);
        }

        //Constructs shortened url
        String shortUrl = baseUrl + "/" + shortUrlId;
        return shortUrl;
    }

    //Generates a random string and then checks if it isn't already in the database
    //If it is in the database, loops and generates a new one
    protected String GenerateUniqueStringId() throws DataAccessException {
        String id;
        do {
            id = RandomStringGenerator.generate(10);
        } while (!urlRepository.isIdUnique(id));
        return id;
    }
}
