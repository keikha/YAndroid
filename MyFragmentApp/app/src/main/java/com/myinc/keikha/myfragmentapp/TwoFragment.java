package com.myinc.keikha.myfragmentapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by keikha on 2/10/15.
 */

// Fragment life-cycle
    // 1. create view
    // 2. creat : not view related
    // 3. After the parent activity is loaded
    // 4. destroy
public class TwoFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        // 1. create your view
        View view = inflater.inflate(R.layout.fragement_two, container, false);

        // 2. Lookup your subviews

        TextView t = (TextView) view.findViewById(R.id.textView);
        // 3. return view
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // initialized data that not view related
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // After activity is done loading

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
