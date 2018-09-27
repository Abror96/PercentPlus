package com.example.kringle.percentplus.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kringle.percentplus.R;
import com.example.kringle.percentplus.model.Object;

import java.util.ArrayList;
import java.util.List;

public class ObjectsAdapter extends RecyclerView.Adapter<ObjectsAdapter.ViewHolder>{

    private List<Object> objectList = new ArrayList<>();

    public ObjectsAdapter(List<Object> objectList) {
        this.objectList = objectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.object_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Object object = objectList.get(position);
        holder.objectName.setText(object.getObjectName());

        holder.objectsItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(view.getContext(), )
            }
        });
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView objectName;
        private ConstraintLayout objectsItemLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            objectsItemLayout = itemView.findViewById(R.id.objectItemLayout);
            objectName = itemView.findViewById(R.id.objectName);
        }
    }
}