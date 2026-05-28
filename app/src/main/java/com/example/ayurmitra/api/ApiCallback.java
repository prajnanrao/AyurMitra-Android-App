package com.example.ayurmitra.api;

public interface ApiCallback<T> {
    void onSuccess(T result);
    void onError(String errorMessage);
}
