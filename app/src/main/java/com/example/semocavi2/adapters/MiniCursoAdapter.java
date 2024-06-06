package com.example.semocavi2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.semocavi2.R;
import com.example.semocavi2.models.MiniCursoModel;

import java.util.List;


/**
 * Adapter para exibir uma lista de minicursos em um RecyclerView.
 /**   
public class MiniCursoAdapter extends RecyclerView.Adapter<MiniCursoAdapter.MiniCursoViewHolder> {

    /**
     * Modelo de minicurso atual.
     */

    private MiniCursoModel minicurso;

     /**
     * Lista de modelos de minicurso.
     */
    private List<MiniCursoModel> minicursoList;

    /**
     * Listener para eventos de clique nos itens.
     */
    private OnItemClickListener listener;
    /**
     * Interface para lidar com cliques nos itens.
     */

    public interface OnItemClickListener{
       /**
         * Método chamado quando um item é clicado.
        */
        void onItemClick(MiniCursoModel miniCursoModel);
    }

     /**
     * Define o listener para cliques nos itens.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

     /**
     * Define a lista de minicursos e notifica o adapter para atualizar a exibição.
     */
        
    public void setMinicursoList(List<MiniCursoModel> minicursoList) {
        this.minicursoList = minicursoList;
        notifyDataSetChanged();
    }

     /**
     * Cria novos view holders para o RecyclerView.
     */

    @NonNull
    @Override
    public MiniCursoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_minicurso, parent, false);
        return new MiniCursoViewHolder(view);
    }

     /**
     * Vincula os dados do minicurso ao view holder.
     */

    @Override
    public void onBindViewHolder(@NonNull MiniCursoViewHolder holder, int position) {
         minicurso = minicursoList.get(position);
        holder.nomeTextView.setText(minicurso.getNome());
        holder.dateTextView.setText(minicurso.getData());
        holder.horariosTextView.setText(minicurso.getHora());
    }

     /**
     * Retorna o número de itens na lista de minicursos.
     * 
     * @return O número de itens na lista.
     */

    @Override
    public int getItemCount() {
        return minicursoList != null ? minicursoList.size() : 0;
    }

    /**
     * ViewHolder para exibir informações de um minicurso.
     */

     class MiniCursoViewHolder extends RecyclerView.ViewHolder {
        TextView nomeTextView,  horariosTextView, dateTextView;

          /**
         * Construtor do ViewHolder.
         */

        public MiniCursoViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.text_nome);
            dateTextView = itemView.findViewById(R.id.text_data);
            horariosTextView = itemView.findViewById(R.id.text_horario);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos =  getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(minicursoList.get(pos));
                    }

                }
            });

        }
    }
}
