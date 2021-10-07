package com.get.dia.ui.feedback;

import android.content.Intent;
import android.net.MailTo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.get.dia.MainActivity;
import com.get.dia.R;

public class FeedBackFragment extends Fragment {

    public FeedBackFragment() {
    }

    private EditText etNome, etFeedBack;
    private Button btnEnviar;
    private View view;

    private void enviarEmail() {

        final String nome = etNome.getText().toString();
        final String[] destinatario = {"marcosnetto995@gmail.com"};
        final String assunto = "FeedBack do aplicativo DIA do usuário " + nome;
        final String corpo = etFeedBack.getText().toString();

        if (!MainActivity.conectionState) {
            Toast.makeText(view.getContext(), "Você não está online para mandar este email", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, destinatario);
        intent.putExtra(Intent.EXTRA_SUBJECT, assunto);
        intent.putExtra(Intent.EXTRA_TEXT, corpo);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Escolha um aplicativo de email"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feed_back, container, false);

        etNome = (EditText) view.findViewById(R.id.etNome);
        etFeedBack = (EditText) view.findViewById(R.id.etFeedBack);
        btnEnviar = (Button) view.findViewById(R.id.btnEnviarFeedBack);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarEmail();
                Toast.makeText(view.getContext(), "E-mail enviado com sucesso", Toast.LENGTH_LONG);
            }
        });

        return view;
    }
}
