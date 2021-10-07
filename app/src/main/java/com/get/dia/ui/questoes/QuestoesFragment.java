package com.get.dia.ui.questoes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.get.dia.R;

public class QuestoesFragment extends Fragment {
    private View vista;
    private Button but,butcal;
    private WebView web;
    private EditText text;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_questoes, container, false);
        vista=root;
        text = vista.findViewById(R.id.editQuestao);
        but = vista.findViewById(R.id.btn_pesquisar);
        butcal = vista.findViewById(R.id.btn_calc);
        butcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_CALCULATOR);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web = vista.findViewById(R.id.viewBrainly);
                web.getSettings().setJavaScriptEnabled(true);
                web.setWebViewClient(new WebViewClient());
                String find =" ";
                for (int j=0;j<text.length();j++){
                    if (text.getText().charAt(j)!=' '){
                        find+=text.getText().charAt(j);
                    }else{
                        find+="+";
                    }
                }
                web.loadUrl("https://brainly.com.br/app/ask?entry=top&q="+find);
                text.setText("");
            }
        });


        return vista;
    }
}
