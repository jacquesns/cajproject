package com.example.knowledge.programming.activities.dao.impl;

/**
 * Created by jacquesdossantos on 2017-02-25.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.example.knowledge.programming.activities.ApplicationContext;
import com.example.knowledge.programming.activities.MainActivity;
import com.example.knowledge.programming.activities.service.impl.ClientServiceImpl;

public class DBOpenHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "cajproject_dev.db";

    private static final int SCHEMA_VERSION = 3;
    private static DBOpenHelper instance;

    private DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    public static synchronized DBOpenHelper instance() {
        if (instance == null) {
            //TODO
            instance = new DBOpenHelper(ApplicationContext.getAppContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDatabase(db);
        createDefaultValues(db);
    }

    private void createDatabase(SQLiteDatabase db){
        //All DAOs
        ClientDao ClientDao = new ClientDao();

        try {
            //AllDAOs
            ClientDao.dropTable(db);
            ClientDao.createTable(db);
        } catch (SQLiteException e) {
            e.printStackTrace();
            //AppUtil.showLongMessage(e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            updateDefaultValues(db, oldVersion);
        }
    }

    private void createDefaultValues(SQLiteDatabase db){
        try {
            /**
            List<String> queries = ClientServiceImpl.getDictionaryDataFromFile();
            queries.addAll(NotepadService.getNotepadData());
            if (queries != null){
                for (String query : queries){
                    if (!query.trim().isEmpty()){
                        db.execSQL(query);
                    }
                }
            }
             **/
        } catch (Exception e){
            e.printStackTrace();
            //HandleException.showMessage(e.getMessage());
        }
    }


    private void updateDefaultValues(SQLiteDatabase db, int oldVersion){
        try {
            List<String> queries = null;//ClientServiceImpl.getUpgradeDictionaryDataFromFile(oldVersion);
            if (queries != null){
                for (String query : queries){
                    if (!query.trim().isEmpty()){
                        db.execSQL(query);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            //HandleException.showMessage(e.getMessage());
        }
    }

    private void backup() {
        try {
            File sdcard = Environment.getExternalStorageDirectory();
            File outputFile = new File(sdcard,
                    "cajproject_dev_BKP.db");

            if (!outputFile.exists())
                outputFile.createNewFile();

            File data = Environment.getDataDirectory();
            File inputFile = new File(data, "data/cajproject_dev.db");
            InputStream input = new FileInputStream(inputFile);
            OutputStream output = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new Error("Backup Failed");
        }
    }
}
