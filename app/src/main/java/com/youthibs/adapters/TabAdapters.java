package com.youthibs.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.youthibs.R;
import com.youthibs.fragments.FragProgramacao;
import com.youthibs.fragments.FragVisita;
import com.youthibs.fragments.FragPublicacao;
import com.youthibs.fragments.FragOracao;


/**
 * Created by CharlleNot on 09/10/2015.
 */
public class TabAdapters extends FragmentPagerAdapter {
    private Context mContext;
    private String[]titles= {""+R.drawable.tab1,""+R.drawable.tab2,""+R.drawable.tab3,""+R.drawable.tab4,""+R.drawable.tab1off,""+R.drawable.tab2off,""+R.drawable.tab3off,""+R.drawable.tab4off};

    public TabAdapters(FragmentManager fm, Context c) {
        super(fm);
        this.mContext=c;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment frag= null;
        if(i==0){
            frag= new FragPublicacao();
        }else if (i==1){
            frag= new FragOracao();
        }else if (i==2){
            frag= new FragVisita();
        }else if (i==3){
            frag= new FragProgramacao();
        }

        Bundle b = new Bundle();
        b.putInt("position",i);

        frag.setArguments(b);

        return frag;
    }

    @Override
    public int getCount() {
        return titles.length-4;
    }
    @Override
    public CharSequence getPageTitle(int position){
        return (titles[position]);
    }
}
