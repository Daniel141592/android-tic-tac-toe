package com.daniel.tic_tac_toe;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Simple implementation of CookieJar without use persistence storage cause we don't need it.
 */
public class MyCookieJar implements CookieJar {
    private List<Cookie> cookies;

    @Override
    public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies) {
        this.cookies = cookies;
    }

    @NonNull
    @Override
    public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
        if (cookies != null)
            return cookies;
        return new ArrayList<>();
    }
}