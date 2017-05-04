package com.example.a0stjal24.pointsofinterestapplication;

import android.os.Bundle;

/**
 * Created by 0stjal24 on 04/05/2017.
 */
public class PreferenceActivity
{
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
