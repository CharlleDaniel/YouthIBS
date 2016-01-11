package com.youthibs.adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuInflater;
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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    private YouthControl sistema;

    public AvisosAdapter(Context context, List<Aviso> list, Fragment frag){
        this.context = context;
        sistema= MenuPrincipal.sistema;
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

        TextView tvTime = (TextView) layout.findViewById(R.id.tvTime);
        long time=aviso.getData().getTime();
        tvTime.setText(getData(time));


        TextView tvText = (TextView) layout.findViewById(R.id.tvTextV);
        tvText.setText(aviso.getText());

        ImageView btO= (ImageView)layout.findViewById(R.id.ivMenu);
        btO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });

        final ImageView btParticipar=(ImageView)layout.findViewById(R.id.ivParticipar);
        final TextView qtdParticipantes=(TextView)layout.findViewById(R.id.qtdParticipantes);

        btParticipar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!aviso.isParticipante(sistema.getUsuarioLogado().getId())){
                    aviso.setParticipante(sistema.getUsuarioLogado().getId());
                    btParticipar.setImageResource(R.drawable.icon_likegreen);
                    qtdParticipantes.setText(""+aviso.getQtdParticipantes());
                }else{
                    aviso.setParticipante(sistema.getUsuarioLogado().getId());
                    btParticipar.setImageResource(R.drawable.icon_like);
                    qtdParticipantes.setText(""+aviso.getQtdParticipantes());
                }

            }
        });
        ImageView img= (ImageView)layout.findViewById(R.id.ivAvisoV);
        Bitmap raw  = BitmapFactory.decodeByteArray(aviso.getFoto(), 0, aviso.getFoto().length);
        img.setImageBitmap(raw);


        return layout;
    }

    public String getData(long time){
        String txt="";
        SimpleDateFormat sdfTime = new SimpleDateFormat("ss");
        int sec=Integer.parseInt(sdfTime.format(time));
        sdfTime = new SimpleDateFormat("mm");
        int min=Integer.parseInt(sdfTime.format(time));
        sdfTime = new SimpleDateFormat("hh");
        int hora=Integer.parseInt(sdfTime.format(time));
        sdfTime = new SimpleDateFormat("dd");
        int dia=Integer.parseInt(sdfTime.format(time));
        sdfTime = new SimpleDateFormat("MM");
        int mes=Integer.parseInt(sdfTime.format(time));
        sdfTime = new SimpleDateFormat("yyyy");
        int ano=Integer.parseInt(sdfTime.format(time));


        long timeNow= new Date().getTime();
        sdfTime = new SimpleDateFormat("ss");
        int secNow=Integer.parseInt(sdfTime.format(timeNow));
        sdfTime = new SimpleDateFormat("mm");
        int minNow=Integer.parseInt(sdfTime.format(timeNow));
        sdfTime = new SimpleDateFormat("hh");
        int horaNow=Integer.parseInt(sdfTime.format(timeNow));
        sdfTime = new SimpleDateFormat("dd");
        int diaNow=Integer.parseInt(sdfTime.format(timeNow));
        sdfTime = new SimpleDateFormat("MM");
        int mesNow=Integer.parseInt(sdfTime.format(timeNow));
        sdfTime = new SimpleDateFormat("yyyy");
        int anoNow=Integer.parseInt(sdfTime.format(timeNow));

        if(ano==anoNow){
            if(mes==mesNow){
                if(dia==diaNow){
                    if(min==minNow){
                        if(sec==secNow){
                            txt="Agora mesmo";
                        }else{
                            txt="há "+(secNow-sec)+" segundos";
                        }
                    }else{
                        txt="há "+(minNow-min)+" minutos";
                    }
                }else{
                    txt="há "+(diaNow-dia)+" dias";
                }
            }else{
                txt="há "+(mesNow-mes)+" meses";
            }
        }else{
            txt= "há mais de 1 ano";
        }
        return txt;
    }
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(context, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.getMenu().removeItem(R.id.action_settings);
        popup.getMenu().add(1,21,1,"Excluir");
        popup.show();


    }

}
