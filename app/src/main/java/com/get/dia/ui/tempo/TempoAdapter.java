package com.get.dia.ui.tempo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.get.dia.R;
import com.get.dia.objetos.Alarme;

import java.util.ArrayList;


public class TempoAdapter extends RecyclerView.Adapter<TempoAdapter.MyView> {
    private ArrayList<Alarme> listaAlarmes;
    Context context;
    private OnItemClickListener clicklistener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        clicklistener = listener;
    }

    public TempoAdapter(Context ct, ArrayList<Alarme> alarmes){
        context = ct;
        listaAlarmes = alarmes;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_alarme,parent,false);
        return new MyView(view,clicklistener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        Alarme al = listaAlarmes.get(position);
        holder.text1.setText(al.getDatahora());
        holder.text2.setText(al.getDescricao());
    }

    @Override
    public int getItemCount() {
        return listaAlarmes.size();
    }

    public static class MyView extends RecyclerView.ViewHolder{
        TextView text1,text2;
        Button btDelete;
        public MyView(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            text1 = itemView.findViewById(R.id.horaAlarme);
            text2 = itemView.findViewById(R.id.descricaoAlarme);
            btDelete = itemView.findViewById(R.id.buttonDelete);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
}
