package com.get.dia.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.view.GravityCompat;

import com.get.dia.DAO.DAOAlarme;
import com.get.dia.DAO.DAOUser;
import com.get.dia.MainActivity;
import com.get.dia.R;
import com.get.dia.database.DadosSQL;
import com.get.dia.notifications.AlertReceiver;
import com.get.dia.objetos.Alarme;
import com.get.dia.objetos.Usuario;
import com.get.dia.ui.loads.LoadActivity;
import com.get.dia.ui.tempo.TempoFragment;

import static com.get.dia.MainActivity.texto;

public class AlarmDialog extends AppCompatDialogFragment {

    private EditText textDesc;
    private SQLiteDatabase conexao;
    private SQLiteOpenHelper dadosSQL;
    private TextView conta;
    private DAOAlarme daoAlarme = new DAOAlarme(LoadActivity.conexao);

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_alarme, null);
        AnimationDrawable animationDrawable = (AnimationDrawable) view.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
        textDesc = view.findViewById(R.id.editDesc);
        builder.setView(view)
                .setTitle("Descrição do alarme")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Alarme obj = new Alarme();
                            int total = daoAlarme.getDados(getContext()).size();
                            if(total!=0) {
                                obj.setIdAlarme(daoAlarme.getDados(getContext()).get(total - 1).getIdAlarme() + 1);
                            }else{
                                obj.setIdAlarme(1);
                            }
                            obj.setDatahora(texto);
                            obj.setUsuario(LoadActivity.user.getIdUser());
                            obj.setDescricao(textDesc.getText().toString());
                            daoAlarme.incluir(obj);
                            Toast.makeText(getContext(), "Alarme definido com sucesso", Toast.LENGTH_LONG).show();
                            MainActivity.atualizarTempo = true;
                            AlertReceiver.desc = textDesc.getText().toString();
                            AlertReceiver.nome = "Alarme Ativado";
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        return builder.create();
    }
}
