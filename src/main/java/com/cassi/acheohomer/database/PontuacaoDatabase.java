package com.cassi.acheohomer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.cassi.acheohomer.model.Pontuacao;

import java.util.ArrayList;
import java.util.List;

public class PontuacaoDatabase extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private static final String NOME_BD = "Simpsons";
    private static final String C_ID = "id";
    private static final String C_DATA = "data";
    private static final String C_PONTOS = "pontos";
    private static final String C_RECORDE = "recorde";
    private static final String TB_PONTUACAO = "pontuacao";
    public static Context contexto;

    public PontuacaoDatabase(@Nullable Context context) {
        super(context, NOME_BD, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TB_PONTUACAO + "( " +
                C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                C_DATA + " TEXT, " +
                C_PONTOS + " INTEGER, " +
                C_RECORDE + " BOOLEAN)";

        db.execSQL(query);
    }

    public void addPontuacao(Pontuacao pontuacao) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_DATA, pontuacao.getData());
        values.put(C_PONTOS, pontuacao.getPontos());
        values.put(C_RECORDE, pontuacao.isRecorde());

        db.insert(TB_PONTUACAO, null, values);
        db.close();
    }

    public List<Pontuacao> findAllPontuacao(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Pontuacao> pontos = new ArrayList<>();
        String query = "SELECT * FROM "+TB_PONTUACAO+ " ORDER BY " +C_PONTOS+ " DESC";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do {
                Pontuacao pontuacao = new Pontuacao(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3) > 0
                );
                pontos.add(pontuacao);
            } while (cursor.moveToNext());
        }
        db.close();
        return pontos;
    }

    public int maiorPontuacao() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String query = "SELECT MAX(" + C_PONTOS + ") FROM " + TB_PONTUACAO;
            cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
            return 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
