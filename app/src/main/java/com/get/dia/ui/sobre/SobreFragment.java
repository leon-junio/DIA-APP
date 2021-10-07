package com.get.dia.ui.sobre;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.get.dia.R;

public class SobreFragment extends Fragment {

    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_sobre, container, false);

        Button botaoAcaoWeb = view.findViewById(R.id.button_visitar_web);
        botaoAcaoWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fazer um site pro APP
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://diaapp.freevar.com")));
            }
        });

        return view;
    }
}
