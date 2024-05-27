package com.example.semocavi2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.semocavi2.R;
import com.example.semocavi2.models.MiniCursoModel;

import java.util.List;

public class PalestraAdapter extends RecyclerView.Adapter<MiniCursoAdapter.MiniCursoViewHolder> {

    private List<MiniCursoModel> palestraList;

    public void setPalestraList(List<MiniCursoModel> palestraList) {
        this.palestraList = palestraList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public PalestraAdapter.PalestraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout., parent, false);
        return new MiniCursoAdapter.MiniCursoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiniCursoAdapter.MiniCursoViewHolder holder, int position) {
        MiniCursoModel minicurso = palestraList.get(position);
        holder.nomeTextView.setText(minicurso.getNome());
        holder.descricaoTextView.setText(minicurso.getDescricao());
    }

    @Override
    public int getItemCount() {
        return palestraList != null ? palestraList.size() : 0;
    }

    static class PalestraViewHolder extends RecyclerView.ViewHolder {
        TextView nomeTextView;
        TextView descricaoTextView;

        public PalestraViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.text_nome);
            descricaoTextView = itemView.findViewById(R.id.text_descricao);
        }
    }
}
