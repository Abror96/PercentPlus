package com.example.kringle.percentplus.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kringle.percentplus.activities.ObjectsActivity;
import com.example.kringle.percentplus.R;
import com.example.kringle.percentplus.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> categoryList;

    public CategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Category category = categoryList.get(position);
        holder.categoryName.setText(category.getCategoryName());
        holder.number.setText(String.valueOf(category.getNum()));

        holder.categoryItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ObjectsActivity.class);
                intent.putExtra("category_name", category.getCategoryName());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView categoryName;
        TextView number;
        ConstraintLayout categoryItemLayout;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           categoryName = itemView.findViewById(R.id.categoryName);
           number = itemView.findViewById(R.id.categoryNum);
           categoryItemLayout = itemView.findViewById(R.id.categoryItemLayout);
       }
    }

}
