package com.example.bluetooth;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    ArrayList<PersonInfo> items=new ArrayList<PersonInfo>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View itemView=inflater.inflate(R.layout.layout1,viewGroup,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        PersonInfo item=items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.textView);
            textView2=itemView.findViewById(R.id.textView2);

        }

        public void setItem(PersonInfo item){
            textView.setText(item.getName());
            textView2.setText(item.getDevice().toString());
        }

    }

    public void addItem(PersonInfo item){
        items.add(item);
    }

    public void setItems(ArrayList<PersonInfo> items){
        this.items=items;
    }

    public PersonInfo getItem(int position){
        return items.get(position);
    }

    public void setItem(int position,PersonInfo item){
        items.set(position,item);
    }

}
