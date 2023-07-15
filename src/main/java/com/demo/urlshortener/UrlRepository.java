package com.demo.urlshortener;
import com.demo.urlshortener.interfaces.IUrlRepository;
import nu.studer.sample.tables.Urls;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UrlRepository implements IUrlRepository {

    private final DSLContext dslContext;
    private final Urls urls = Urls.URLS;

    @Autowired
    public UrlRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void addUrl(String longUrl, String id) throws DataAccessException{
        this.dslContext.insertInto(urls)
                .columns(urls.LONG_URL, urls.ID)
                .values(longUrl, id).execute();
    }

    public String getLongUrl(String shortUrlId) throws DataAccessException{
        return this.dslContext.select(urls.LONG_URL)
                .from(urls)
                .where(urls.ID.eq(shortUrlId))
                .fetchOneInto(String.class);
    }

    public String checkIfUrlAdded(String longUrl) throws DataAccessException{
        return this.dslContext.select(urls.ID)
                .from(urls)
                .where(urls.LONG_URL.eq(longUrl))
                .fetchAnyInto(String.class);
    }

    public boolean isIdUnique(String id) throws DataAccessException{
        return this.dslContext.selectFrom(urls)
                .where(urls.ID.eq(id))
                .fetchOne() == null;
    }
}