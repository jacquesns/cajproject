package com.example.knowledge.programming.activities.dao;

/**
 * Created by jacquesdossantos on 2017-02-25.
 */
import android.database.sqlite.SQLiteException;

import java.util.List;

public interface BaseDao<E>{

    public void executeDML(String dml) throws SQLiteException;

    public Long insert(E e) throws SQLiteException;

    public void update(E e) throws SQLiteException;

    public void delete(Long id);

    public E selectById(Long id);

    public List<E> selectAll();

    public String getColumnHack();

}