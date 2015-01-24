package com.myinc.keikha.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class EditItemActivity extends ActionBarActivity {

    private String text;
    private int position;
    private EditText etItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        text = getIntent().getStringExtra("text");
        position = getIntent().getIntExtra("position" , 0);
        etItem = (EditText) findViewById(R.id.etItem);
        etItem.setText(text);
        etItem.hasFocus();
        etItem.setSelection(etItem.getText().length());

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
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

    public void OnSaveClick(View v)
    {

        Intent data = new Intent();
        data.putExtra("newValue" , etItem.getText().toString());
        data.putExtra("position" , position);

        setResult( RESULT_OK , data);

        this.finish();
    }
}
