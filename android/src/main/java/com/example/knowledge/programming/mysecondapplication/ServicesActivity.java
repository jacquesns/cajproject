package com.example.knowledge.programming.mysecondapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by jacquesdossantos on 2017-02-23.
 */

public class ServicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services);

        Button buttonRequestServices = (Button)findViewById(R.id.buttonRequestServices);
        buttonRequestServices.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(ServicesActivity.this, RequestServices.class));
            }
        });

        Button buttonOfferServices = (Button)findViewById(R.id.buttonOfferServices);
        buttonOfferServices.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(ServicesActivity.this, OfferServices.class));
            }
        });
    }
}