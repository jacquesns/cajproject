package com.example.knowledge.programming.activities.dao.impl;

/**
 * Created by jacquesdossantos on 2017-02-25.
 */
import java.util.concurrent.atomic.AtomicInteger;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseManager {

    private AtomicInteger openCounter = new AtomicInteger();

    private static DataBaseManager instance;
    private SQLiteDatabase database;

    public static synchronized DataBaseManager instance() {
        if (instance == null) {
            instance = new DataBaseManager();
        }
        return instance;
    }

    public synchronized SQLiteDatabase openDatabaseToWrite() {
        if(openCounter.incrementAndGet() == 1) {
            // Opening new database
            database = DBOpenHelper.instance().getWritableDatabase();
        }
        return database;
    }

    public synchronized SQLiteDatabase openDatabaseToRead() {
        if(openCounter.incrementAndGet() == 1 ) {
            // Opening new database
            database = DBOpenHelper.instance().getWritableDatabase();
        }
        return database;
    }

    public synchronized void closeDatabase() {
        if(openCounter.decrementAndGet() == 0) {
            // Closing database
            database.close();

        }
    }
}
