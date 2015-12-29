package com.youthibs.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youthibs.R;


/**
 * Created by CharlleNot on 09/10/2015.
 */
public class FragPublicacao extends Fragment {


    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_layout_publicacao, container, false);

        return view;

    }


}
