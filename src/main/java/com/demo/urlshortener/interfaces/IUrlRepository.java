package com.demo.urlshortener.interfaces;

public interface IUrlRepository {
    void addUrl(String longUrl, String id);
    String getLongUrl(String shortUrlId);
    String checkIfUrlAdded(String longUrl);
    boolean isIdUnique(String id);
}
