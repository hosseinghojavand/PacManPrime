package com.hossein.ghojavand.pacmanprime.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hossein.ghojavand.pacmanprime.R;
import com.hossein.ghojavand.pacmanprime.model.Device;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

public class ConnectedDevicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Serializable {

    Context context;

    List<Device> devices;

    public ConnectedDevicesAdapter(Context context , List<Device> devices) {
        this.context = context;
        this.devices = devices;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder myViewHolder = (ItemViewHolder) holder;
        String s =String.valueOf(devices.get(position).id);
        myViewHolder.name_tv.setText(s);

    }

    @Override
    public int getItemCount() {

        return devices.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements Serializable {

        TextView name_tv ;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.name_tv);
        }
    }

}
