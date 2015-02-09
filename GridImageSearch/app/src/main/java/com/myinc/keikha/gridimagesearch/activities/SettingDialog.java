package com.myinc.keikha.gridimagesearch.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.myinc.keikha.gridimagesearch.R;
import com.myinc.keikha.gridimagesearch.models.SettingsOptions;

import java.util.ArrayList;

/**
 * Created by keikha on 2/2/15.
 */
public class SettingDialog extends DialogFragment {


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


    public SettingDialog()
    {

    }

    public static SettingDialog newInstance(SettingsOptions settings)
    {
        SettingDialog dialog = new SettingDialog();
        Bundle args = new Bundle();
        args.putSerializable("settings", settings);

        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_setting , container);


        settings = (SettingsOptions) getArguments().getSerializable("settings");

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

        spinSize = (Spinner) view.findViewById(R.id.spinSize);
        spinColor = (Spinner) view.findViewById(R.id.spinColor);
        spinType = (Spinner) view.findViewById(R.id.spinType);
        etSite = (EditText ) view.findViewById(R.id.etSite);

//        adapterSize = new ArrayAdapter<String>(view, R.layout.support_simple_spinner_dropdown_item, valuesSize);
//        spinSize.setAdapter(adapterSize);
//
//        adapterColor = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, valuesColor);
//        spinColor.setAdapter(adapterColor);
//
//        adapterType = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, valuesType);
//        spinType.setAdapter(adapterType);


//        settings = (SettingsOptions) getIntent().getSerializableExtra("settings");
//
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



//        view.findViewById(R.id.btnSave).setOnClickListener(new DialogInterface.OnClickListener() {
//            @Override
////            public void onClick(DialogInterface dialog, int which) {
////                dialog.dismiss();
////            }
//        });
        return view;
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


    public void onSaveClick(View view) {
//
//        Intent i = new Intent();
//        settings.site = etSite.getText().toString();
//        i.putExtra("settings" , settings);
////
//        setResult(RESULT_OK , i);
//        // step 2 : dismiss this screen and go back
//        this.finish();

        this.dismiss();

    }

}
