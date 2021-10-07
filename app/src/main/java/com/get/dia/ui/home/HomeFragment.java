package com.get.dia.ui.home;


import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.get.dia.Dialogs.TutorialDialog;
import com.get.dia.MainActivity;
import com.get.dia.R;
import com.get.dia.ui.loads.LoadActivity;


public class HomeFragment extends Fragment {
    private View vista;
    private TextView conta;
    public static boolean idmat = false;
    public static int slide=1;
    public static int idDialog;
    private View animationWifi;
    private View webView;
    public static boolean back = false;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        vista = root;
        idDialog = R.layout.dialog_tutorial;
        animationWifi = vista.findViewById(R.id.animationWifi);
        AnimationDrawable animationDrawable = (AnimationDrawable) vista.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
        navegar();
        if (LoadActivity.user.getInicio() == 1) {
            openDialog();
            idmat = true;
        }
        if (dados.isAlive()) {
            dados.interrupt();
        } else {
            dados.start();
        }
        return vista;
    }

    /**
     * Thread usada para atualizar os dados do user enquanto ele usa o app
     */
    private Thread dados = new Thread() {
        @Override
        public void run() {
            super.run();
            try {
                final String nombre = LoadActivity.user.getNome();
                final String escuela = LoadActivity.user.getEscola();
                while (true) {
                    try {
                        conta = getActivity().findViewById(R.id.conta);
                    } catch (Exception e) {
                        System.out.println("Deu erro+" + e.getMessage());
                    }
                    if (LoadActivity.user.getInicio() != 1) {
                        if (conta != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    conta.setText(nombre + " " + escuela);
                                }
                            });
                            break;
                        }
                    }
                }
               idmat = false;
            } catch (Exception ex) {
                System.out.println("Thread 1 Error "+ ex.getMessage());
            }
        }
    };

    /**
     * Método para abrir uma caixa de dialogos no primeiro acesso
     */
    private void openDialog() {
        TutorialDialog tuto = new TutorialDialog();
        tuto.show(getActivity().getSupportFragmentManager(), "Tutorial");
    }


    /**
     * Método que retorna uma webview pronta pra uso de acordo com a
     * materia selecionada pelo user
     */
    private void navegar() {
        if (MainActivity.conectionState) {
            System.out.println("Conectado");
        } else {
            animationWifi.setVisibility(View.VISIBLE);
        }
    }
}