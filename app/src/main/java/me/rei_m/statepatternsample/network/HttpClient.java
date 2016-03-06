package me.rei_m.statepatternsample.network;

import okhttp3.OkHttpClient;

public enum HttpClient {

    INSTANCE;

    private final OkHttpClient instance = new OkHttpClient();

    public OkHttpClient get() {
        return instance;
    }
}
