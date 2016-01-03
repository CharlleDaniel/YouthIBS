package com.youthibs.adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.youthibs.Control.YouthControl;
import com.youthibs.MainActivity;
import com.youthibs.MenuPrincipal;
import com.youthibs.R;
import com.youthibs.entidades.Aviso;
import com.youthibs.fragments.FragPublicacao;

import java.util.List;

/**
 * Created by CharlleNot on 19/10/2015.
 */

public class AvisosAdapter extends BaseAdapter {
    private final Display display;
    private final Point size;
    private List<Aviso> lAvisos;
    private Context context;
    private Fragment frag;

    public AvisosAdapter(Context context, List<Aviso> list, Fragment frag){
        this.context = context;
        this.frag=frag;
        this.lAvisos = list;
        display = ((WindowManager) context.getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay();
        size = new Point();
        display.getSize(size);
    }

    @Override
    public int getCount() {
        return lAvisos.size();
    }

    @Override
    public Aviso getItem(int position) {
        return this.lAvisos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.lAvisos.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        final Aviso aviso= lAvisos.get(position);

        final LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout =inflater.inflate(R.layout.custom_aviso, null);

        TextView tvTitle = (TextView) layout.findViewById(R.id.tvTitleV);
        tvTitle.setText(aviso.getTitle());

        TextView tvText = (TextView) layout.findViewById(R.id.tvTextV);
        tvText.setText(aviso.getText());

        Button btO= (Button)layout.findViewById(R.id.btOptions);
        btO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YouthControl y = MenuPrincipal.sistema;
                y.removeAviso(aviso);
                frag.onResume();
            }
        });


        ImageView img= (ImageView)layout.findViewById(R.id.ivAvisoV);
        Bitmap raw  = BitmapFactory.decodeByteArray(aviso.getFoto(), 0, aviso.getFoto().length);
        img.setImageBitmap(raw);


        return layout;
    }



}
