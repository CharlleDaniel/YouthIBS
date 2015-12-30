package com.youthibs;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.youthibs.Control.YouthControl;
import com.youthibs.entidades.Aviso;

import java.io.ByteArrayOutputStream;


/**
 * Created by CharlleNot on 14/10/2015.
 */
public class CadAvisos extends AppCompatActivity {


    private static final int RESULT_LOAD_IMAGE =500 ;
    private Toolbar bar;
    private YouthControl sistema;
    private byte[] img;
    private EditText textTitle;
    private EditText textText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sistema=MainActivity.sistema;
        setContentView(R.layout.layout_cad_avisos);
        acessViews();

        bar.setTitle("Nova Publicação");
        bar.setTitleTextAppearance(this, R.style.AppThemeBarTitleCad);
        bar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(bar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    private void acessViews() {
        bar= (Toolbar)findViewById(R.id.bar);
        textTitle= (EditText)findViewById(R.id.edTitle);
        textText=(EditText)findViewById(R.id.edText);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cad, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.salvar){

            showMessage("Publicado");
            Aviso aviso = new Aviso();
            aviso.setTitle(textTitle.getText().toString().trim());
            aviso.setText(textText.getText().toString().trim());
            aviso.setFoto(img);
            aviso.setId(sistema.getAvisos().size());
            sistema.addAviso(aviso);
            super.finish();

        }if(id==android.R.id.home){
            onBackPressed();
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
            Display display = ((WindowManager) getSystemService(this.WINDOW_SERVICE)).getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            Bitmap resize= Bitmap.createScaledBitmap(bitmap, size.x, size.y / 2, true);

            ImageView foto = (ImageView)findViewById(R.id.ivAviso);
            foto.setImageBitmap(resize);

            ByteArrayOutputStream saida = new ByteArrayOutputStream();
            resize.compress(Bitmap.CompressFormat.JPEG, 100, saida);
            img = saida.toByteArray();

            // String picturePath contains the path of selected Image
        }
    }
}
/* File imgFile= new File("/sdcard/test.jpg");
        if(imgFile.exists()){
            Display display = ((WindowManager) getActivity().getSystemService(getActivity().WINDOW_SERVICE)).getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            Bitmap resize= Bitmap.createScaledBitmap(bitmap, size.x, size.y/2, true);

            ImageView foto = (ImageView)view.findViewById(R.id.ivTest);
            foto.setImageBitmap(resize);

            ByteArrayOutputStream saida = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,saida);
            byte[] img = saida.toByteArray();



            //Bitmap raw  = BitmapFactory.decodeByteArray(img, 0, img.length);


        }
*/
