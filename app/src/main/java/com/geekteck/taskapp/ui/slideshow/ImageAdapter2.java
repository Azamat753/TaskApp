package com.geekteck.taskapp.ui.slideshow;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekteck.taskapp.R;

import java.io.File;
import java.util.List;

public class ImageAdapter2 extends RecyclerView.Adapter<ImageAdapter2.Holder> {
    private List<File>list;

    public ImageAdapter2(List<File> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ImageAdapter2.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slideshow_holder, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter2.Holder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_hd);
        }

        public void bind(File file) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imageView.setImageBitmap(bitmap);

        }
    }


}





