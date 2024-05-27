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

public class MiniCursoAdapter extends RecyclerView.Adapter<MiniCursoAdapter.MiniCursoViewHolder> {

    private List<MiniCursoModel> minicursoList;

    public void setMinicursoList(List<MiniCursoModel> minicursoList) {
        this.minicursoList = minicursoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MiniCursoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_minicurso, parent, false);
        return new MiniCursoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiniCursoViewHolder holder, int position) {
        MiniCursoModel minicurso = minicursoList.get(position);
        holder.nomeTextView.setText(minicurso.getNome());
        holder.dateTextView.setText(minicurso.getData());
//        holder.descricaoTextView.setText(minicurso.getDescricao());
        holder.horariosTextView.setText(minicurso.getHora());
    }

    @Override
    public int getItemCount() {
        return minicursoList != null ? minicursoList.size() : 0;
    }

    static class MiniCursoViewHolder extends RecyclerView.ViewHolder {
        TextView nomeTextView;
//        TextView descricaoTextView;

        TextView horariosTextView;
        TextView dateTextView;

        public MiniCursoViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.text_nome);
//            descricaoTextView = itemView.findViewById(R.id.text_descricao);
            dateTextView = itemView.findViewById(R.id.text_data);
            horariosTextView = itemView.findViewById(R.id.text_horario);
        }
    }
}
