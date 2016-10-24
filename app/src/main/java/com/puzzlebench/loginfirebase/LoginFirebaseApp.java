package com.puzzlebench.loginfirebase;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

/**
 * Created by andresdavid on 21/10/16.
 */
public class LoginFirebaseApp extends Application{

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "Lg5m8MylxUwQeVdKMyhqRsNjF";
    private static final String TWITTER_SECRET = "o1d4mhMC0tzWYPYDgSjGZQ5HrcH1NWRSMfjyaGkMy1yr6P3mz7";

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
