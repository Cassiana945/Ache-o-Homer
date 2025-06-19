package com.cassi.acheohomer.activities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cassi.acheohomer.R;
import com.cassi.acheohomer.database.PontuacaoDatabase;
import com.cassi.acheohomer.model.Pontuacao;

public class GameActivity extends AppCompatActivity {

    TextView txtPontos, txtTempo;
    ImageView homer;
    MediaPlayer playerDoh;
    int pontos = 0;
    PontuacaoDatabase bdPontos;
    Random r = new Random();
    RelativeLayout main;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        txtPontos = findViewById(R.id.pontos);
        txtTempo = findViewById(R.id.tempo);
        homer = findViewById(R.id.homer);
        main = findViewById(R.id.layout);
        playerDoh = MediaPlayer.create(GameActivity.this, R.raw.doh);



        bdPontos = new PontuacaoDatabase(this);

        homer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homer.setImageResource(R.drawable.homer_doh);
                playerDoh.start();
                homer.setEnabled(false);
                homer.setVisibility(View.VISIBLE);
                pontos++;
                txtPontos.setText(String.valueOf(pontos));

            }
        });

        mostrarHomerAleatorio();

    }

    private void mostrarHomerAleatorio() {
        Handler homerHandler = new Handler();

        new Thread(() -> {
            long startTime = System.currentTimeMillis();
            long duration = 60000;

            while (System.currentTimeMillis() - startTime < duration) {
                long elapsed = System.currentTimeMillis() - startTime;
                int secondsLeft = (int) ((duration - elapsed) / 1000);

                homerHandler.post(() -> {
                    txtTempo.setText(String.valueOf(secondsLeft));

                    homer.setImageResource(R.drawable.homer);
                    homer.setVisibility(View.VISIBLE);
                    homer.setEnabled(true);

                    int maxX = main.getWidth() - homer.getWidth();
                    int maxY = main.getHeight() - homer.getHeight() - 200;

                    float randomX = r.nextInt(Math.max(1, maxX));
                    float randomY = r.nextInt(Math.max(1, maxY));

                    homer.setX(randomX);
                    homer.setY(randomY);
                });

                SystemClock.sleep(1000);
            }

            homerHandler.post(() -> {
                homer.setVisibility(View.INVISIBLE);
                gameOver();
            });

        }).start();
    }

    private void gameOver() {
        boolean recorde = false;
        String data = dataAtual();
        int maiorPontuacao = bdPontos.maiorPontuacao();

        if (pontos > maiorPontuacao) {
            recorde = true;
            SharedPreferences.Editor editor = getSharedPreferences("recorde", MODE_PRIVATE).edit();
            editor.putInt("recorde", pontos);
            editor.apply();
        }
        Pontuacao pontuacao = new Pontuacao(data, pontos, recorde);
        bdPontos.addPontuacao(pontuacao);

        new AlertDialog.Builder(this)
                .setTitle("Game Over!")
                .setMessage("pontuação: " +pontos)
                .setCancelable(false)
                .setNegativeButton("Sair",((dialog, which) -> {
                    startActivity(new Intent(this, MainActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }))
                .setPositiveButton("Novo Jogo", (dialog, which) -> {
                    Intent i = new Intent(this, GameActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                })
                .show();


    }

    String dataAtual() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }


}
