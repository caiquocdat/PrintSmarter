package com.example.printmaster.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.printmaster.R;
import com.example.printmaster.model.CompatibleModel;
import com.example.printmaster.model.NameCompatibleModel;

import java.util.ArrayList;
import java.util.List;

public class CompatibleAdapter extends RecyclerView.Adapter<CompatibleAdapter.ViewHodel> {
    private Context context;
    private ArrayList<NameCompatibleModel> listNameCompatible;
    private String nameSearch;


    public CompatibleAdapter(Context context, ArrayList<NameCompatibleModel> listNameCompatible, String nameSearch) {
        this.context = context;
        this.listNameCompatible = listNameCompatible;
        this.nameSearch = nameSearch;
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.compatible_item_rcv, parent, false);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {
        List<CompatibleModel> listCompatible = new ArrayList<>();
//        for(int i=0;i<listCompatible.size();i++){
//            if(nameSearch.equals(listCompatible.get(i).getType())){
        holder.nameTv.setText(listNameCompatible.get(position).getName());
//            }
//        }
    }

    @Override
    public int getItemCount() {
//        int count=0;
//        List<CompatibleModel> listCompatible= new ArrayList<>();
//        for(int i=0;i<listCompatible.size();i++){
//            if(nameSearch.equals(listCompatible.get(i).getType())){
//                count=listCompatible.get(i).getListName().size();
//            }
//        }
        if (listNameCompatible==null) {
            return 0;
        } else {
            return listNameCompatible.size();
        }
    }

    public class ViewHodel extends RecyclerView.ViewHolder {
        TextView nameTv;

        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.nameTv);
        }
    }
}
