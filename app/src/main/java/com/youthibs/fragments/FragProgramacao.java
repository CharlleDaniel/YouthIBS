package com.youthibs.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.youthibs.R;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by CharlleNot on 09/10/2015.
 */
public class FragProgramacao extends Fragment {


    private static final int RESULT_OK =-1;
    private int CAMERA_REQUEST ;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CAMERA_REQUEST = 500;
      
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_layout_programacao, container, false);
        return view;
    }

    public void showMessage(String txt){
        Toast.makeText(getContext(),txt,Toast.LENGTH_LONG).show();
    }
}