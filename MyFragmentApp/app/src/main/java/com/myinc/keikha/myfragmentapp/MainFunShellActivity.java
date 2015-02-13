package com.myinc.keikha.myfragmentapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainFunShellActivity extends ActionBarActivity {


    TwoFragment fragment2;
    OneFragment fragment1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fun_shell);

        fragment1 = new OneFragment();
        fragment2 = new TwoFragment();
    }


    // Whole the activity needs to do:
    // 1. Insertion and removal of fragments
    // 2. Navigation between fragments
    // 3. Communicating data between fragments
    // 4. ActionBar information and logic


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_fun_shell, menu);
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

    public void onFragmentOne(View view) {
        // 1. begin a transaction for tha fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        //2. insert or remove a fragment
        ft.addToBackStack("");// when you hit the back, it goes to the previous fragment
        ft.replace(R.id.flContainer , fragment1);

        // 3. commit the transaction
        ft.commit();
    }

    public void onFragmentTwo(View view) {

        // 1. begin a transaction for tha fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.addToBackStack("");// when you hit the back, it goes to the previous fragment
        //2. insert or remove a fragment
        ft.replace(R.id.flContainer , fragment2);

        // 3. commit the transaction
        ft.commit();
    }
}
