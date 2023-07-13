package com.demo.urlshortener;
import com.demo.urlshortener.interfaces.IUrlRepository;
import com.demo.urlshortener.interfaces.IUrlService;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UrlService implements IUrlService {

    private final IUrlRepository urlRepository;
    @Value("${app.baseUrl}")
    private String baseUrl;


    @Autowired
    public UrlService(IUrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String getLongUrl(int shortUrlId) {
        try{
            return urlRepository.getLongUrl(shortUrlId);
        }catch (DataAccessException ex) {
            throw new DataAccessException("Error occurred while retrieving long URL from the database", ex);
        }
    }

    public String addUrl(String longUrl) {
        try{
            int shortUrlId = urlRepository.addUrl(longUrl);
            String shortUrl = baseUrl + "/" + shortUrlId;
            return shortUrl;
        }catch (DataAccessException ex) {
            throw new RuntimeException("Error occurred while adding URL to the database", ex);
        }
    }
}
