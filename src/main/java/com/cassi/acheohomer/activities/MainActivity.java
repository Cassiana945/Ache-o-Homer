package com.cassi.acheohomer.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.cassi.acheohomer.R;


public class MainActivity extends AppCompatActivity {

    ImageView btnIniciar, btnHistorico;
    TextView txtMaiorPontuacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnIniciar = findViewById(R.id.iniciar);
        btnHistorico = findViewById(R.id.historico);
        txtMaiorPontuacao = findViewById(R.id.pontos);


        SharedPreferences prefs = getSharedPreferences("recorde", MODE_PRIVATE);
        int maiorPontuacao = prefs.getInt("recorde",0);
        txtMaiorPontuacao.setText(String.valueOf(maiorPontuacao));


        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        btnHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoricoActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

    }
}
