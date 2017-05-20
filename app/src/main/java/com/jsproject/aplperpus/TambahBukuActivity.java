package com.jsproject.aplperpus;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class TambahBukuActivity extends AppCompatActivity {
    private static final int SELECT_PICTURE = 1;

    private String selectedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_buku);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
            }
        }
        ImageView img = (ImageView) findViewById(R.id.gambar_buku);
        img.setImageURI(Uri.parse(selectedImagePath));
    }

    public void klikSimpan(View v) {
        DatabaseHandler db = DatabaseHandler.getInstance(this);
        String nama_buku, nama_pengarang;
        byte[] gambar;

        EditText editText = (EditText) findViewById(R.id.nama_buku);
        nama_buku = editText.getText().toString();
        editText = (EditText) findViewById(R.id.nama_pengarang);
        nama_pengarang = editText.getText().toString();
        ImageView imageView = (ImageView) findViewById(R.id.gambar_buku);
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        gambar = DbBitmapUtility.getBytes(bitmap);

        Buku buku = new Buku(nama_buku, nama_pengarang, gambar);

        db.tambahBuku(buku);

        Toast.makeText(this, "Tambah Buku Berhasil", Toast.LENGTH_SHORT).show();

        setResult(10);
        finish();
    }

    public void klikPilihGambar(View v) {
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        int RESULT_LOAD_IMAGE = 1;
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    public String getPath(Uri uri) {
        // just some safety built in
        if (uri == null) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }

}
