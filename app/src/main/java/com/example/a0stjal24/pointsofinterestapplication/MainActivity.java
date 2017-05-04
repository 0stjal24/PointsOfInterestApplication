package com.example.a0stjal24.pointsofinterestapplication;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
{

    MapView mv;
    ItemizedIconOverlay<OverlayItem> items;
    ItemizedIconOverlay.OnItemGestureListener<OverlayItem> markerGestureListener;
    private boolean network;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // This Line sets the user agent, a requirement to download OSM maps
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        setContentView(R.layout.activity_main);
        mv = (MapView)findViewById(R.id.map1);

        mv.setBuiltInZoomControls(true);
        mv.getController().setZoom(14);
        mv.getController().setCenter(new GeoPoint(50.9111,-1.4025));

        double latitude = mv.getMapCenter().getLatitude();
        double longitude = mv.getMapCenter().getLongitude();

        markerGestureListener = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>()
        {
            public boolean onItemLongPress(int i, OverlayItem item){
                Toast.makeText(MainActivity.this, item.getSnippet(), Toast.LENGTH_SHORT).show();
                return true;
            }

            public boolean onItemSingleTapUp(int i, OverlayItem item) {
                Toast.makeText(MainActivity.this, item.getSnippet(), Toast.LENGTH_SHORT).show();
                return true;
            }

        };

        items = new ItemizedIconOverlay<OverlayItem>(this, new ArrayList<OverlayItem>(), null);

    }
    // This is where we inflate the menu . this loads in the menu from the XML file
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_poi, menu);
        return true;
    }
    // onOptionsItemSelected method takes MenuItem as a parameter
    // Can find out which MenuItem was selected bby using the getItemId() method of the MenuItem
    // This will return item's ID as defined in the menu.xml file.
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.addpoi)
        {
            // react to the menu item being slected
            // An intent launches the second activity.
            // Intent is description of an instruction to do something
            Intent intent = new Intent(this,AddPoiActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }
        else if(item.getItemId() == R.id.savepoi)
        {

            return true;
        }
        else if(item.getItemId() == R.id.loadpoi)
        {
            return true;
        }
        else if (item.getItemId() == R.id.prefs) {
            Intent intent = new Intent(this, MyPreferenceActivity.class);
            startActivityForResult(intent, 1);
            return true;
        }
        return false;
    }


    protected void onActivityResult(int requestCode,int resultCode, Intent intent)
    {
        if(requestCode==0)
        {


            Bundle bundle = intent.getExtras();

            String poiName = bundle.getString("a0stjal24.name");
            String poiType = bundle.getString("a0stjal24.type");
            String poiDesc = bundle.getString("a0stjal24.desc");

            double latitude = mv.getMapCenter().getLatitude();
            double longitude = mv.getMapCenter().getLongitude();

            OverlayItem addpoi = new OverlayItem(poiName, poiType + poiDesc, new GeoPoint(latitude, longitude));

            // this.listPOIs.add(new POIs(poiname, poitype, poidescription, latitude, longitude));

            items.addItem(addpoi);

            mv.getOverlays().add(items);

            mv.refreshDrawableState();

            Toast.makeText(MainActivity.this, "Marker added!", Toast.LENGTH_SHORT).show();

        }
        //else if(requestCode == 1)
       // {

        //}
    }

    public void onStart() {
        super.onStart();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        network = prefs.getBoolean("network", false);
    }





    protected void onStop() {
        super.onStop();

    }



}


//user021