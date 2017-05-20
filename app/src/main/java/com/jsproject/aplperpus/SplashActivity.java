package com.jsproject.aplperpus;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent i = new Intent(this, MainActivity.class);

        DatabaseHandler db = DatabaseHandler.getInstance(this);
        /*
         * C R U D
         */
        // tambah buku
        Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.empty);
        byte[] oh = DbBitmapUtility.getBytes(bmp);
        Log.d("Insert: ", "Inserting...");
        if (db.ambilJumlahBuku()==0) {

            db.tambahBuku(new Buku("Belum ada buku gan", "", oh));
            /*db.tambahBuku(new Buku("Laskar Pelangi", "Andrea Hirata", R.drawable.laskar_pelangi));
            db.tambahBuku(new Buku("Bumi Manusia", "Pramoedya Ananta Toer", R.drawable.bumi_manusia));
            db.tambahBuku(new Buku("5 cm", "Donny Dhirgantoro", R.drawable.g5cm));
            db.tambahBuku(new Buku("Negeri 5 Menara", "Ahmad Fuadli", R.drawable.g5_menara));
            db.tambahBuku(new Buku("Sang Pemimpi", "Andrea Hirata", R.drawable.sang_pemimpi));
            db.tambahBuku(new Buku("Ronggeng Dukuh Paruk", "Ahmad Tohari", R.drawable.ronggeng));
            db.tambahBuku(new Buku("Perahu Kertas", "Dee Lestari", R.drawable.perahu_kertas));*/
        }

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}
