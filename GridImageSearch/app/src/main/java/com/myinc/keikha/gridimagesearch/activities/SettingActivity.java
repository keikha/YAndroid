package com.myinc.keikha.gridimagesearch.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.myinc.keikha.gridimagesearch.R;
import com.myinc.keikha.gridimagesearch.models.SettingsOptions;

import java.util.ArrayList;


public class SettingActivity extends ActionBarActivity  {

    private String[] valuesSize;
    private String[] valuesColor;
    private String[] valuesType;

    private ArrayList<String> indexSize;
    private ArrayList<String> indexColor;
    private ArrayList<String> indexType;


    private Spinner spinSize;
    private Spinner spinColor;
    private Spinner spinType;
    private EditText etSite;
    private SettingsOptions settings;

    private ArrayAdapter<String> adapterType;
    private ArrayAdapter<String> adapterColor;
    private  ArrayAdapter<String> adapterSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        valuesSize = new String[]{"Icon", "Small", "Medium", "Large", "X Large", "XX Large", "Huge"};
        valuesColor = new String[]{"Black", "Blue", "Brown", "Gray", "Green", "Orange", "Pink", "Purple", "Red", "Teal", "White", "Yellow"};
        valuesType = new String[]{"Faces", "Photographic", "Clipart", "Line drawing"};

        indexSize = new ArrayList<String>();
        indexType = new ArrayList<String>();
        indexColor = new ArrayList<String>();

        for(String size: valuesSize)
        {
            indexSize.add(size.toLowerCase().replaceAll("\\s+",""));
        }

        for(String color : valuesColor)
        {
            indexColor.add(color.toLowerCase());
        }

        indexType.add("face");
        indexType.add("photo");
        indexType.add("clipart");
        indexType.add("lineart");

        spinSize = (Spinner) findViewById(R.id.spinSize);
        spinColor = (Spinner) findViewById(R.id.spinColor);
        spinType = (Spinner) findViewById(R.id.spinType);
        etSite = (EditText ) findViewById(R.id.etSite);

        adapterSize = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, valuesSize);
        spinSize.setAdapter(adapterSize);

        adapterColor = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, valuesColor);
        spinColor.setAdapter(adapterColor);

        adapterType = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, valuesType);
        spinType.setAdapter(adapterType);


        settings = (SettingsOptions) getIntent().getSerializableExtra("settings");

        if(settings!=null) {
            this.setSizeSpinner(settings.size);
            this.setColorSpinner(settings.color);
            this.setTypeSpinner(settings.type);
            this.setSiteEditText(settings.site);
        }



        spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                settings.type = indexType.get(spinType.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                settings.size = indexSize.get(spinSize.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Log.i("INFO", "selecte color position: "+ spinColor.getSelectedItemPosition());
//                Log.i("INFO", "selecte color: "+ indexColor.get(spinColor.getSelectedItemPosition()]);
                settings.color = indexColor.get(spinColor.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    public void setSizeSpinner(String value) {

        if(!value.isEmpty() && indexSize.indexOf(value)>=0)
            spinSize.setSelection(indexSize.indexOf(value));

    }

    public void setColorSpinner(String value) {

        if(!value.isEmpty() && indexColor.indexOf(value)>=0)
            spinColor.setSelection(indexColor.indexOf(value));
    }

    public void setTypeSpinner(String value)
    {
        if(!value.isEmpty() && indexType.indexOf(value)>=0)
        spinType.setSelection(indexType.indexOf(value));
    }

    public void setSiteEditText(String value)
    {
        etSite.setText(value);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSaveClick(View view) {
//
        Intent i = new Intent();
        settings.site = etSite.getText().toString();
        i.putExtra("settings" , settings);
//
        setResult(RESULT_OK , i);
        // step 2 : dismiss this screen and go back
        this.finish();

    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//        if(parent.getId()== R.id.spinColor)
//        {
//            Log.i("INFO", "selecte color position: "+ spinColor.getSelectedItemPosition());
//            Log.i("INFO", "selecte color: "+ valuesColor[spinColor.getSelectedItemPosition()]);
//
//            settings.color = valuesColor[spinColor.getSelectedItemPosition()];
//        }
//
//        if(parent.getId()== R.id.spinSize )
//        {
//            settings.size = valuesSize[spinSize.getSelectedItemPosition()];
//        }
//
//        if(parent.getId()== R.id.spinType)
//        {
//            settings.type = valuesType[spinType.getSelectedItemPosition()];
//        }
//
//    }

//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
}
