package com.procentplus.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.procentplus.ProgressDialog.DialogConfig;
import com.procentplus.R;
import com.procentplus.retrofit.RetrofitClient;
import com.procentplus.retrofit.interfaces.INewPassword;
import com.procentplus.retrofit.models.NewPassword;
import com.procentplus.retrofit.models.NewPasswordReq;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RestorePasswordActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.restore_back_btn)
    ImageView restore_back_btn;
    @BindView(R.id.send_email_restore)
    Button btn_send_email_restore;
    @BindView(R.id.register_btn_restore)
    TextView register_btn_restore;
    @BindView(R.id.et_restore_email)
    EditText et_restore_email;

    private Retrofit retrofit;
    private INewPassword iNewPassword;
    private DialogConfig progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_password);
        ButterKnife.bind(this);

        // init api
        retrofit = RetrofitClient.getInstance();

        // init dialog
        progressDialog = new DialogConfig(this, "Идет загрузка");

        // underlining text views
        register_btn_restore.setPaintFlags(register_btn_restore.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // init click listeners
        restore_back_btn.setOnClickListener(this);
        btn_send_email_restore.setOnClickListener(this);
        register_btn_restore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.restore_back_btn:
                onBackPressed();
                break;
            case R.id.send_email_restore:
                String restore_email = et_restore_email.getText().toString().trim();
                if (!restore_email.isEmpty()) {
                    restorePassword(restore_email);

                    // show dialog
                    progressDialog.showDialog();

                } else {
                    MainActivity.prefConfig.displayToast("Введите свой email");
                }
                break;
            case R.id.register_btn_restore:
                Intent intent = new Intent(RestorePasswordActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void restorePassword(String restore_email) {
        iNewPassword = retrofit.create(INewPassword.class);

        Call<NewPassword> newPasswordCall = iNewPassword.newPasswordData(
                new NewPassword(new NewPasswordReq(restore_email))
        );

        newPasswordCall.enqueue(new Callback<NewPassword>() {
            @Override
            public void onResponse(Call<NewPassword> call, Response<NewPassword> response) {
                int statusCode = response.code();
                Log.d("LOGGER Reg", "statusCode: " + statusCode);
                if (statusCode == 200) {
                    // hide dialog
                    progressDialog.dismissDialog();
                    MainActivity
                            .prefConfig
                            .displayToast("Для сброса пароля перейдите по ссылке, которая была отправлена на вашу почту");
                    Intent intent = new Intent(RestorePasswordActivity.this, AuthActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // hide dialog
                    progressDialog.dismissDialog();
                    MainActivity
                            .prefConfig
                            .displayToast("Произошла ошибка при отправлении запроса. Попробуйте снова");
                }
            }

            @Override
            public void onFailure(Call<NewPassword> call, Throwable t) {
                // hide dialog
                progressDialog.dismissDialog();
                MainActivity
                        .prefConfig
                        .displayToast("Произошла ошибка при отправлении запроса. Попробуйте снова");
            }
        });
    }
}
