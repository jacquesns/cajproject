package com.example.knowledge.programming.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.knowledge.programming.activities.dao.impl.ClientDao;
import com.example.knowledge.programming.activities.entities.Client;

import java.util.Date;

/**
 * Created by jacquesdossantos on 2017-02-24.
 */

public class UserManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);





        Button btnScape = (Button)findViewById(R.id.buttonSaveNewUser);
        btnScape.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Log.d("Insert", "Inserting...");

                ClientDao clientDao = new ClientDao();
                EditText editTextName = (EditText)findViewById(R.id.newUserEditName);
                String name = editTextName.getText().toString();

                EditText editTextSurname = (EditText)findViewById(R.id.newUserEditSurname);
                String surname = editTextSurname.getText().toString();

                //EditText editTextBirthday = (EditText)findViewById(R.id.newUserEditBirthday);
                //String birthday = editTextBirthday.getText().toString();

                EditText editTextLogin = (EditText)findViewById(R.id.newUserEditLogin);
                String login = editTextLogin.getText().toString();

                EditText editTextPassword = (EditText)findViewById(R.id.newUserEditPassword);
                String password = editTextPassword.getText().toString();

                Client client = new Client(1L, name, surname, null, login, password, true);
                clientDao.insert(client);

                startActivity(new Intent(UserManagerActivity.this, ServicesActivity.class));
            }
        });


    }

}
