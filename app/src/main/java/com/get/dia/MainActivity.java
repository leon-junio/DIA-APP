package com.get.dia;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import com.get.dia.DAO.DAOAlarme;
import com.get.dia.Dialogs.AgendaDialog;
import com.get.dia.Dialogs.AlarmDialog;
import com.get.dia.Dialogs.TutorialDialog;
import com.get.dia.notifications.AlertReceiver;

import android.os.CountDownTimer;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.get.dia.ui.tempo.TempoFragment;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TimePickerDialog.OnTimeSetListener {

    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    FloatingActionButton fab;
    NavigationView navigationView;
    NavController navController;
    private TextView text;
    private RadioButton radioButton;
    public static int materia = 0;
    int count = 0;
    private CheckBox chkb;
    public static boolean conectionState;
    boolean ver = false;
    private Calendar c;
    private Button bt_scan;
    private boolean back = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_agenda, R.id.nav_historico,
                R.id.nav_ia, R.id.nav_questoes, R.id.nav_tarefas, R.id.nav_trabalhos
                , R.id.nav_tempo)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(this);



        if (!fabT.isAlive()) {
            fabT.start();
        }
    }


    public static boolean atualizarTempo = false;
    /**
     * Thread para manter os IDs do botao flotoante ativos e iniciar a verificação de rede
     * Causa: Erro de execução em uma flag de outro Fragment não ativo
     */
    private Thread fabT = new Thread() {
        @Override
        public void run() {
            super.run();
            try {
                while (true) {
                    conectionState = estadoConexao();
                    try {
                        fab = findViewById(R.id.fab);
                        bt_scan = findViewById(R.id.bt_scan);
                    } catch (Exception e) {
                        System.out.println("Find erro+" + e.getMessage());
                    }
                    if(atualizarTempo){
                        iniciar(c);
                        atualizarTempo=false;
                        texto="";
                        navController.navigate(R.id.nav_tempo);
                    }
                    if(bt_scan != null){
                        bt_scan.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                navController.navigate(R.id.nav_home);
                                navController.navigate(R.id.to_scannerFragment);
                            }
                        });
                    }
                    if (fab != null) {
                        ver = true;
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar.make(view, "Abrindo IA", Snackbar.LENGTH_SHORT)
                                        .setAction("Action", null).show();
                                navController.navigate(R.id.nav_home);
                                navController.navigate(R.id.toIa);
                            }
                        });
                    }
                }
            } catch (Exception e) {
                System.out.println(" Thread 2 Error " + e.getMessage());
                e.printStackTrace();
            }
        }
    };
    /**
     * Verifica o estado de conexão com a internet
     * @return True se tiver conexão e false caso contrário
     */
    public boolean estadoConexao() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método para verificar se um radio button foi ativado
     *
     * @param view View que vai ser analisada
     */
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButton2:
                if (checked)
                    materia = 1;
                radioButton = findViewById(R.id.radioButton2);
                break;
            case R.id.radioButton3:
                if (checked)
                    materia = 2;
                radioButton = findViewById(R.id.radioButton3);
                break;
            case R.id.radioButton4:
                if (checked)
                    materia = 3;
                radioButton = findViewById(R.id.radioButton4);
                break;
            case R.id.radioButton5:
                if (checked)
                    materia = 4;
                radioButton = findViewById(R.id.radioButton5);
                break;
        }
    }

    /**
     * Método para pegar um horário do sistema
     *
     * @param view      A view atual de execução da janela
     * @param hourOfDay Hora do sistema
     * @param minute    Minutos do sistema
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        try {
            System.out.println("SETEI CAPETA");
            if(TempoFragment.tempo) {
                c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                c.set(Calendar.SECOND, 0);
                atualizaTempo(c);
                TempoFragment.tempo = false;
            }else{
                c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                c.set(Calendar.SECOND, 0);
                AgendaDialog.horarioAgenda = c;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método que inicia um alarme no Sistema
     *
     * @param c parametro que representa a data e hora selecionada no sistema
     */
    public void iniciar(Calendar c) {
        try {
            AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE, 1);
            }
            alarm.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Parametro pra atualizar o textField da hora dentro do fragment de tempo
     *
     * @param c parametro que representa a data e hora selecionada no sistema
     */
    private void atualizaTempo(Calendar c) {
        try {
            text = findViewById(R.id.txtT);
            texto += c.getTime();
            text.setText(texto);
            AlarmDialog alarm = new AlarmDialog();
            alarm.show(this.getSupportFragmentManager(), "Alarme");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Alarme não definido", Toast.LENGTH_LONG).show();
        }
    }

    public static String texto="";

    boolean doubleBackToExitPressedOnce = false;
    /**
     * Método que faz as telas retornarem dentro dos activityes
     */
    @Override
    public void onBackPressed() {
        try {
            if (navController.getCurrentDestination().getId() == R.id.nav_tempo) {
                navController.navigate(R.id.nav_home);
                count = 0;
            }
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
            count++;
            this.doubleBackToExitPressedOnce = true;
            if (navController.getCurrentDestination().getId() == R.id.nav_home) {
                Toast.makeText(this, "Aperte novamente pra fechar", Toast.LENGTH_LONG).show();
            }
            if (count == 2 && navController.getCurrentDestination().getId() == R.id.nav_home) {
                this.finishAffinity();
                finishAndRemoveTask();
            } else {
                new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        //VAZIO POR ENQUANTO
                    }

                    @Override
                    public void onFinish() {
                        doubleBackToExitPressedOnce = false;
                        count = 0;
                    }
                }.start();
            }
            if (doubleBackToExitPressedOnce && navController.getCurrentDestination().getId() != R.id.nav_home) {
                super.onBackPressed();
                count = 0;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Método que muda entre os Fragmentos da navigation view
     *
     * @param menuItem Item selecionado no menu
     * @return True se conseguiu mudar de tela
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        try {
            switch (menuItem.getItemId()) {
                case R.id.nav_agenda:
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                    navController.navigate(R.id.nav_home);
                    navController.navigate(R.id.toAgenda);
                    break;
                case R.id.nav_historico:
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                    navController.navigate(R.id.nav_home);
                    navController.navigate(R.id.toHistorico);
                    break;
                case R.id.nav_ia:
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                    navController.navigate(R.id.nav_home);
                    navController.navigate(R.id.toIa);
                    break;
                case R.id.nav_questoes:
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                    navController.navigate(R.id.nav_home);
                    navController.navigate(R.id.toQuestoes);
                    break;
                case R.id.nav_tempo:
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                    navController.navigate(R.id.nav_home);
                    navController.navigate(R.id.toTempo);
                    break;
                case R.id.nav_trabalhos:
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                    navController.navigate(R.id.nav_home);
                    navController.navigate(R.id.toTrabalhos);
                    break;
                case R.id.nav_tarefas:
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                    navController.navigate(R.id.nav_home);
                    navController.navigate(R.id.toTarefas);
                    break;

                case R.id.nav_sobre:
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                    navController.navigate(R.id.nav_home);
                    navController.navigate(R.id.toSobre);
                    break;
                case R.id.nav_config:
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                    navController.navigate(R.id.nav_home);
                    navController.navigate(R.id.toConfig);
                    break;
                case R.id.nav_feedback:
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                    navController.navigate(R.id.nav_home);
                    navController.navigate(R.id.to_feedBack);
                    System.out.println("feedback");
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_settings: acao_settings(); return true;
            case R.id.action_about:acao_about(); return true;
            case R.id.action_feedBack:acao_feedback();return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void acao_scanner(){
        navController.navigate(R.id.nav_home);
        navController.navigate(R.id.to_scannerFragment);
    }

    public void acao_settings(){
        navController.navigate(R.id.nav_home);
        navController.navigate(R.id.toConfig);
    }
    public void acao_about(){
        navController.navigate(R.id.nav_home);
        navController.navigate(R.id.toSobre);
    }
    public void acao_feedback(){

        navController.navigate(R.id.nav_home);
        navController.navigate(R.id.to_feedBack);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



}
