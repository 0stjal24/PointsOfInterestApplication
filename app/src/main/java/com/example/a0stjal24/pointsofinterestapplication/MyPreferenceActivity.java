package com.example.a0stjal24.pointsofinterestapplication;

import android.os.Bundle;
import android.preference.PreferenceActivity;


/**
 * Created by 0stjal24 on 04/05/2017.
 */
public class MyPreferenceActivity extends PreferenceActivity
{
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
