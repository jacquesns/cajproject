package com.example.knowledge.programming.mysecondapplication;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.knowledge.programming.mysecondapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnScape = (Button)findViewById(R.id.buttonLogin);
        btnScape.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
              startActivity(new Intent(MainActivity.this, ServicesActivity.class));
            }
        });
    }

    public void loginValidation(View view) {
        /**
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Login failed");
        alertDialog.show();
         **/
        Intent intent = new Intent(this, ServicesActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        //EditText editText = (EditText) findViewById(R.id.edit_message);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);

        startActivity(intent);
    }







}

