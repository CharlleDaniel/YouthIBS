package com.youthibs;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.youthibs.Control.YouthControl;
import com.youthibs.adapters.TabAdapters;
import com.youthibs.entidades.Usuario;
import com.youthibs.extras.SlidingTabLayout;

public class MenuPrincipal extends AppCompatActivity {

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton fab;
    private int tabposition;
    public static YouthControl sistema;
    private Toolbar toolbar;
    private LocationManager locationManager;
    public static Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        sistema = MainActivity.sistema;
        user=sistema.getUsuarioLogado();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(this,R.style.AppThemeBarTitle);
        toolbar.setTitle("  " + user.getNome());
        toolbar.setLogo(R.drawable.logo_menu);
        toolbar.setSubtitleTextAppearance(this, R.style.AppThemeBarSubTitle);
        verificarInternet();
        setSupportActionBar(toolbar);

        accessViews();
        mViewPager = (ViewPager) findViewById(R.id.vp_tabs);
        mViewPager.setAdapter(new TabAdapters(getSupportFragmentManager(), this));

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        mSlidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.white));
        mSlidingTabLayout.setCustomTabView(R.layout.custom_tab_view, R.id.imageView);
        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tabposition = position;
                verificarInternet();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mSlidingTabLayout.setViewPager(mViewPager);

    }

    @Override
    protected void onResume() {
        verificarInternet();
        super.onResume();
    }

    public void verificarInternet(){
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if ( netInfo == null||cm==null) {
            toolbar.setSubtitle("    Offline");
        }
        else  if(netInfo.isConnectedOrConnecting()){
            if(netInfo.isConnected()&& netInfo.isAvailable()){
                toolbar.setSubtitle("    Online ");
            }else{
                toolbar.setSubtitle("   Contectando");

            }
        }
    }
    public void accessViews(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if (tabposition==0) {
                Intent it = new Intent(MenuPrincipal.this, CadAvisos.class);
                startActivity(it);

            }

            }
        });
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
}
