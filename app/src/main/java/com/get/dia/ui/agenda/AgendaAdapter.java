package com.get.dia.ui.agenda;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.get.dia.R;
import com.get.dia.objetos.Agenda;
import java.util.ArrayList;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.MyView> {

    private ArrayList<Agenda> listaAgenda;
    Context context;
    private AgendaAdapter.OnItemClickListener clicklistener;


    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(AgendaAdapter.OnItemClickListener listener){
        clicklistener = listener;
    }

    public AgendaAdapter(Context ct, ArrayList<Agenda> agenda){
        context = ct;
        listaAgenda = agenda;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_agenda,parent,false);
        Button btDeleteEvt;
        btDeleteEvt = view.findViewById(R.id.bt_deleteAg);
        if(listaAgenda.get(0).getNotificacao().equals("Nada agendado para esse dia")){
            btDeleteEvt.setVisibility(View.INVISIBLE);
        }
        return new AgendaAdapter.MyView(view,clicklistener);
    }


    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        try {
            Agenda agenda = listaAgenda.get(position);
            holder.text1.setText(agenda.getNotificacao());
            holder.text2.setText(agenda.getData()+" às "+ agenda.getHora());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listaAgenda.size();
    }


    public static class MyView extends RecyclerView.ViewHolder{
        TextView text1,text2;
        Button btDeleteEvt;
        public MyView(@NonNull View itemView, final AgendaAdapter.OnItemClickListener listener) {
            super(itemView);
            text1 = itemView.findViewById(R.id.txt_agendamento);
            text2 = itemView.findViewById(R.id.txt_descagenda);
            btDeleteEvt = itemView.findViewById(R.id.bt_deleteAg);
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
            btDeleteEvt.setOnClickListener(new View.OnClickListener() {
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
