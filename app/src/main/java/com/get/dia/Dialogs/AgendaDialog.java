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

import com.get.dia.R;
import com.get.dia.ui.timePicker.TimePickerFragment;

import java.util.Calendar;


public class AgendaDialog extends AppCompatDialogFragment implements TimePickerDialog.OnTimeSetListener{

    private EditText textAg;
    private boolean escolha;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_agenda, null);
        AnimationDrawable animationDrawable = (AnimationDrawable) view.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
        textAg = view.findViewById(R.id.editAgenda);
        textAg.setText("");
        builder.setView(view)
                .setTitle("Descrição do evento")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            escolha = true;
                            DialogFragment time = new TimePickerFragment();
                            time.show(getActivity().getSupportFragmentManager(),"Selecionador para agenda");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    escolha = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return builder.create();
    }

    public String getEvento(){
        return textAg.getText().toString();
    }

    public boolean getEscolha(){
        return escolha;
    }

    public static Calendar horarioAgenda;

    public Calendar getHoras(){
        return horarioAgenda;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        horarioAgenda = Calendar.getInstance();
        horarioAgenda.set(Calendar.HOUR_OF_DAY, hourOfDay);
        horarioAgenda.set(Calendar.MINUTE, minute);
        horarioAgenda.set(Calendar.SECOND, 0);
    }
}
