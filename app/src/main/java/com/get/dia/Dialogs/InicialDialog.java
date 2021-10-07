package com.get.dia.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.get.dia.DAO.DAOUser;
import com.get.dia.MainActivity;
import com.get.dia.R;
import com.get.dia.database.DadosSQL;
import com.get.dia.objetos.Usuario;
import com.get.dia.ui.loads.LoadActivity;

public class InicialDialog extends AppCompatDialogFragment {

    private EditText textNome;
    private EditText textEscola;
    private RadioGroup grupo;
    private RadioButton radioButton;
    private SQLiteDatabase conexao;
    private SQLiteOpenHelper dadosSQL;
    private TextView conta;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_inicial, null);
        AnimationDrawable animationDrawable = (AnimationDrawable) view.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
        textEscola = view.findViewById(R.id.editEscola);
        textNome = view.findViewById(R.id.editNome);
        builder.setView(view)
                .setTitle("Primeiro Acesso")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Usuario user = new Usuario(1, textNome.getText().toString(), textEscola.getText().toString(),MainActivity.materia, 2);
                        dadosSQL = new DadosSQL(getActivity());
                        conexao = dadosSQL.getWritableDatabase();
                        DAOUser dao = new DAOUser(conexao);
                        dao.atualizar(user);
                        conta = getActivity().findViewById(R.id.conta);
                        conta.setText(user.getNome() + " " + user.getEscola());
                        LoadActivity.user = user;
                    }
                });
        return builder.create();
    }

}
