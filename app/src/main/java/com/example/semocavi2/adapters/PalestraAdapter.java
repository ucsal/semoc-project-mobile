package com.example.semocavi2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.semocavi2.R;
import com.example.semocavi2.models.PalestraModel;
import com.example.semocavi2.ui.palestra.PalestraViewModel;

import java.util.List;

/**
 * Adapter para exibir uma lista de palestras em um RecyclerView.
 */

public class PalestraAdapter  extends RecyclerView.Adapter<PalestraAdapter.PalestraViewHolder>
{

     /**
     * Modelo de palestra atual.
     */

    private PalestraModel palestraModel;
     /**
     * Lista de modelos de palestras.
     */
    private List<PalestraModel> palestraList;
     /**
     * Listener para eventos de clique nos itens.
     */
    private OnItemClickListener listener;
     /**
     * Interface para lidar com cliques nos itens.
     */

    @NonNull
    @Override
    public PalestraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_palestra, parent, false);
    return new PalestraViewHolder(view);
    }
/**
 * ViewHolder para exibir informações de uma palestra.
 */


    @Override
    public void onBindViewHolder(@NonNull PalestraViewHolder holder, int position) {
        palestraModel = palestraList.get(position);
        holder.nomeTextView.setText(palestraModel.getNome());
        holder.dateTextView.setText((palestraModel.getData()));
        holder.horariosTextView.setText(palestraModel.getHora());
    }


    @Override
    public int getItemCount() {
        return palestraList != null? palestraList.size(): 0;
    }


    public interface OnItemClickListener{
        void onItemClick(PalestraModel palestraModel);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public void setPalestraList(List<PalestraModel> palestraList) {
        this.palestraList = palestraList;
        notifyDataSetChanged();
    }


     class PalestraViewHolder extends RecyclerView.ViewHolder {
         TextView nomeTextView,  horariosTextView, dateTextView;


         public PalestraViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.text_nome);
            dateTextView = itemView.findViewById(R.id.text_data);
            horariosTextView = itemView.findViewById(R.id.text_horario);

             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     int pos =  getAdapterPosition();
                     if (pos != RecyclerView.NO_POSITION && listener != null) {
                         listener.onItemClick(palestraList.get(pos));
                     }

                 }
             });
        }
    }
}
