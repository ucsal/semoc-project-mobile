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

public class PalestraAdapter  extends RecyclerView.Adapter<PalestraAdapter.PalestraViewHolder>
{

    private PalestraModel palestraModel;
    private List<PalestraModel> palestraList;
    private OnItemClickListener listener;

    @NonNull
    @Override
    public PalestraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_palestra, parent, false);
    return new PalestraViewHolder(view);
    }


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


        }
    }
}
