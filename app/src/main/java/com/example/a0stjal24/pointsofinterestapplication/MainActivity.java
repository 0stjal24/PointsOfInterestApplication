package com.example.a0stjal24.pointsofinterestapplication;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.os.Environment;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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

        items = new ItemizedIconOverlay<OverlayItem>(this, new ArrayList<OverlayItem>(), markerGestureListener);
        mv.getOverlays().add(items);

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
            savePoi();
            return true;
        }
        else if(item.getItemId() == R.id.loadpoi)
        {
            loadPoi();
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

            OverlayItem item = new OverlayItem(poiName, poiType + poiDesc, new GeoPoint(latitude, longitude));



            items.addItem(item);
            mv.invalidate();

            Toast.makeText(MainActivity.this, "Marker added!", Toast.LENGTH_SHORT).show();

        }
        else if(requestCode == 1)
        {

        }
    }
   private void savePoi() {

       if (network == false) {


           Toast.makeText(MainActivity.this, "Saved marker", Toast.LENGTH_SHORT).show();


           for (int i = 0; i < items.size(); i++) {
               OverlayItem item = items.getItem(i);

               String stringToSave = item.getTitle() + "," + item.getSnippet() + "," + item.getPoint();

               try {
                   PrintWriter pw = new PrintWriter(new FileWriter(Environment.getExternalStorageDirectory().getAbsolutePath() + "/markers.txt"));
                   pw.println(stringToSave);
                   pw.flush();
                   pw.close();

               } catch (IOException e) {
                   new AlertDialog.Builder(this).setMessage("ERROR: " + e).show();


               }


           }
       }

   }

   @Override
   protected void onStop() {
       super.onStop();
       savePoi();
   }


    private void loadPoi(){

        Toast.makeText(MainActivity.this, "Markers Loaded", Toast.LENGTH_SHORT).show();

        try {
            FileReader fr = new FileReader(Environment.getExternalStorageDirectory().getAbsolutePath() + "/markers.txt");
            BufferedReader reader = new BufferedReader(fr);
            String stringToLoad;
            while ((stringToLoad = reader.readLine()) != null)
            {

                String[] markerprt = stringToLoad.split(",");
                if(markerprt.length==4)
                {
                    double la=Double.parseDouble(markerprt[2]);
                    double lo=Double.parseDouble(markerprt[3]);

                    OverlayItem item = new OverlayItem (markerprt[0], markerprt[1], new GeoPoint(la, lo));
                    items.addItem(item);
                }






            }

        }catch(IOException e){
            new AlertDialog.Builder(this).setMessage("ERROR: " + e).show();


        }
    }
    
}















//user021