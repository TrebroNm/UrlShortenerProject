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

    // TODO mojsej, shortUrl by bolo asi lepsie nejaky kratny generovany string ako "int"
    public String getLongUrl(int shortUrlId) {
        try {
            return urlRepository.getLongUrl(shortUrlId);
        } catch (DataAccessException ex) {
            throw new DataAccessException("Error occurred while retrieving long URL from the database", ex);
        }
    }

    public String addUrl(String longUrl) {
        try {
            // TODO mojsej, najskor treba ceknut ci nahodou uz takato URL nie je skratena a v DB sa uz nenachadza. Ak hej vratim existujucu.
            int shortUrlId = urlRepository.addUrl(longUrl);
            return baseUrl + "/" + shortUrlId;
            // TODO mojsej DataAccessException je RuntimeException, tento pattern ze sa chyti DataAccessException a vyhodi sa RuntimeException nie je uplne ok. Exception handling by som robil na tej najvyssej urovni,
            // takto to postupne prebuble az hore a tam sa to chyti a vrati ako HTTP 500. Inak Spring exceptiony odchyti na controlleroch aj sam a vrati HTTP 500.
        } catch (DataAccessException ex) {
            throw new RuntimeException("Error occurred while adding URL to the database", ex);
        }
    }
}
