package com.youthibs;

import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.youthibs.Control.YouthControl;
import com.youthibs.entidades.Usuario;

import java.io.ByteArrayOutputStream;


public class MainActivity extends AppCompatActivity implements OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {

    // VIEWS
    private static final int RESULT_LOAD_IMAGE =500 ;
    private LinearLayout llContainerAll;
    private ProgressBar pbContainer;
    private RelativeLayout llConnected;
    private TextView tvLogin;
    private Button btNext;
    private EditText edNome;
    protected String name;
    protected String mAccountName;
    protected String dataNasci;

    //Control
    protected static YouthControl sistema;

    //Drive Login

    private static final int SIGN_IN_CODE = 56465;
    private GoogleApiClient googleApiClient;
    private ConnectionResult connectionResult;
    private EditText edDataNasc;
    private ImageView imgProfile;
    private EditText edNumero;
    private byte[] img;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sistema = new YouthControl(getBaseContext());

        accessViews();


        if(sistema.getUsuarioLogado()!=null){
            Intent it = new Intent(this,MenuPrincipal.class);
            startActivity(it);
            super.finish();
        }

        googleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .addConnectionCallbacks(MainActivity.this)
                .addOnConnectionFailedListener(MainActivity.this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();

        showUi(false,true);

    }

    public void criaDiretorios(){
        try {
            if (!new java.io.File("/sdcard/SimpleERP").exists()) { // Verifica se o diretório existe.
                (new java.io.File("/sdcard/SimpleERP")).mkdir();// Cria o diretório
                (new java.io.File("/sdcard/SimpleERP/Planilhas")).mkdir();// Cria o diretório
            }
        } catch (Exception ex) {
            showMessage("Erro");
        }
    }

    // UTIL
    public void accessViews(){
        llContainerAll = (LinearLayout) findViewById(R.id.llContainerAll);
        pbContainer = (ProgressBar) findViewById(R.id.pbContainer);

        // CONNECTED
        llConnected = (RelativeLayout) findViewById(R.id.llConnected);
        tvLogin = (TextView) findViewById(R.id.login);
        btNext = (Button) findViewById(R.id.avançar);
        edNome=(EditText)findViewById(R.id.nome);
        edDataNasc=(EditText)findViewById(R.id.dataNasc);
        edNumero=(EditText)findViewById(R.id.editText2);
        imgProfile= (ImageView)findViewById(R.id.ivProfile);
        // LISTENER

        btNext.setOnClickListener(MainActivity.this);

        verificaCampo();
        verificaCampo2();
    }

    public void showUi(boolean status, boolean statusProgressBar){
        if(!statusProgressBar){
            llContainerAll.setVisibility(View.VISIBLE);
            tvLogin.setVisibility(View.GONE);
            pbContainer.setVisibility(View.GONE);


            llConnected.setVisibility(!status ? View.GONE : View.VISIBLE);
        }
        else{

            pbContainer.setVisibility(View.VISIBLE);
        }
    }

    // LISTENERS
    @Override
    public void onClick(View v) {

        if(v.getId() == btNext.getId()){

            String temp = edNome.getText().toString();
            temp=temp.trim();
            if(temp.equals("")){
                showMessage("Não é Permitido Campo em Branco");
            }
            else{
                Usuario u = new Usuario();
                u.setNome(temp);
                u.setEmail(mAccountName);
                sistema.login(u);
                Intent it = new Intent(this,MenuPrincipal.class);
                startActivity(it);
                super.finish();
            }

        }

    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(){
        super.onStart();

        if(googleApiClient != null){
            googleApiClient.connect();
        }
    }

    @Override
    public void onStop(){
        super.onStop();

        if(googleApiClient != null && googleApiClient.isConnected()){
            googleApiClient.disconnect();
        }
    }

    public void resolveSignIn(){
        if(connectionResult != null && connectionResult.hasResolution()){
            try {
                connectionResult.startResolutionForResult(MainActivity.this, SIGN_IN_CODE);
            }
            catch(IntentSender.SendIntentException e) {
                googleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if(googleApiClient.isConnected()){
            showUi(true, false);
            mAccountName = Plus.AccountApi.getAccountName(googleApiClient);
            Person p = Plus.PeopleApi.getCurrentPerson(googleApiClient);
            name= p.getName().getGivenName()+ " "+p.getName().getFamilyName();
            name=sistema.upCaseAllFirstChar(name);
            dataNasci=p.getBirthday();
            edNome.setText(name);
            edDataNasc.setText(dataNasci);

            showMessage("Conectado com " + mAccountName);

        }else{
            resolveSignIn();
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        googleApiClient.connect();
        showUi(false, false);
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if(!result.hasResolution()){
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), MainActivity.this, 0).show();
            return;
        }
        connectionResult = result;
        resolveSignIn();
    }
    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }

    public void verificaCampo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(edDataNasc.getText().toString().length()<10 || edDataNasc.isEnabled()){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if(edDataNasc.getText().toString().length()==2 ){
                                int data=Integer.parseInt(edDataNasc.getText().toString().substring(0,2));

                                if(data>0 && data <=31){
                                    edDataNasc.setText(edDataNasc.getText().toString().substring(0,2)+"/");
                                    edDataNasc.setSelection(3);
                                }else{
                                    showMessage("Dia Invalido.");
                                    edDataNasc.setText("");
                                }

                            }
                            if(edDataNasc.getText().toString().length()==5){
                                int mes=Integer.parseInt(edDataNasc.getText().toString().substring(3,5));
                                if(mes>0 && mes<=12){
                                    edDataNasc.setText(edDataNasc.getText().toString().substring(0,5)+"/");
                                    edDataNasc.setSelection(6);
                                }else{
                                    showMessage("Mês Invalido.");
                                    edDataNasc.setText(edDataNasc.getText().toString().substring(0,3));
                                    edDataNasc.setSelection(3);
                                }
                            }
                        }
                    });
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        }).start();
    }

    public void verificaCampo2(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(edNumero.getText().toString().length()<14 || edNumero.isEnabled()){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if(edNumero.getText().toString().length()>=2 ){
                                if(edNumero.getText().toString().length()==2 ){
                                    try {
                                        int ddd=Integer.parseInt(edNumero.getText().toString().substring(0,2));
                                        edNumero.setText("("+edNumero.getText().toString().substring(0,2)+") ");
                                        edNumero.setSelection(5);
                                    }catch (Exception e){
                                        edNumero.setText(edNumero.getText().toString().substring(1,2));
                                        edNumero.setSelection(1);
                                    }
                                }


                            }

                        }
                    });
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }


            }
        }).start();
    }
    public void addFoto(View v){

        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            Bitmap resize;

            resize= Bitmap.createScaledBitmap(bitmap, imgProfile.getWidth(), imgProfile.getHeight(), true);

            Bitmap circleBitmap = Bitmap.createBitmap(resize.getWidth(),resize.getHeight(), Bitmap.Config.ARGB_8888);
            BitmapShader shader = new BitmapShader (resize,  Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            Paint paint = new Paint();
            paint.setShader(shader);
            Canvas c = new Canvas(circleBitmap);
            c.drawCircle(resize.getWidth() / 2, resize.getHeight() / 2, resize.getWidth() / 2, paint);

            imgProfile.setImageBitmap(resize);



            ByteArrayOutputStream saida = new ByteArrayOutputStream();
            resize.compress(Bitmap.CompressFormat.JPEG, 100, saida);
            img = saida.toByteArray();

            // String picturePath contains the path of selected Image
        }
        else if (requestCode == SIGN_IN_CODE){

            if(resultCode != RESULT_OK){
                resolveSignIn();
            }

            if(!googleApiClient.isConnecting()){
                googleApiClient.connect();
            }
        }
    }
}