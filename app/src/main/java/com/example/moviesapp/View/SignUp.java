package com.example.moviesapp.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.moviesapp.R;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    private Button logInBtn;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPass;
    private TextInputLayout textInputLayoutConfirmPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);

        logInBtn = findViewById(R.id.Sign_Up_sign_up_btn);
        textInputLayoutName = findViewById(R.id.Sign_Up_Name);
        textInputLayoutEmail = findViewById(R.id.Sign_Up_E_mail);
        textInputLayoutPass = findViewById(R.id.Sign_Up_Password);
        textInputLayoutConfirmPass = findViewById(R.id.Sign_Up_Confirm_Password);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkSignUoRes = checkSignUp();
                if(checkSignUoRes == false)
                {
                    Toast.makeText(getApplicationContext(),"Faild Sing Up",Toast.LENGTH_SHORT).show();
                    return;
                }
                String userName = textInputLayoutName.getEditText().getText().toString().trim();
                String email = textInputLayoutEmail.getEditText().getText().toString().trim();
                String pass = textInputLayoutPass.getEditText().getText().toString().trim();

                boolean res = flashScreen.dataBase.insertData(userName,email,pass);

            }
        });
    }

    private boolean checkSignUp()
    {
        if(textInputLayoutName.getEditText().getText().toString().trim().equals(""))
        {
            textInputLayoutName.setErrorEnabled(true);
            textInputLayoutName.setError("Check User Name");
            return false;
        }
        if(textInputLayoutEmail.getEditText().getText().toString().trim().equals(""))
        {
            textInputLayoutEmail.setErrorEnabled(true);
            textInputLayoutEmail.setError("Check Email");
            return false;
        }
        if(textInputLayoutPass.getEditText().getText().toString().trim().equals(textInputLayoutConfirmPass.getEditText().getText().toString().trim()))
        {
            textInputLayoutPass.setErrorEnabled(true);
            textInputLayoutConfirmPass.setErrorEnabled(true);

            textInputLayoutPass.setError("Check Password");
            textInputLayoutConfirmPass.setError("Check Confirm  Password");
            return false;
        }
        return true;
    }
}