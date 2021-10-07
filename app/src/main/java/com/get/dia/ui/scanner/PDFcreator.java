package com.get.dia.ui.scanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;

import com.get.dia.ui.home.HomeFragment;
import com.get.dia.ui.loads.LoadActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PDFcreator {

    private ArrayList<File> files = new ArrayList<>();
    private String nomePdf;

    public PDFcreator(ArrayList<File> lista, String nome) throws FileNotFoundException, IOException {
        files.addAll(lista);
        if (nome == null || nome.equals("")) {
            nomePdf = "diaapp";
            for (File f : files
            ) {
                if (f.getName().equals(nomePdf + ".pdf")) {
                    nomePdf += "_2";
                }
            }
        } else {
            nomePdf = nome;
        }
    }


    public boolean criarPdf() {
        try {
            PdfDocument pdf = new PdfDocument();
            Paint paint = new Paint();
            if (files.size() == 0) {
                throw new IOException();
            }
            //MELHORAR ESSES CODIGOS PARA TER UMA RESOLUCAO MELHOR
            for (int i = 0; i < files.size(); i++) {
                File f = files.get(i);
                System.out.println(f.getName());
                Bitmap btg = BitmapFactory.decodeFile(f.getAbsolutePath());
                Bitmap btgResize = Bitmap.createScaledBitmap(btg, 2430, 3400, false);
                PdfDocument.PageInfo pageinfo1 = new PdfDocument.PageInfo.Builder(2480, 3508, i).create();
                PdfDocument.Page page1 = pdf.startPage(pageinfo1);
                Canvas canvas = page1.getCanvas();
                canvas.drawBitmap(btgResize, 20, 20, paint);
                pdf.finishPage(page1);
            }
            File file = new File(Environment.getExternalStorageDirectory(), "/DIA/" + nomePdf + ".pdf");
            pdf.writeTo(new FileOutputStream(file));
            return true;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

}
