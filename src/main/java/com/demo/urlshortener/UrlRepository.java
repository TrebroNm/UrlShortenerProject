package com.demo.urlshortener;
import com.demo.urlshortener.interfaces.IUrlRepository;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
public class UrlRepository implements IUrlRepository {

    private final DSLContext dslContext;

    @Autowired
    public UrlRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public int addUrl(String longUrl) throws DataAccessException{
        // TODO takto by sa nemal JOOQ pouzivat, nie je to typesafe.
        // Robi sa to tak ze si nehate vygenerovat Java metamodel z DB schemy. Je na to gradle plugin, vid https://github.com/etiennestuder/gradle-jooq-plugin
        // Nasledne sa neodkazujete cez stringove nazvy tabuliek a stlpcov ale cez vygenerovany java model
        Result<Record> result = this.dslContext.insertInto(table("urls"))
                    .columns(field("long_url"))
                    .values(longUrl)
                    .returning(field("id"))  // Return Generated ID
                    .fetch();

        return result.get(0).getValue(field("id", Integer.class));
    }

    public String getLongUrl(int shortUrlId) throws DataAccessException{
        return this.dslContext.select(field("long_url"))
                    .from(table("urls"))
                    .where(field("id").eq(shortUrlId))
                    .fetchOneInto(String.class);
    }
}