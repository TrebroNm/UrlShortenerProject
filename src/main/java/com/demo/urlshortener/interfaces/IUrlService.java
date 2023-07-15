package com.demo.urlshortener.interfaces;

public interface IUrlService {
    String getLongUrl(String shortUrlId);
    String addUrl(String longUrl);
}
