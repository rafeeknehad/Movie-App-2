package com.example.moviesapp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesapp.R;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterForm extends AppCompatActivity {

    private TextInputLayout userName;
    private TextInputLayout pass;
    private Button logInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        userName = findViewById(R.id.RegisterForm_user_name_txt);
        pass = findViewById(R.id.RegisterForm_password_txt);
        logInBtn = findViewById(R.id.RegisterForm_Login_btn);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNameStr = userName.getEditText().getText().toString().trim();
                String passStr = pass.getEditText().getText().toString().trim();

                boolean res = flashScreen.dataBase.checkUser(userNameStr,passStr);
                if(res)
                {

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please Sign Up To Log In",Toast.LENGTH_SHORT).show();
                }
            }
        });
        initialForm();
    }

    private void initialForm()
    {
        TextView textView = (TextView) findViewById(R.id.RegisterForm_sign_up);
        String text = "You Don't have an account?Sign Up";
        SpannableString spannableString = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivityForResult(intent,0);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
            }
        };
        spannableString.setSpan(clickableSpan,26,33,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}