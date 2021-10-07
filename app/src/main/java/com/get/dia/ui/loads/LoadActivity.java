package com.get.dia.ui.loads;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.get.dia.DAO.DAOUser;
import com.get.dia.R;
import com.get.dia.database.DadosSQL;
import com.get.dia.objetos.Usuario;
import com.get.dia.ui.home.HomeFragment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class LoadActivity extends AppCompatActivity {
    private ProgressBar progress;
    private TextView textProgress;
    public static String linha;
    public static SQLiteDatabase conexao;
    private DadosSQL dadosSQL;
    private DAOUser daouser;
    public static Usuario user;
    private ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        progress = findViewById(R.id.progress_bar);
        textProgress = findViewById(R.id.textProgress);
        progress.setMax(100);
        progress.setScaleY(3f);
        progressBarAnimation();
        rodarIa();
        criarConexao();
        user = daouser.getDados();
        Window window = getWindow();
    }

    @Override
    public void onPanelClosed(int featureId, Menu menu) {
        super.onPanelClosed(featureId, menu);
        setVisible(false);
    }

    private void rodarIa() {
        try {
            InputStream inputStream = ((getAssets().open("baseIA.txt")));
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            linha = new String(buffer);
        } catch (FileNotFoundException e) {
            System.out.println("GARAI");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void criarConexao() {
        try {
            dadosSQL = new DadosSQL(this);
            conexao = dadosSQL.getWritableDatabase();
            daouser = new DAOUser(conexao);
            Toast.makeText(this, "Acessado", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(e.getMessage());
            dlg.setPositiveButton("Ok", null);
            dlg.show();
        }
    }

    public void progressBarAnimation() {
        ProgressBarAnimation proAni = new ProgressBarAnimation(this, progress, textProgress, 0f, 100f);
        proAni.setDuration(3200);
        progress.setAnimation(proAni);
    }
}
