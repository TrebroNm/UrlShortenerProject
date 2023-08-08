package com.demo.urlshortener;

import com.demo.urlshortener.interfaces.IUrlRepository;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.demo.urlshortener.Tables.URLS;

@Repository
public class UrlRepository implements IUrlRepository {

    private final DSLContext jooq;

    @Autowired
    public UrlRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    public void addUrl(String longUrl, String id) throws DataAccessException {
        jooq.insertInto(URLS, URLS.LONG_URL, URLS.ID)
                .values(longUrl, id).execute();
    }

    public String getLongUrl(String shortUrlId) throws DataAccessException {
        return jooq.select(URLS.LONG_URL)
                .from(URLS)
                .where(URLS.ID.eq(shortUrlId))
                .fetchOneInto(String.class);
    }

    public String checkIfUrlAdded(String longUrl) throws DataAccessException {
        return jooq.select(URLS.ID)
                .from(URLS)
                .where(URLS.LONG_URL.eq(longUrl))
                .fetchAnyInto(String.class);
    }

    public boolean isIdUnique(String id) throws DataAccessException {
        //return jooq.select(DSL.exists(DSL.selectFrom(URLS).where(URLS.ID.eq(id))));
        return jooq.selectFrom(URLS)
                .where(URLS.ID.eq(id))
                .fetchOne() == null;
    }
}