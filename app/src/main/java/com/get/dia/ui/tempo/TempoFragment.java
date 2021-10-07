package com.get.dia.ui.tempo;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.get.dia.DAO.DAOAlarme;
import com.get.dia.DAO.DAOUser;
import com.get.dia.R;
import com.get.dia.notifications.AlertReceiver;
import com.get.dia.objetos.Alarme;
import com.get.dia.ui.loads.LoadActivity;
import com.get.dia.ui.timePicker.TimePickerFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TempoFragment extends Fragment implements TimePickerDialog.OnTimeSetListener{

    private RecyclerView recyclerView;
    private View vista,animation;
    private TextView text,noAlarm;
    private DAOAlarme daoAlarme = new DAOAlarme(LoadActivity.conexao);
    private ArrayList<Alarme> alarmes = new ArrayList<>();
    private ArrayList<Alarme> alarmesBD = new ArrayList<>();
    private ArrayList<Alarme> alarmesMod = new ArrayList<>();
    private TempoAdapter adapter;
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        c.set(Calendar.SECOND,0);
        iniciar(c);
        atualizaTempo(c);
    }
    public void iniciar(Calendar c ){
        AlarmManager alarm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),1,intent,0);
        alarm.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
    }

    private void atualizaTempo(Calendar c){
        String texto = "Alarme definido para: ";
        texto+= DateFormat.getTimeInstance(DateFormat.SHORT).format(c);
        text.setText(texto);
        Alarme obj = new Alarme();
        obj.setDatahora(texto);
        obj.setUsuario(LoadActivity.user.getIdUser());
        obj.setDescricao("Teste de alarmes dia 0.5.2");
        //daoAlarme.incluir(obj);
        System.out.println("FRAGMENT ALARME");
        System.out.println("ALARME"+texto);
    }
    public void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this.getContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getContext(), 1, intent, 0);
        text = vista.findViewById(R.id.txtT);
        alarmManager.cancel(pendingIntent);
        text.setText("Alarme cancelado");
    }
    public ArrayList<Alarme> getAlarmes(){
        atualizarAlarmes();
        Date dataHoraAtual = new Date();
        System.out.println("Tempo teste "+ dataHoraAtual);
        Date dateBD=null;
        int i = 0;
        for (Alarme a:alarmesBD) {
            if(a.getDatahora()!=null) {
                dateBD = stringToDate(a.getDatahora());
            }
            System.out.println("DATE BD "+dateBD);
            if(dateBD!=null) {
                if (dataHoraAtual.after(dateBD)) {
                    Alarme alarme = alarmesBD.get(i);
                    alarme.setDatahora("Concluido em: \n" + a.getDatahora());
                    alarmesMod.add(alarme);
                    i++;
                }
                else {
                    Alarme alarme = alarmesBD.get(i);
                    alarme.setDatahora("Agendado para: \n" + a.getDatahora());
                    alarmesMod.add(alarme);
                    i++;
                }
            }
        }
        ArrayList<Alarme> auxiliar = new ArrayList<>();
        for(int j=alarmesMod.size()-1;j>=0;j--){
           auxiliar.add(alarmesMod.get(j));
        }
        alarmes = auxiliar;
        return alarmes;
    }
    private Date stringToDate(String aDate) {
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM d HH:mm:ss zz yyyy",Locale.US);
        try {
            Date date = format.parse(aDate);
            System.out.println("Formatei meno "+date);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void mudarItem(int position,String text){
        try {
            getAlarmes().get(position).setDatahora(" " + text + " " + getAlarmes().get(position).getDatahora());
            DAOUser daou = new DAOUser(LoadActivity.conexao);
            adapter.notifyItemChanged(position);
            AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
            dlg.setTitle("Descrição do alarme");
            dlg.setMessage("Autor: " + daou.getDados().getNome() + "\n" +
                    "Descrição: " + getAlarmes().get(position).getDescricao() + "\n" +
                    "Data e hora: " + getAlarmes().get(position).getDatahora());
            dlg.setPositiveButton("Ok", null);
            dlg.show();
            System.out.println("adapter changed");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void removerItem(final int position){
        try{
        AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
        dlg.setTitle("Exclusão do alarme");
        dlg.setMessage("Deseja excluir o alarme ? \n"+
                "Descrição: "+getAlarmes().get(position).getDescricao()+"\n" +
                "Data e hora: "+getAlarmes().get(position).getDatahora());
        dlg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                daoAlarme.excluir(getAlarmes().get(position).getIdAlarme());
                alarmes.clear();
                alarmesMod.clear();
                alarmesBD.clear();
                criarView();
            }
        });
        dlg.setNegativeButton("Não",null);
        dlg.show();
    }catch(Exception e){
        e.printStackTrace();
    }}

    public boolean criarView(){
        try {
            adapter = new TempoAdapter(getContext(), getAlarmes());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter.setOnItemClickListener(new TempoAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mudarItem(position,"X");
                }

                @Override
                public void onDeleteClick(int position) {
                    removerItem(position);
                }


            });
        }catch(Exception e){
            System.out.println("Erro na criação de view de alarmes");
            e.printStackTrace();
        }
        return true;
    }
    public static boolean tempo;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tempo, container, false);
        vista = root;
        noAlarm = vista.findViewById(R.id.semAlarmes);
        animation = vista.findViewById(R.id.animationAlarme);
        text = vista.findViewById(R.id.txtT);
        recyclerView = vista.findViewById(R.id.reciclerView);
        tempo = true;
        if(daoAlarme.getDados(getContext())!=null && daoAlarme.getDados(getContext()).size()>0) {
            atualizarAlarmes();
            animation.setVisibility(View.INVISIBLE);
            noAlarm.setVisibility(View.INVISIBLE);
            criarView();
        }else if(daoAlarme.getDados(getContext()).size()==0){
            animation.setVisibility(View.VISIBLE);
            noAlarm.setVisibility(View.VISIBLE);
        }
        Button buttonDefinir = vista.findViewById(R.id.buttonDefinir);
        Button buttonCronometro = vista.findViewById(R.id.buttonCronometro);
        buttonDefinir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment time = new TimePickerFragment();
                time.show(getActivity().getSupportFragmentManager(),"Selecionador de tempo");
            }
        });

        buttonCronometro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cronometro();
            }
        });

        Button buttonCancelAlarm = vista.findViewById(R.id.buttonCancelar);
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    cancelAlarm();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        return vista;
    }
    public void atualizarAlarmes(){
        try {
            for (Alarme a:
                    daoAlarme.getDados(getContext())){
                    System.out.println("Resultado do get: "+a.getDatahora());
            }
            alarmesBD.clear();
            alarmesBD.addAll(daoAlarme.getDados(getContext()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void cronometro(){
        try {
            final int[] i = {0};
            final boolean[] start = new boolean[1];
            start[0] = true;
            final AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
            dlg.setTitle("Cronometro");
            dlg.setMessage("" + i[0]);
            dlg.setPositiveButton("Começar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    start[0] = true;
                }
            });
            dlg.setNegativeButton("Parar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    start[0] = false;
                }
            });
            dlg.show();
            }catch(Exception e){
                e.printStackTrace();
            }

    }
}


//Muitos erros no time reverificar as listas que estao bagunçadas