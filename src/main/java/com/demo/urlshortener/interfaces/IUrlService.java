package com.demo.urlshortener.interfaces;

public interface IUrlService {
    String getLongUrl(int shortUrlId);
    String addUrl(String longUrl);

}
