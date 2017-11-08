package com.example.jasonzhang.cs242;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Start extends AppCompatActivity {
    public static String Login_ID = "com.example.jasonzhang.cs242.MESSAGE";
    public static String Password = "com.example.jasonzhang.cs242.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void login(View view){
        Intent intent = new Intent(this, MainActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(Login_ID, message);
        editText = (EditText) findViewById(R.id.editText2);
        message = editText.getText().toString();
        intent.putExtra(Password, message);
        startActivity(intent);
    }
}