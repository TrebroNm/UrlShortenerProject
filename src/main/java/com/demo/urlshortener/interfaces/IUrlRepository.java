package com.demo.urlshortener.interfaces;

public interface IUrlRepository {
    int addUrl(String longUrl);
    String getLongUrl(int shortUrlId);
}
