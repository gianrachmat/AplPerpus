package com.jsproject.aplperpus;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_admin);
        setSupportActionBar(toolbar);

        utama();
    }

    void utama(){
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_admin);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(generateData());
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                klikTambah();
            }
        });
    }

    void klikTambah() {
        Intent in = new Intent(this, TambahBukuActivity.class);

        startActivityForResult(in, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 10) {
                utama();
            } else {

            }
        }
    }

    private ArrayList<Buku> generateData() {
        ArrayList<Buku> items;
        DatabaseHandler db = DatabaseHandler.getInstance(this);

        items = db.ambilSemuaBuku();

        return items;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                clickList(position);
                Log.i("item", " Clicked on Item " + position);
            }
        });
    }

    void clickList(final int pos) {
        //Intent intent = new Intent(this, ScrollingActivity.class);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Hapus");
        arrayAdapter.add("Ubah");
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Options");
        /*adb.setMessage("Yang anda klik = "+pos);
        adb.setPositiveButton("OK", null);*/

        adb.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        adb.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(AdminActivity.this);
                builderInner.setMessage(strName);
                /*builderInner.setTitle("");*/
                builderInner.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        diKlik(pos);

                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });

        adb.show();

        //intent.putExtra("gambar", "" + pos);

        //startActivity(intent);
    }

    void diKlik(int pos){
        ArrayList<Buku> bukuArrayLists = generateData();

        Log.d("Sebelum hapus = ", ""+bukuArrayLists.get(pos).get_nama_buku());

        DatabaseHandler.getInstance(this).hapusBuku(bukuArrayLists.get(pos).get_nama_buku());

        Log.d("Sesudah hapus = ", ""+bukuArrayLists.get(pos).get_nama_buku());

        MyRecyclerViewAdapter www = new MyRecyclerViewAdapter(bukuArrayLists);
        www.deleteItem(pos);
        utama();
    }
}
