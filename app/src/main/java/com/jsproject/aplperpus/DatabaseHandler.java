package com.jsproject.aplperpus;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by J on 17/04/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // static variables
    // db version
    private static final int DATABASE_VERSION = 1;

    // nama db
    private static final String DATABASE_NAME = "daftarBuku";

    // nama tabel dari buku
    private static final String TABLE_BOOKS = "buku";

    // nama kolom dari tabel buku
    private final static String KEY_ID = "id";
    private final static String KEY_BOOK_NAME = "bookName";
    private final static String KEY_AUTHOR = "bookAuthor";
    private final static String KEY_IMAGE = "bookImage";

    private static DatabaseHandler mInstance = null;

    //constructor
    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return mInstance;
    }

    void keByte(Bitmap bitmap) {
        byte[] bytes = DbBitmapUtility.getBytes(bitmap);
    }

    // buat tabel
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOKS_TABLE = "CREATE TABLE " + TABLE_BOOKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_BOOK_NAME
                + " TEXT," + KEY_AUTHOR + " TEXT," + KEY_IMAGE
                + " BLOB" + ")";
        db.execSQL(CREATE_BOOKS_TABLE);
    }

    // upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop tabel sebelumnya jika ada
        db.execSQL("DROP TABLE IF EXIST " + TABLE_BOOKS);

        // buat tabel lagi
        onCreate(db);
    }

    // tambah buku
    public void tambahBuku(Buku buku) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BOOK_NAME, buku.get_nama_buku()); // nama buku
        values.put(KEY_AUTHOR, buku.get_nama_pengarang()); // nama pengarang
        values.put(KEY_IMAGE, buku.get_gambar_buku());

        // tambah baris
        db.insert(TABLE_BOOKS, null, values);
        db.close(); // menutup koneksi database
    }

    // mengambil data buku
    public Buku ambilBuku(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BOOKS, new String[]{
                        KEY_ID, KEY_BOOK_NAME, KEY_AUTHOR, KEY_BOOK_NAME},
                KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Buku buku = new Buku(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getBlob(3));
        cursor.close();
        // return buku
        return buku;
    }

    // ambil semua daftar buku
    public ArrayList<Buku> ambilSemuaBuku() {
        ArrayList<Buku> daftarBuku = new ArrayList<Buku>();
        // query untuk pilih semua
        String selectQuery = "SELECT * FROM " + TABLE_BOOKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping melalui semua baris dan ditambahkan pada list
        if (cursor.moveToFirst()) {
            do {
                Buku buku = new Buku();
                buku.set_id(Integer.parseInt((cursor.getString(0))));
                buku.set_nama_buku(cursor.getString(1));
                buku.set_nama_pengarang(cursor.getString(2));
                buku.set_gambar_buku(cursor.getBlob(3));
                // menambah buku pada list
                daftarBuku.add(buku);
            } while (cursor.moveToNext());
        }

        return daftarBuku;
    }

    // mengetahui jumlah buku
    public int ambilJumlahBuku() {
        String countQuery = "SELECT * FROM " + TABLE_BOOKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int nilai = cursor.getCount();
        cursor.close();

        return nilai;
    }

    public int updateBuku(Buku buku) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BOOK_NAME, buku.get_nama_buku());
        values.put(KEY_AUTHOR, buku.get_nama_pengarang());
        values.put(KEY_IMAGE, buku.get_gambar_buku());

        return db.update(TABLE_BOOKS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(buku.get_id())});
    }

    public void hapusBuku(String nama_buku) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOOKS, KEY_BOOK_NAME + " = ?",
                new String[]{nama_buku});
        Log.d("isi id", ""+nama_buku);
        db.close();
    }
}
