package com.jsproject.aplperpus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import java.util.ArrayList;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        isi();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void isi(){
        DatabaseHandler db = DatabaseHandler.getInstance(this);

        Intent man = getIntent();

        String pos = man.getStringExtra("gambar");
        Log.d("isi pos ", ""+pos);

        int pa = Integer.parseInt(pos);
        Log.d("isi pa ", ""+pa);
        ImageView img = (ImageView) findViewById(R.id.image);

        ArrayList<Buku> aB = db.ambilSemuaBuku();

        Log.d("isi ambilbuku ", ""+db.ambilBuku(pa));
        img.setImageBitmap(DbBitmapUtility.getImage(aB.get(pa).get_gambar_buku()) );

        setTitle(aB.get(pa).get_nama_buku());
    }
}
