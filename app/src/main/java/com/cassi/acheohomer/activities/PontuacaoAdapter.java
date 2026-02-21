package com.cassi.acheohomer.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cassi.acheohomer.R;
import com.cassi.acheohomer.model.Pontuacao;

import java.util.ArrayList;

public class PontuacaoAdapter extends RecyclerView.Adapter<PontuacaoAdapter.ViewHolder> {

    Context contexto;
    ArrayList<Pontuacao> pontos;

    public PontuacaoAdapter(Context contexto, ArrayList<Pontuacao> pontos) {
        this.pontos = pontos;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public PontuacaoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(contexto)
                .inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PontuacaoAdapter.ViewHolder holder, int position) {
        Pontuacao pontuacao = pontos.get(position);
        holder.txtPonto.setText(String.valueOf(pontuacao.getPontos()));
        if(pontuacao.isRecorde()){
            holder.txtRecorde.setText("sim");
        } else{
            holder.txtRecorde.setText("não");
        }
        holder.txtData.setText(pontuacao.getData());

    }

    @Override
    public int getItemCount() {
        return pontos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtPonto, txtRecorde, txtData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPonto = itemView.findViewById(R.id.pontos);
            txtRecorde = itemView.findViewById(R.id.recorde);
            txtData = itemView.findViewById(R.id.data);
        }
    }
}
