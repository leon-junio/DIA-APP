package com.get.dia.ui.agenda;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.get.dia.DAO.DAOAgenda;
import com.get.dia.DAO.DAOUser;
import com.get.dia.Dialogs.AgendaDialog;
import com.get.dia.Dialogs.AlarmDialog;
import com.get.dia.Dialogs.EvtsDialog;
import com.get.dia.R;
import com.get.dia.objetos.Agenda;
import com.get.dia.ui.loads.LoadActivity;
import com.get.dia.ui.scanner.ScannerAdapter;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class AgendaFragment extends Fragment {

    private ArrayList<Agenda> lista = new ArrayList<>();
    private ArrayList<Agenda> eventosDoDia = new ArrayList<>();
    private CompactCalendarView calendarView;
    private TextView txtag;
    private SimpleDateFormat dateBr = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private SimpleDateFormat dateHoraBr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private SimpleDateFormat dateInfo = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());
    private View vista;
    private RecyclerView recyclerView,recyclerView2;
    private AgendaAdapter adapter;
    private FloatingActionButton btCriar;
    private Button btEventos;
    private DAOAgenda dao = new DAOAgenda(LoadActivity.conexao);
    private String diaEscolhido;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_agenda, container, false);
        recyclerView = vista.findViewById(R.id.recycler_ag);
        recyclerView2 = vista.findViewById(R.id.recycler_evts);
        txtag = vista.findViewById(R.id.txt_agenda);
        btCriar = vista.findViewById(R.id.bt_criar);
        btEventos = vista.findViewById(R.id.bt_eventos);
        calendarView = vista.findViewById(R.id.compactcalendar_view);
        calendarView.setUseThreeLetterAbbreviation(true);
        calendarView.setFirstDayOfWeek(Calendar.MONDAY);
        calendarView.setLocale(TimeZone.getDefault(), Locale.getDefault());
        final Date atual = new Date();
        atual.setHours(0);
        atual.setMinutes(0);
        atual.setSeconds(0);
        txtag.setText(dateInfo.format(atual));
        diaEscolhido = dateBr.format(atual);
        getEventosDia(diaEscolhido);
        criarView();
        getLista();
        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                try {
                    diaEscolhido = dateBr.format(dateClicked);
                    Context context = getContext();
                    System.out.println(diaEscolhido);
                    System.out.println(atual);
                    getEventosDia(diaEscolhido);
                    criarView();
                    int soma = 0;
                    for (Agenda a : eventosDoDia) {
                        if (dateBr.format(dateClicked).compareTo(a.getData()) == 0) {
                            soma++;
                        }
                    }
                    Toast.makeText(context, soma + " Eventos nesse dia", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                try {
                    txtag.setText(dateInfo.format(firstDayOfNewMonth));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                criarViewEvts();
            }
        });
        btCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CRIAR FRAGMENT
                try {
                    final AgendaDialog ag = new AgendaDialog();
                    ag.show(getActivity().getSupportFragmentManager(), "Agenda");
                    AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                    dlg.setTitle("Lista de eventos");
                    dlg.setMessage("Seus eventos foram atualizados com sucesso!");
                    dlg.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (ag.getEscolha()) {
                                System.out.println("Estamos incluindo");
                                Agenda evento = new Agenda();
                                evento.setNotificacao(ag.getEvento());
                                Date alfa = ag.getHoras().getTime();
                                int a = alfa.getHours(),b = alfa.getMinutes(), c = alfa.getSeconds();
                                String horas = a+":"+b+":"+c+".0";
                                evento.setData(diaEscolhido);
                                evento.setHora(horas);
                                if (dao.getDados().size() != 0) {
                                    evento.setIdAgenda(dao.getDados().get(dao.getDados().size() - 1).getIdAgenda() + 1);
                                } else {
                                    evento.setIdAgenda(1);
                                }
                                dao.incluir(evento);
                                getEventosDia(diaEscolhido);
                                java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(evento.getData()+" "+horas);
                                Event ev1 = new Event(Color.RED,timestamp.getTime(),evento);
                                calendarView.addEvent(ev1);
                                criarView();
                                lista.clear();
                                lista = dao.getDados();
                            } else {
                                getEventosDia(diaEscolhido);
                                criarView();
                            }
                        }
                    });
                    dlg.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return vista;
    }

    public void getEventosDia(String dia) {
        eventosDoDia.clear();
        if(dao.getDados() != null) {
            if (dao.getEventosDoDia(dia) != null) {
                eventosDoDia.addAll(dao.getEventosDoDia(dia));
            }
        }
    }

    public void compararDia() {

    }

    public ArrayList<Agenda> getLista() {
        try {
            lista.clear();
            lista = dao.getDados();
            for(Agenda a:lista){
                java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(a.getData()+" "+a.getHora());
                Event ev1 = new Event(Color.RED,timestamp.getTime(),a);
                calendarView.addEvent(ev1);
            }
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean criarView() {
        try {
            if (eventosDoDia.size() == 0) {
                Agenda a = new Agenda();
                a.setNotificacao("Nada agendado para esse dia");
                a.setData("Sem horários");
                a.setHora("!");
                eventosDoDia.add(a);
            }
            adapter = new AgendaAdapter(getContext(), eventosDoDia);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter.setOnItemClickListener(new AgendaAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mudarItem(position, "X");
                }

                @Override
                public void onDeleteClick(int position) {
                    removerItem(position);
                }
            });
        } catch (Exception e) {
            System.out.println("Erro na criação de view de agenda");
            e.printStackTrace();
        }
        return true;
    }

    public boolean criarViewEvts() {
        try {
            final EvtsDialog ev = new EvtsDialog();
            ev.show(getActivity().getSupportFragmentManager(), "Dialog de Eventos");
            if (lista.size() == 0) {
                Agenda a = new Agenda();
                a.setNotificacao("Nenhum evento foi criado até agora");
                a.setData("Crie horários e defina eventos para ver eles por aqui");
                a.setHora("!");
                lista.add(a);
            }
            EvtsDialog.setLista(lista);
        } catch (Exception e) {
            System.out.println("Erro na criação de view de agenda");
            e.printStackTrace();
        }
        return true;
    }

    public void mudarItem(int position, String text) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removerItem(final int position) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

