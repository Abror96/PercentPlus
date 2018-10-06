package com.example.kringle.percentplus.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kringle.percentplus.activities.ObjectsActivity;
import com.example.kringle.percentplus.R;
import com.example.kringle.percentplus.model.Category;
import com.example.kringle.percentplus.retrofit.models.CategoriesResponse;

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

        Log.d("LOGGER Adapter", "onBindViewHolder: " + categoryList.toString());

        final CategoriesResponse.ActivityType category = categoryList.get(position);
        holder.categoryName.setText(category.getName());
        holder.number.setText(String.valueOf(category.getId()));

        holder.categoryItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ObjectsActivity.class);
                intent.putExtra("category_name", category.getName());
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
