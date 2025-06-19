package com.cassi.acheohomer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cassi.acheohomer.R;
import com.cassi.acheohomer.database.PontuacaoDatabase;
import com.cassi.acheohomer.model.Pontuacao;

import java.util.ArrayList;
import java.util.List;

public class HistoricoActivity extends AppCompatActivity {

    RecyclerView recycler;
    ImageView btnVoltar;
    PontuacaoAdapter adapter;
    List<Pontuacao> lista;
    PontuacaoDatabase dbPontos;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        dbPontos = new PontuacaoDatabase(this);
        recycler = findViewById(R.id.recycler);
        btnVoltar = findViewById(R.id.voltar);

        carregarHistorico();

        adapter = new  PontuacaoAdapter(HistoricoActivity.this, new ArrayList<>(lista));
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HistoricoActivity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });
    }

    private void carregarHistorico() {
        lista = dbPontos.findAllPontuacao();

    }
}
