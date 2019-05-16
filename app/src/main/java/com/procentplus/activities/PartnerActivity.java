package com.procentplus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.procentplus.ProgressDialog.DialogConfig;
import com.procentplus.R;
import com.procentplus.retrofit.RetrofitClient;
import com.procentplus.retrofit.interfaces.ILogout;
import com.procentplus.retrofit.interfaces.ISaleRecord;
import com.procentplus.retrofit.models.CategoriesResponse;
import com.procentplus.retrofit.models.SaleRecord;
import com.procentplus.retrofit.models.SaleRecordRequest;
import com.procentplus.retrofit.models.SaleRecordResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.procentplus.activities.MainActivity.prefConfig;

public class PartnerActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.partner_cab_scan_button)
    Button scan;

    @BindView(R.id.partner_cab_sum)
    EditText sumText;

    @BindView(R.id.partner_cab_send)
    Button send;

    @BindView(R.id.partner_cab_logout)
    ImageView logout_btn;

    private ILogout iLogout;
    private ISaleRecord iSaleRecord;
    private Retrofit retrofit;

    //dialog for logout and send
    private DialogConfig sendDialog;
    private DialogConfig dialog;


    private Double sum;
    private String scanData;
    private int user_id;
    private int point_of_sale;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner);
        ButterKnife.bind(this);

        retrofit = RetrofitClient.getInstance();

        //add message for logout dialog
        dialog = new DialogConfig(this, "Выход...");
        sendDialog = new DialogConfig(this, "Отправка транзакции...");

        scan.setOnClickListener(this);
        send.setOnClickListener(this);
        logout_btn.setOnClickListener(this);

        //check scan data
        if (getIntent().getStringExtra("qr_data") != null) {
            scanData = getIntent().getStringExtra("qr_data");
            parserData(scanData);
        }
    }

    private void parserData(String data) {
        try {
            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse(data).getAsJsonObject();
            user_id = object.get("user_id").getAsInt();
            point_of_sale = object.get("point_of_sale_id").getAsInt();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            scanData = null;
            MainActivity.prefConfig.displayToast("Не корректный QR-код, просканируйте еще раз");
        } catch (IllegalStateException e) {
            e.printStackTrace();
            scanData = null;
            MainActivity.prefConfig.displayToast("Не корректный QR-код, просканируйте еще раз");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.partner_cab_scan_button:
                Log.d("Partner Activity", "readIsPartner - if - " + prefConfig.readIsPartner());
                Intent auth_intent = new Intent(PartnerActivity.this, QrCodeScannerActivity.class);
                startActivity(auth_intent);
                break;
            case R.id.partner_cab_send:
                sum = Double.valueOf(sumText.getText().toString().trim());
                if (sum != 0|| scanData != null) {
                    sendDialog.showDialog();
                    send();
                } else {
                    prefConfig.displayToast("Введите сумму и просканируйте QR-код!");
                }
                break;
            case R.id.partner_cab_logout:
                dialog.showDialog();
                logout();
                break;
        }
    }

    private void send() {
        Log.d("Partner activity", "Send() : sum = " + sum + ",  user_id = " + user_id +
                ", point_of_sale = " + point_of_sale +
                ", partner_id = " + MainActivity.prefConfig.readPartnerId());
        iSaleRecord = retrofit.create(ISaleRecord.class);

        Call<SaleRecordResponse> saleRecordResponseCall = iSaleRecord.getAccountData(MainActivity.prefConfig.readToken(), new SaleRecordRequest
                (new SaleRecord(Double.valueOf(sum), new SimpleDateFormat("yyyyMMdd_HHmmss").format
                        (Calendar.getInstance().getTime()), point_of_sale, user_id, MainActivity.prefConfig.readPartnerId())));
        saleRecordResponseCall.enqueue(new Callback<SaleRecordResponse>() {
            @Override
            public void onResponse(Call<SaleRecordResponse> call, Response<SaleRecordResponse> response) {
                sendDialog.dismissDialog();
                int statusCode = response.code();
                Log.d("LOGGER PartnerActivity", "Send - statusCode: " + statusCode);
                if (statusCode == 200) {

                    if (response.body().getSuccess()) {
                        MainActivity.prefConfig.displayToast("Успешно!\n ID транзакции: " + response.body().getSale_record().getTransaction_id());
                    } else {
                        MainActivity.prefConfig.displayToast("Не успешно!\n ID транзакции: " + response.body().getSale_record().getTransaction_id());
                    }
                }
            }

            @Override
            public void onFailure(Call<SaleRecordResponse> call, Throwable t) {
                sendDialog.dismissDialog();
                MainActivity.prefConfig.displayToast("Произошла ошибка при отправке транзакции.");

            }
        });

    }

    private void logout() {
        iLogout = retrofit.create(ILogout.class);
        Call<CategoriesResponse> call = iLogout.logOut(prefConfig.readToken());

        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                dialog.dismissDialog();
                int statusCode = response.code();
                Log.d("LOGGER Logout", "statusCode: " + statusCode);
                if (statusCode == 200 || statusCode == 204) {
                    prefConfig.writeLoginStatus(false);
                    Intent intent = new Intent(PartnerActivity.this, AuthActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                dialog.dismissDialog();
                prefConfig.displayToast("Произошла ошибка при попытке выхода из профиля\n" + t.getStackTrace());
            }
        });

    }


}
