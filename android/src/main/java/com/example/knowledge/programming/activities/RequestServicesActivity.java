package com.example.knowledge.programming.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.knowledge.programming.activities.service.impl.ClientServiceImpl;

/**
 * Created by jacquesdossantos on 2017-02-24.
 */

public class RequestServicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_services);


        Button buttonRequestServices = (Button)findViewById(R.id.buttonSearchRequestService);
        buttonRequestServices.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ClientServiceImpl clientService = new ClientServiceImpl();

                //Intent intent = new Intent(this, ClientServiceImpl.class);
                //bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

                //startActivity(new Intent(this, ServicesActivity.class));

                startActivity(new Intent(RequestServicesActivity.this, ServicesActivity.class));
            }
        });


    }

}
