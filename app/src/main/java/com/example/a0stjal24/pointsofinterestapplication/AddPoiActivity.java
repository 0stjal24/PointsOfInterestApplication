package com.example.a0stjal24.pointsofinterestapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by 0stjal24 on 04/05/2017.
 */
public class AddPoiActivity extends Activity implements View.OnClickListener
{

    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpoi);

        Button marker = (Button)findViewById(R.id.button);
        marker.setOnClickListener(this);
    }

    public void onClick(View v)
    {

        Bundle bundle=new Bundle();

        EditText poiName = (EditText) findViewById(R.id.poiname);
        bundle.putString("a0stjal24.name",poiName.getText().toString() );

        EditText poiType = (EditText) findViewById(R.id.poitype);
        bundle.putString("a0stjal24.type",poiType.getText().toString() );

        EditText poiDesc = (EditText) findViewById(R.id.poidesc);
        bundle.putString("a0stjal24.desc",poiDesc.getText().toString() );

        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();


    }


}
