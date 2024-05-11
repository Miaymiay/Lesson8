package com.mirea.pershinadv.yandexdriver;

import android.app.Application;

import com.yandex.mapkit.MapKitFactory;

public class App extends Application {
    private final String MAPKIT_API_KEY = "891f81ec-e160-44ee-9e07-5ba7965b433b";
    @Override
    public void onCreate() {
        super.onCreate();
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
    }
}
