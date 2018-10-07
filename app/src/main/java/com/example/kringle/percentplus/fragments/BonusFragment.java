package com.example.kringle.percentplus.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kringle.percentplus.R;
import com.example.kringle.percentplus.activities.LegendActivity;
import com.example.kringle.percentplus.activities.MainActivity;
import com.example.kringle.percentplus.retrofit.RetrofitClient;
import com.example.kringle.percentplus.retrofit.interfaces.IBonus;
import com.example.kringle.percentplus.retrofit.interfaces.IObjects;
import com.example.kringle.percentplus.retrofit.models.Bonus;
import com.example.kringle.percentplus.retrofit.models.BonusRequest;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BonusFragment extends Fragment implements View.OnClickListener {

    private String object_name = "";
    private int object_id;

    private IBonus iBonus;
    private Retrofit retrofit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() !=null) {
            final Bundle args = getArguments();
            object_name = args.getString("object_name");
            object_id = args.getInt("object_id");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // init api
        retrofit = RetrofitClient.getInstance();

        View view = inflater.inflate(R.layout.fragment_bonus, null);

        TextView tv_object_name = view.findViewById(R.id.bonus_object_name);
        tv_object_name.setText(object_name);
        TextView tv_icon_info = view.findViewById(R.id.icon_info);
        tv_icon_info.setOnClickListener(this);

        TextView how_to_get_percent = view.findViewById(R.id.how_to_get_percent);
        how_to_get_percent.setText("Как накопить процент");
        how_to_get_percent.setPaintFlags(how_to_get_percent.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        how_to_get_percent.setOnClickListener(this);

        getBonus(view);

        return view;
    }

    private void getBonus(final View view) {
        iBonus = retrofit.create(IBonus.class);

        Call<Bonus> bonusCall = iBonus.getBonus(
                MainActivity.prefConfig.readToken(),
                new BonusRequest(object_id)
        );

        bonusCall.enqueue(new Callback<Bonus>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Bonus> call, Response<Bonus> response) {
                int statusCode = response.code();
                Log.d("LOGGER Bonus", "statusCode: " + statusCode);
                if (statusCode == 200) {
                    TextView tv_bonus_text = view.findViewById(R.id.tv_bonus_text);
                    String percent_full_num = response.body().getBonus().getPercent();
                    String percent = percent_full_num
                            .substring(0, percent_full_num.length()-2);
                    tv_bonus_text.setText(
                            "До скидки " +
                            percent+
                            "% вам необходимо накопить еще " +
                            (response.body().getBonus().getSumTo()-response.body().getBonus().getSumFrom()) +
                            " руб. По данным на " +
                            getDate()
                    );
                }
            }

            @Override
            public void onFailure(Call<Bonus> call, Throwable t) {
                MainActivity.prefConfig.displayToast("Произошла ошибка при получении бонусов");
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.icon_info:
                intent = new Intent(getContext(), LegendActivity.class);
                startActivity(intent);
                break;
            case R.id.how_to_get_percent:
                intent = new Intent(getContext(), LegendActivity.class);
                startActivity(intent);
                break;
        }
    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        return String.valueOf(dateFormat.format(new Date()));
    }
}