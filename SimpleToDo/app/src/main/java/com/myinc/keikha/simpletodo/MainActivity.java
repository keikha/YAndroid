package com.myinc.keikha.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {


    ArrayList<String> items ;
    ArrayAdapter<String> itemAdapter;
    ListView lvItems ;

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItem();
//        items = new ArrayList<String>();
        itemAdapter = new ArrayAdapter<String>( this , android.R.layout.simple_list_item_1 , items );
        lvItems = (ListView) findViewById(R.id.lvItems);

        lvItems.setAdapter(itemAdapter);

        setupListViewListener();

    }

    public void onAddItem(View v)
    {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemAdapter.add(itemText);
        etNewItem.setText("");
        writeItem();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void setupListViewListener()
    {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        items.remove(position);
                        itemAdapter.notifyDataSetChanged();
                        writeItem();
                        return true;
                    }
                }
        );

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent( MainActivity.this , EditItemActivity.class);
                        i.putStringArrayListExtra("items" , items);
                        i.putExtra("text" , items.get(position));
                        i.putExtra("position" , position);
                        startActivityForResult( i , REQUEST_CODE);

                    }
                }

        );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String name = data.getExtras().getString("newValue");
            int position = data.getIntExtra("position" , 0);


            items.set(position , name);
            itemAdapter.notifyDataSetChanged();
            writeItem();
            // Toast the name to display temporarily on screen

        }

    }

    private void readItem()
    {
        File fileDir = getFilesDir();
        File todoFile = new File( fileDir , "todo.txt");

        try
        {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }
        catch (IOException o)
        {
            items = new ArrayList<>();
        }
    }

    private void writeItem()
    {
        File fileDir = getFilesDir();
        File todoFile = new File( fileDir , "todo.txt");

        try
        {
            FileUtils.writeLines(todoFile, items);
        }
        catch (IOException o)
        {
            o.printStackTrace();
        }
    }
}
