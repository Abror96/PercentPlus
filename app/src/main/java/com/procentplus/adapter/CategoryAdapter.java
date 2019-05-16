package com.procentplus.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.procentplus.activities.ObjectsActivity;
import com.procentplus.R;
import com.procentplus.retrofit.models.CategoriesResponse;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoriesResponse.ActivityType> categoryList;

    public CategoryAdapter(List<CategoriesResponse.ActivityType> categoryList) {
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


        final CategoriesResponse.ActivityType category = categoryList.get(position);
        //holder.number.setText(String.valueOf(category.getId()));
        try {
            holder.categoryName.setText(category.getName().substring(0, 26
            ) + "...");
        } catch (Exception e) {
            Log.d("LOGGER Adapter", "onBindViewHolder: " + e.getMessage());
        }

        holder.categoryItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ObjectsActivity.class);
                intent.putExtra("category_name", category.getName());
                intent.putExtra("category_id", category.getId());
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
        //TextView number;
        ConstraintLayout categoryItemLayout;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           categoryName = itemView.findViewById(R.id.categoryName);
           //number = itemView.findViewById(R.id.categoryNum);
           categoryItemLayout = itemView.findViewById(R.id.categoryItemLayout);
       }
    }

}
