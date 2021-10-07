package com.get.dia.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.get.dia.R;
import com.get.dia.objetos.Agenda;
import com.get.dia.ui.agenda.AgendaAdapter;
import com.get.dia.ui.timePicker.TimePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;


public class EvtsDialog extends AppCompatDialogFragment{

    private RecyclerView recyclerView;
    private static ArrayList<Agenda> lista = new ArrayList<>();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_eventos, null);
        recyclerView = view.findViewById(R.id.recycler_evts);
        AnimationDrawable animationDrawable = (AnimationDrawable) view.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
        AgendaAdapter adapter2 = new AgendaAdapter(getContext(), lista);
        recyclerView = view.findViewById(R.id.recycler_evts);
        recyclerView.setAdapter(adapter2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter2.setOnItemClickListener(new AgendaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mudarItem(position, "X");
            }

            @Override
            public void onDeleteClick(int position) {
                removerItem(position);
            }
        });
        builder.setView(view)
                .setTitle("Lista de eventos")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        return builder.create();
    }

    private void removerItem(int position) {
    }

    private void mudarItem(int position, String x) {
    }

    public static void setLista(ArrayList<Agenda> list){
        lista.clear();
        lista.addAll(list);
    }
}
