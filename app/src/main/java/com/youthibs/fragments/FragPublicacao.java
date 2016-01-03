package com.youthibs.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.youthibs.Control.YouthControl;
import com.youthibs.MainActivity;
import com.youthibs.MenuPrincipal;
import com.youthibs.R;
import com.youthibs.adapters.AvisosAdapter;


/**
 * Created by CharlleNot on 09/10/2015.
 */
public class FragPublicacao extends Fragment {


    private View view;
    private YouthControl sistema;
    private ListView lvAvisos;
    private  AvisosAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_layout_publicacao, container, false);
        sistema= MenuPrincipal.sistema;
        accessViews();
        return view;

    }
    public void  accessViews(){
        lvAvisos= (ListView)view.findViewById(R.id.lvPublicacoes);


    }

    public void buildAvisos(){
        registerForContextMenu(lvAvisos);
        adapter = new AvisosAdapter(getContext(),sistema.getAvisos(),getFragmentManager().getFragments().get(0));
        lvAvisos.setAdapter(adapter);

    }
    @Override
    public void onResume(){
        buildAvisos();
        super.onResume();
    }


}
