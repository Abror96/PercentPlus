package com.procentplus.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.procentplus.R;
import com.procentplus.activities.MainActivity;
import com.procentplus.retrofit.models.PointOfSale;
import com.procentplus.retrofit.models.SearchResponse;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<PointOfSale> searchResultList = new ArrayList<>();
    private boolean isEmpty = false;

    public SearchAdapter(List<PointOfSale> objectList) {
        this.searchResultList = objectList;
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setPointOfSales(new ArrayList<PointOfSale>());
        PointOfSale pointOfSale = new PointOfSale();
        pointOfSale.setName("По данному запросу ничего не найдено");

        if (searchResultList.size() == 0) {
            isEmpty = true;
            searchResultList.add(pointOfSale);
        }
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.object_item, parent, false);
        SearchAdapter.ViewHolder viewHolder = new SearchAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        final PointOfSale object = searchResultList.get(position);
        holder.objectName.setText(object.getName());
        if (isEmpty) {
            holder.objectName.setTextSize(15);
            holder.objectName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

        if (!isEmpty) {
            holder.objectsItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    intent.putExtra("tab_id", 2);
                    intent.putExtra("object_name", object.getName());
                    intent.putExtra("object_id", object.getPartnerId());
                    view.getContext().startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return searchResultList.size();
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
