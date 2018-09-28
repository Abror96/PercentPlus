package com.example.kringle.percentplus.fragments;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kringle.percentplus.R;
import com.example.kringle.percentplus.activities.LegendActivity;

import org.w3c.dom.Text;

public class BonusFragment extends Fragment implements View.OnClickListener {

    private String object_name = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() !=null) {
            final Bundle args = getArguments();
            object_name = args.getString("object_name");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_bonus, null);

        TextView tv_object_name = view.findViewById(R.id.bonus_object_name);
        tv_object_name.setText(object_name);
        TextView tv_icon_info = view.findViewById(R.id.icon_info);
        tv_icon_info.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_info:
                Intent intent = new Intent(getContext(), LegendActivity.class);
                startActivity(intent);
                break;
        }
    }
}