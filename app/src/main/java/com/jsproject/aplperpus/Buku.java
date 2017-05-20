package com.jsproject.aplperpus;

/**
 * Created by J on 17/04/2016.
 */
public class Buku {
    int _id;
    String _nama_buku;
    String _nama_pengarang;
    byte[] _gambar_buku;

    public Buku(){
        // constructor kosong
    }

    public Buku(int _id, String _nama_buku, String _nama_pengarang,
                byte[] _gambar_buku) {
        // constructor
        this._id = _id;
        this._nama_buku = _nama_buku;
        this._nama_pengarang = _nama_pengarang;
        this._gambar_buku = _gambar_buku;
    }

    public Buku(String _nama_buku, String _nama_pengarang,
                byte[] _gambar_buku) {
        // constructor
        this._nama_buku = _nama_buku;
        this._nama_pengarang = _nama_pengarang;
        this._gambar_buku = _gambar_buku;
    }

    // getter and setter dari variabel diatas

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_nama_buku() {
        return _nama_buku;
    }

    public void set_nama_buku(String _nama_buku) {
        this._nama_buku = _nama_buku;
    }

    public String get_nama_pengarang() {
        return _nama_pengarang;
    }

    public void set_nama_pengarang(String _nama_pengarang) {
        this._nama_pengarang = _nama_pengarang;
    }

    public byte[] get_gambar_buku() {
        return _gambar_buku;
    }

    public void set_gambar_buku(byte[] _gambar_buku) {
        this._gambar_buku = _gambar_buku;
    }
}