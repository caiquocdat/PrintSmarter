package com.example.printmaster.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.nsd.NsdServiceInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.printmaster.R;
import com.example.printmaster.fragment.FragmentActivity;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {
    private List<NsdServiceInfo> devices;
    private Context context;

    public DeviceAdapter(List<NsdServiceInfo> devices, Context context) {
        this.devices = devices;
        this.context = context;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device_print, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        NsdServiceInfo device = devices.get(position);
        holder.deviceName.setText(device.getServiceName());
        String ip= device.getHost()+"";
        if (ip.equals("null")){
            ip="172.16.1.51";
        }
        holder.deviceIp.setText(ip);
        holder.itemLienar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FragmentActivity.class);
                context.startActivity(intent);
                SharedPreferences sharedPreferences_device= context.getSharedPreferences("info_device",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_Editor=sharedPreferences_device.edit();
                editor_Editor.putString("name",device.getServiceName());
                editor_Editor.apply();
            }
        });
        // set other info
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    class DeviceViewHolder extends RecyclerView.ViewHolder {
        TextView deviceName,deviceIp;
        LinearLayout itemLienar;
        // add more views as needed

        DeviceViewHolder(View view) {
            super(view);
            deviceName = view.findViewById(R.id.nameDevice);
            deviceIp = view.findViewById(R.id.ipDevice);
            itemLienar = view.findViewById(R.id.itemLienar);
            // initialize other views
        }
    }
}

