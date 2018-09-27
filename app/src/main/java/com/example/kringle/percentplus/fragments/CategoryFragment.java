package com.example.kringle.percentplus.fragments;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kringle.percentplus.R;
import com.example.kringle.percentplus.adapter.CategoryAdapter;
import com.example.kringle.percentplus.model.Category;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.content.res.AppCompatResources.getDrawable;

public class CategoryFragment extends Fragment {

    private RecyclerView categoryRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Category> categoryList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, null);

        TextView tv_category_city = view.findViewById(R.id.tv_category_city);
        tv_category_city.setText("Курск");
        tv_category_city.setPaintFlags(tv_category_city.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        setUpRecyclerView(view);

        return view;
    }

    private void setUpRecyclerView(View view) {
        fillData();
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(getDrawable(getContext(), R.drawable.divider));
        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView);
        categoryRecyclerView.setHasFixedSize(true);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CategoryAdapter(categoryList);
        categoryRecyclerView.addItemDecoration(itemDecorator);
        categoryRecyclerView.setAdapter(mAdapter);
    }

    private void fillData() {
        categoryList.add(new Category("Шиномонтаж", 12));
        categoryList.add(new Category("Сауны", 134));
    }
}
