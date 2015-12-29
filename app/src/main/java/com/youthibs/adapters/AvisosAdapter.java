package com.youthibs.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.youthibs.R;
import com.youthibs.entidades.Aviso;

import java.util.List;

/**
 * Created by CharlleNot on 19/10/2015.
 */

public class AvisosAdapter extends BaseAdapter {
    private final Display display;
    private final Point size;
    private List<Aviso> lAvisos;
    private Context context;


    public AvisosAdapter(Context context, List<Aviso> list){
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {


        Aviso aviso= lAvisos.get(position);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout =inflater.inflate(R.layout.custom_aviso, null);

        TextView tvTitle = (TextView) layout.findViewById(R.id.tvTitleV);
        tvTitle.setText(aviso.getTitle());

        TextView tvText = (TextView) layout.findViewById(R.id.tvTextV);
        tvText.setText(aviso.getText());


        ImageView img= (ImageView)layout.findViewById(R.id.ivAvisoV);
        Bitmap raw  = BitmapFactory.decodeByteArray(aviso.getFoto(), 0, aviso.getFoto().length);
        Bitmap resize= Bitmap.createScaledBitmap(raw, size.x, size.y/2, true);

        img.setImageBitmap(resize);
        return layout;
    }



}
