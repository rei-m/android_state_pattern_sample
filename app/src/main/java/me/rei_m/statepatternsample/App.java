package me.rei_m.statepatternsample;

import android.app.Application;

import me.rei_m.statepatternsample.manager.ModelLocator;
import me.rei_m.statepatternsample.model.AtndModel;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ModelLocator.register(ModelLocator.Tag.ATND, new AtndModel());
    }
}
