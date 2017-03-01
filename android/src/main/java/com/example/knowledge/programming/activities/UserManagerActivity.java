package com.example.knowledge.programming.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by jacquesdossantos on 2017-02-24.
 */

public class UserManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);

        Log.d("Insert", "Inserting...");

        Button btnScape = (Button)findViewById(R.id.buttonSaveNewUser);
        btnScape.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                startActivity(new Intent(UserManagerActivity.this, ServicesActivity.class));
            }
        });
    }

}
