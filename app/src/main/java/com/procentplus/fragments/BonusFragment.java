package com.procentplus.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.procentplus.ProgressDialog.DialogConfig;
import com.procentplus.R;
import com.procentplus.activities.LegendActivity;
import com.procentplus.activities.MainActivity;
import com.procentplus.retrofit.RetrofitClient;
import com.procentplus.retrofit.interfaces.IBonus;
import com.procentplus.retrofit.interfaces.IBonusQR;
import com.procentplus.retrofit.models.Bonus;
import com.procentplus.retrofit.models.BonusQR;
import com.procentplus.retrofit.models.BonusQRRequest;
import com.procentplus.retrofit.models.BonusRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BonusFragment extends Fragment implements View.OnClickListener {

    private String object_name = "";
    private int object_id = -1;
    private int object_point_id;

    private DialogConfig mDialogConfig;

    private IBonus iBonus;
    private IBonusQR iBonusQR;
    private Retrofit retrofit;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy.MM.dd");
    private SimpleDateFormat dateFormat3 = new SimpleDateFormat("dd MMMM");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() !=null) {
            final Bundle args = getArguments();
            object_name = args.getString("object_name");
            object_id = args.getInt("object_id");
            if (args.getString("object_point_id") != null) {
                object_point_id = Integer.parseInt(args.getString("object_point_id"));
            }

            Log.d("BONUS_FRAGMENT: ", "onCreate: object_name = " + object_name + ", object_id" + object_id +
                    ", object_point_id " + object_point_id);

        }
        if (object_id == -1) {
            object_id = MainActivity.prefConfig.readId();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // init api
        retrofit = RetrofitClient.getInstance();

        mDialogConfig = new DialogConfig(getActivity(), "Загрузка...");

        final View view = inflater.inflate(R.layout.fragment_bonus, null);

        //setting time
        final TextView cur_date = view.findViewById(R.id.currentDate);
        final TextView cur_time = view.findViewById(R.id.currentTime);
        cur_date.setText(String.valueOf(dateFormat3.format(new Date())));
        cur_time.setText(String.valueOf(dateFormat.format(new Date())));

        // animate circle
        animateCircle(view);

        TextView tv_object_name = view.findViewById(R.id.bonus_object_name);
        tv_object_name.setText(object_name);
        TextView tv_icon_info = view.findViewById(R.id.icon_info);
        tv_icon_info.setOnClickListener(this);

        TextView how_to_get_percent = view.findViewById(R.id.how_to_get_percent);
        how_to_get_percent.setText("Как накопить процент");
        how_to_get_percent.setPaintFlags(how_to_get_percent.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        how_to_get_percent.setOnClickListener(this);


        mDialogConfig.showDialog();
        if (object_point_id == 0) {
            getBonus(view);
        } else {
            getBonusQR(view);
        }

        return view;
    }

    private void animateCircle(View view) {
        ImageView animating_circle = view.findViewById(R.id.animating_circle);
        Animation mAnimation = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.02f);
        mAnimation.setDuration(400);
        mAnimation.setRepeatCount(-1);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setInterpolator(new LinearInterpolator());
        animating_circle.setAnimation(mAnimation);
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
                mDialogConfig.dismissDialog();
                int statusCode = response.code();
                Log.d("BONUS_FRAGMENT: ", "onCreate: object_name = " + object_name + ", object_id" + object_id +
                        ", object_point_id " + object_point_id);
                Log.d("LOGGER Bonus", "statusCode: " + statusCode);
                if (statusCode == 200) {
                    TextView tv_bonus_text = view.findViewById(R.id.tv_bonus_text);
                    TextView current_user_bonus = view.findViewById(R.id.current_user_bonus);
                    ImageView qr_image = view.findViewById(R.id.bonus_barcode_image);
                    qr_image.setImageResource(R.drawable.barcode);
                    qr_image.setVisibility(View.VISIBLE);

                    String percent_full_num = response.body().getBonus().getPercent();
                    switch (percent_full_num) {
                        case "5.0": current_user_bonus.setText("0%");
                            break;
                        case "7.0": current_user_bonus.setText("5%");
                            break;
                        case "10.0": current_user_bonus.setText("7%");
                            break;
                        case "15.0": current_user_bonus.setText("10%");
                            break;
                    }
                    String percent = percent_full_num
                            .substring(0, percent_full_num.length()-2);
                    tv_bonus_text.setText(
                            "До скидки " +
                            percent+
                            "% вам необходимо накопить еще " +
                            (response.body().getBonus().getSumTo()-response.body().getBonus().getSumFrom()) +
                            " руб. \n По данным на " +
                            getDate()
                    );
                }
            }

            @Override
            public void onFailure(Call<Bonus> call, Throwable t) {
                mDialogConfig.dismissDialog();
                MainActivity.prefConfig.displayToast("Произошла ошибка при получении бонусов");
            }
        });
    }

    private void getBonusQR(final View view) {
        iBonusQR = retrofit.create(IBonusQR.class);

        Call<BonusQR> bonusQRCall = iBonusQR.getBonusQR(MainActivity.prefConfig.readToken(),
                new BonusQRRequest(object_id, object_point_id));

        bonusQRCall.enqueue(new Callback<BonusQR>() {
            @Override
            public void onResponse(Call<BonusQR> call, Response<BonusQR> response) {
                mDialogConfig.dismissDialog();
                int statusCode = response.code();
                Log.d("BONUSQR_FRAGMENT: ", "onCreate: object_name = " + object_name + ", object_id" + object_id +
                        ", object_point_id " + object_point_id);
                Log.d("LOGGER Bonus", "statusCode: " + statusCode);
                if (statusCode == 200) {
                    TextView tv_bonus_text = view.findViewById(R.id.tv_bonus_text);
                    TextView current_user_bonus = view.findViewById(R.id.current_user_bonus);
                    ImageView qr_image = view.findViewById(R.id.bonus_qr_image);
                    qr_image.setVisibility(View.VISIBLE);

                    String qr = response.body().getQrcode();
                    final String pureBase64Encoded = qr.substring(qr.indexOf(",")  + 1);
                    Log.d("BONUSQR_FRAGMENT: ", "onResponse: " + qr);

                    byte[] decodedString = Base64.decode(pureBase64Encoded, Base64.DEFAULT);

                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    qr_image.setImageBitmap(decodedByte);

                    String percent_full_num = response.body().getBonus().getPercent();
                switch (percent_full_num) {
                    case "5.0": current_user_bonus.setText("0%");
                        break;
                    case "7.0": current_user_bonus.setText("5%");
                        break;
                    case "10.0": current_user_bonus.setText("7%");
                        break;
                    case "15.0": current_user_bonus.setText("10%");
                        break;
                }

                    String percent = percent_full_num
                            .substring(0, percent_full_num.length()-2);
                    tv_bonus_text.setText(
                            "До скидки " +
                                    percent+
                                    "% вам необходимо накопить еще " +
                                    (response.body().getBonus().getSumTo()-response.body().getBonus().getSumFrom()) +
                                    " руб.\n По данным на " +
                                    getDate()
                    );
                }
            }

            @Override
            public void onFailure(Call<BonusQR> call, Throwable t) {
                mDialogConfig.dismissDialog();
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
        return String.valueOf(dateFormat2.format(new Date()));
    }
}