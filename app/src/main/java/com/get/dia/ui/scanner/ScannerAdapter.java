package com.get.dia.ui.scanner;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.get.dia.R;
import com.get.dia.objetos.Alarme;

import java.io.File;
import java.util.ArrayList;

public class ScannerAdapter extends RecyclerView.Adapter<ScannerAdapter.MyView> {

    private ArrayList<File> listaFiles;
    Context context;
    private ScannerAdapter.OnItemClickListener clicklistener;


    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(ScannerAdapter.OnItemClickListener listener){
        clicklistener = listener;
    }

    public ScannerAdapter(Context ct, ArrayList<File> files){
        context = ct;
        listaFiles = files;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_imagens,parent,false);
        return new ScannerAdapter.MyView(view,clicklistener);
    }

    private int i =0;

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        try {
            i++;
            File file = listaFiles.get(position);
            holder.text1.setText("Página " + i + "\n" + file.getName());
            holder.text2.setText(file.getPath());
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (myBitmap != null) {
                holder.img.setImageBitmap(resizeBitmap(myBitmap, 150, 180));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Bitmap resizeBitmap(Bitmap bitmap,int width,int height) {
        Matrix m = new Matrix();
        m.setRectToRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), new RectF(0, 0, width, height), Matrix.ScaleToFit.CENTER);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
    }



    @Override
    public int getItemCount() {
        return listaFiles.size();
    }

    public static class MyView extends RecyclerView.ViewHolder{
        TextView text1,text2;
        Button btDelete;
        ImageView img;
        public MyView(@NonNull View itemView, final ScannerAdapter.OnItemClickListener listener) {
            super(itemView);
            text1 = itemView.findViewById(R.id.imagens);
            text2 = itemView.findViewById(R.id.descricaoImagens);
            btDelete = itemView.findViewById(R.id.buttonDelete_2);
            img = itemView.findViewById(R.id.img_row);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
}
