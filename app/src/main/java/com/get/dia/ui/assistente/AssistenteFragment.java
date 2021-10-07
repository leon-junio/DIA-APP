package com.get.dia.ui.assistente;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.get.dia.R;
import com.get.dia.ui.loads.LoadActivity;


public class AssistenteFragment extends Fragment {
    private TextView resposta;
    private EditText campoText;
    private Button btenviar;
    private IA ia = new IA();
    private View vista;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_assistente, container, false);
        vista = root;
        resposta = vista.findViewById(R.id.lblResposta);
        resposta.setText(resposta.getText()+" "+ LoadActivity.user.getNome());
        btenviar = vista.findViewById(R.id.btn_enivar);
        btenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campoText = vista.findViewById(R.id.txtPergunta);
                respostaIA(campoText.getText()+"");
            }
        });
        return vista;
    }

    /**
     * Método para que escreve na View a resposta ja processada pela IA
     * @param texto Texto de resposta ja verificado para ser exibido
     */
    private void respostaIA(String texto){
        String b=ia.verificar(texto,getContext());
        resposta = vista.findViewById(R.id.lblResposta);
        campoText = vista.findViewById(R.id.txtPergunta);
        if(b.equalsIgnoreCase("Sem respostas por enquanto :(")){
            Toast.makeText(getContext(),"A DIA pode te ajudar com educação e assistencia pessoal",Toast.LENGTH_LONG).show();
        }
        resposta.setText("DIA: "+b);
        campoText.setText("");
    }


}
