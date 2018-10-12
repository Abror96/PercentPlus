package com.example.kringle.percentplus.activities;

import android.app.ProgressDialog;
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

import com.example.kringle.percentplus.ProgressDialog.DialogConfig;
import com.example.kringle.percentplus.R;
import com.example.kringle.percentplus.retrofit.RetrofitClient;
import com.example.kringle.percentplus.retrofit.interfaces.IRegistration;
import com.example.kringle.percentplus.retrofit.models.MobileUser;
import com.example.kringle.percentplus.retrofit.models.RegistrationResponse;
import com.example.kringle.percentplus.retrofit.models.SignRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.next_register)
    Button btn_next_register;
    @BindView(R.id.auth_btn_register)
    TextView auth_btn_register;
    @BindView(R.id.register_back_btn)
    ImageView register_back_btn;
    @BindView(R.id.et_email_register)
    EditText et_email_register;
    @BindView(R.id.et_city_register)
    EditText et_city_register;
    @BindView(R.id.et_password_register)
    EditText et_password_register;
    @BindView(R.id.et_confirm_password_register)
    EditText et_confirm_password_register;


    private IRegistration iRegistration;
    private Retrofit retrofit;

    private String email;
    private String password;
    private String confirm_password;
    private String name;
    private String city;
    private DialogConfig progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        // init api
        retrofit = RetrofitClient.getInstance();

        // init dialog
        progressDialog = new DialogConfig(this, "Идет загрузка");

        // underlining text views
        auth_btn_register.setPaintFlags(auth_btn_register.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // init click listeners
        btn_next_register.setOnClickListener(this);
        auth_btn_register.setOnClickListener(this);
        register_back_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_register:
                email = et_email_register.getText().toString().trim();
                password = et_password_register.getText().toString().trim();
                confirm_password = et_confirm_password_register.getText().toString().trim();
                city = et_city_register.getText().toString().trim();
                if (!email.isEmpty() && !password.isEmpty() && !confirm_password.isEmpty()
                        && !city.isEmpty()) {
                    if (password.length() >= 6) {
                        if (password.equals(confirm_password)) {
                            registerUser();

                            // show dialog
                            progressDialog.showDialog();

                        } else MainActivity.prefConfig.displayToast("Убедитесь в соответствии паролей.");
                    } else MainActivity.prefConfig.displayToast("Пароль не может быть меньше 6 символов.");

                } else MainActivity.prefConfig.displayToast("Заполните все поля!");
                break;
            case R.id.auth_btn_register:
                finish();
                break;
            case R.id.register_back_btn:
                onBackPressed();
                break;
        }
    }

    private void registerUser() {
        iRegistration = retrofit.create(IRegistration.class);

        Call<RegistrationResponse> registrationResponseCall = iRegistration.registrationData(
                new SignRequest(
                        new MobileUser(email, password, "Name", city)
                )
        );

        registrationResponseCall.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                int statusCode = response.code();
                Log.d("LOGGER Reg", "statusCode: " + statusCode);
                if (statusCode == 200) {
                    // hide dialog
                    MainActivity.dialogConfig.dismissDialog();
                    MainActivity.prefConfig.writeEmail(email);
                    MainActivity.prefConfig.writePassword(password);
                    Intent intent = new Intent(RegisterActivity.this, ConfirmEmailActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // hide dialog
                    progressDialog.dismissDialog();
                    MainActivity.prefConfig.displayToast("Произошла ошибка при попытке регистрации, попытайтесь снова.");
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                // hide dialog
                progressDialog.dismissDialog();
                MainActivity.prefConfig.displayToast("Произошла ошибка при попытке регистрации, попытайтесь снова.");
            }
        });


    }
}
