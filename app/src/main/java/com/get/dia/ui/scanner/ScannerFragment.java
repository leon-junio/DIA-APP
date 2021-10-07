package com.get.dia.ui.scanner;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.get.dia.R;
import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ScannerFragment extends Fragment {

    private ArrayList<File> lista = new ArrayList<>();
    public View vista;
    private Button btc, btg, btp;
    private RecyclerView recyclerView;
    private ScannerAdapter adapter;
    private EditText txtName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_scanner, container, false);
        vista = root;
        recyclerView = vista.findViewById(R.id.recicler_img);
        btc = vista.findViewById(R.id.bt_camera);
        btg = vista.findViewById(R.id.bt_galeria);
        btp = vista.findViewById(R.id.bt_pdf);
        txtName = vista.findViewById(R.id.txt_name);
        if (getFiles().size() != 0) {
            criarView();
        }
        btc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamera(getView());
            }
        });
        btg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria(getView());
            }
        });
        btp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PDFcreator pdf = new PDFcreator(lista,txtName.getText().toString());
                    if(pdf.criarPdf()){
                        AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                        dlg.setTitle("PDF CRIADO COM SUCESSO");
                        dlg.setMessage("Seu PDF esta pronto na página de documentos do DIA, crie um novo ou compartilhe o seu!");
                        dlg.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteFiles();
                                atualizarList();
                                criarView();
                                txtName.setText("");
                            }
                        });
                        dlg.show();
                        System.out.println("CRIADO COM SUCESSO");
                    }else{
                        AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                        dlg.setTitle("PDF NÃO FOI CRIADO");
                        dlg.setMessage("Seu PDF esta indisponivel tente novamente ou crie outro arquivo.");
                        dlg.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                atualizarList();
                                criarView();
                            }
                        });
                        dlg.show();
                        System.out.println("ERRO DOIDAO");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return vista;
    }

    public void deleteFiles(){
        for (File f:lista) {
            f.delete();
            if (f.exists()) {
                try {
                    f.getCanonicalFile().delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean criarView() {
        try {
            adapter = new ScannerAdapter(getContext(), lista);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter.setOnItemClickListener(new ScannerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mudarItem(position, "X");
                }

                @Override
                public void onDeleteClick(int position) {
                    removerItem(position);
                }


            });
        } catch (Exception e) {
            System.out.println("Erro na criação de view de files");
            e.printStackTrace();
        }
        return true;
    }


    public static final String PATH = "/DIA/cache";

    public ArrayList<File> getFiles() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissReadFile(this.getActivity())) {
                    File root = Environment.getExternalStorageDirectory();
                    File dir = new File(root.getAbsolutePath() + PATH);
                    if (!dir.exists()) {
                        dir.mkdirs();
                        return lista;
                    }
                    if (dir.isDirectory()) {
                        lista.addAll(Arrays.asList(dir.listFiles()));
                        for (File a : lista
                        ) {
                            System.out.println(a.getName());
                        }
                        return lista;
                    }
                }
            }
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("DEU ERRO DOIDO NA PARTE DE LISTA DE FILES");
            return lista;
        }
    }


    public static final int CHECK_PERMISSION_REQUEST_READ_FILES = 61;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean permissReadFile(Activity activity) {
        boolean res = true;
        if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (activity.shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            }

            activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    CHECK_PERMISSION_REQUEST_READ_FILES);
            res = false;
        }
        return res;
    }

    public void abrirCamera(View v) {
        try {
            int REQUEST_CODE = 99;
            int preference = ScanConstants.OPEN_CAMERA;
            Intent intent = new Intent(this.getContext(), ScanActivity.class);
            intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
            startActivityForResult(intent, REQUEST_CODE);
            AlertDialog.Builder dlg = new AlertDialog.Builder(this.getContext());
            dlg.setTitle("Criação de documento scaneado");
            dlg.setMessage("Adicione mais fotos ou finalize seu PDF clicando no botão gerar PDF");
            dlg.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    atualizarList();
                    criarView();
                }
            });
            dlg.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void atualizarList() {
        lista.clear();
        getFiles();
    }

    public void abrirGaleria(View v) {
        try {
            int REQUEST_CODE = 99;
            int preference = ScanConstants.OPEN_MEDIA;
            Intent intent = new Intent(this.getContext(), ScanActivity.class);
            intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
            startActivityForResult(intent, REQUEST_CODE);
            AlertDialog.Builder dlg = new AlertDialog.Builder(this.getContext());
            dlg.setTitle("Criação de documento scaneado");
            dlg.setMessage("Adicione mais fotos ou finalize seu PDF clicando no botão gerar PDF");
            dlg.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    atualizarList();
                    criarView();
                }
            });
            dlg.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                getActivity().getContentResolver().delete(uri, null, null);
                //scannedImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void mudarItem(int position, String text) {
        try {
            ImageView image = new ImageView(this.getContext());
            File file = lista.get(position);
            Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
            image.setImageBitmap(bm);
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(this.getContext()).
                            setMessage(file.getName()).
                            setPositiveButton("OK", null).
                            setView(image);
            builder.create().show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void removerItem(final int position) {
        try {
            File file = lista.get(position);
            file.delete();
            if (file.exists()) {
                file.getCanonicalFile().delete();
            }
            atualizarList();
            criarView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

