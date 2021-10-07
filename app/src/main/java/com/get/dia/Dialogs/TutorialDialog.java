package com.get.dia.Dialogs;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;
import com.get.dia.R;
import com.get.dia.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class TutorialDialog extends AppCompatDialogFragment {
    private FloatingActionButton proximo;
    private FloatingActionButton anterior;
    private static View view;
    private AlertDialog.Builder builder;
    private LayoutInflater inflater;
    private FragmentManager support;

    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        support = getActivity().getSupportFragmentManager();
        view = inflater.inflate(HomeFragment.idDialog, null);
        if(HomeFragment.slide!=8) {
            builder.setView(view)
                    .setTitle("Boas vindas e Tutorial")
                    .setPositiveButton("Pular", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            first();
                        }
                    });
        }else{
            builder.setView(view)
                    .setTitle("Boas vindas e Tutorial")
                    .setPositiveButton("Iniciar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            first();
                        }
                    });
        }
        proximo = view.findViewById(R.id.proximoBt2);
        anterior = view.findViewById(R.id.proximoBt);
        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HomeFragment.slide == 1) {
                    onDismiss(getDialog());
                    HomeFragment.slide++;
                    HomeFragment.idDialog = R.layout.tuto_2;
                    novaTela();
                } else if (HomeFragment.slide == 2) {
                    HomeFragment.slide++;
                    onDismiss(getDialog());
                    HomeFragment.idDialog = R.layout.tuto_3;
                    novaTela();
                } else if (HomeFragment.slide == 3) {
                    HomeFragment.slide++;
                    onDismiss(getDialog());
                    HomeFragment.idDialog = R.layout.tuto_4;
                    novaTela();
                }else if (HomeFragment.slide == 4) {
                    HomeFragment.slide++;
                    onDismiss(getDialog());
                    HomeFragment.idDialog = R.layout.tuto_5;
                    novaTela();
                }else if (HomeFragment.slide == 5) {
                    HomeFragment.slide++;
                    onDismiss(getDialog());
                    HomeFragment.idDialog = R.layout.tuto_6;
                    novaTela();
                }else if (HomeFragment.slide == 6) {
                    HomeFragment.slide++;
                    onDismiss(getDialog());
                    HomeFragment.idDialog = R.layout.tuto_7;
                    novaTela();
                }else if (HomeFragment.slide == 7) {
                    HomeFragment.slide++;
                    onDismiss(getDialog());
                    HomeFragment.idDialog = R.layout.tuto_8;
                    novaTela();
                }
            }
        });
        return builder.create();
    }

    /**
     * Método que chama um novo slide do tutorial
     */
    private void novaTela() {
        TutorialDialog tuto = new TutorialDialog();
        tuto.show(getActivity().getSupportFragmentManager(), "Tutorial");
    }


    /**
     * Método para retornar a tela de primeiro acesso
     *
     * @return se houve sucesso
     */
    private boolean first() {
        try {
            InicialDialog inicialDialog = new InicialDialog();
            inicialDialog.show(support, "Primeiro acesso");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}