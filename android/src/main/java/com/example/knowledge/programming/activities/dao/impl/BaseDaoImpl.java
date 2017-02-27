package com.example.knowledge.programming.activities.dao.impl;

/**
 * Created by jacquesdossantos on 2017-02-25.
 */
 import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatatypeMismatchException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteMisuseException;
 import com.example.knowledge.programming.activities.dao.BaseDao;
 import com.example.knowledge.programming.activities.entities.BaseEntity;

public abstract class BaseDaoImpl<E extends BaseEntity> implements BaseDao<E>{

    protected abstract String getScriptCreateIndex();
    protected abstract String setTableFields();
    public abstract String getTableName();
    public abstract String getColumnHack();
    protected abstract ContentValues getContentValues(E entity);

    private String getCreateScript(){
        StringBuffer query = new StringBuffer();
        query.append(this.getScriptCreateTable());
        query.append(this.getScriptCreateIndex()==null?"":this.getScriptCreateIndex());
        return query.toString();
    }

    private String getScriptCreateTable(){
        StringBuffer query = new StringBuffer();
        query.append(" CREATE TABLE ");
        query.append(getTableName());
        query.append(" ( ");
        query.append(setTableFields());
        query.append(" ); ");
        return query.toString();
    }

    public void createTable(SQLiteDatabase db) throws SQLiteException{
        db.execSQL(this.getCreateScript());
        printCommandMessage(this.getCreateScript(), null);
    }

    public void dropTable(SQLiteDatabase db) throws SQLiteException{
        String sql = "DROP TABLE IF EXISTS "+getTableName()+";";
        db.execSQL(sql);
        printCommandMessage(sql, null);
    }

    public void executeDML(String query) throws SQLiteException{
        SQLiteDatabase database = DataBaseManager.instance().openDatabaseToWrite();
        try {
            database.execSQL(query);
        } catch (Exception e){
            e.printStackTrace();
            throw new SQLiteException(e.getMessage());
        } finally {
            database.close();
            DataBaseManager.instance().closeDatabase();
        }
        printCommandMessage(query, null);
    }

    public Long insert(E entity) throws SQLiteException, SQLiteException{
        Long newId = 0L;
        SQLiteDatabase database = DataBaseManager.instance().openDatabaseToWrite();
        try {
            newId = database.insert(getTableName(), getColumnHack(), getContentValues(entity));
        }
        /**
        catch (SQLiteDatatypeMismatchException e){
            e.printStackTrace();
            throw new SQLiteException(e.getMessage());
        } catch (SQLiteMisuseException e){
            e.printStackTrace();
            throw new SQLiteException(e.getMessage());
        }

         catch (SQLiteBindOrColumnIndexOutOfRangeException e){
            e.printStackTrace();
            throw new SQLiteException(e.getMessage());
        }
         **/
        catch (SQLiteConstraintException e){
            e.printStackTrace();
            throw new SQLiteException(e.getMessage());
        } catch (SQLiteException e){
            e.printStackTrace();
            throw new SQLiteException(e.getMessage());
        } catch (SQLException e){
            e.printStackTrace();
            throw new SQLiteException(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            throw new SQLiteException(e.getMessage());
        } finally {
            DataBaseManager.instance().closeDatabase();
            if (newId == -1){
                throw new SQLiteException("new id = "+newId);
            }
        }
        printCommandMessage("INSERT ", entity);
        return newId;
    }

    protected int getColumnByName(String name, Cursor cursor){
        return cursor.getColumnIndex(name);
    }

    public void update(E entity) throws SQLiteException{
        SQLiteDatabase database = DataBaseManager.instance().openDatabaseToWrite();
        if (entity.getId() != null && entity.getId() > 0){
            database.update(getTableName(), getContentValues(entity), getColumnHack()+" = ?", new String[]{entity.getId().toString()});
            DataBaseManager.instance().closeDatabase();
        }
        else throw new SQLiteException ("Não foi encontrado o identificador do registro a ser atualizado.");
        printCommandMessage("UPDATE ", entity);
    }

    public void delete(Long id){
        SQLiteDatabase database = DataBaseManager.instance().openDatabaseToWrite();
        try {
            if (id != null && id > 0){
                database.delete(getTableName(), getColumnHack()+" = "+id, null);
                printCommandMessage("DELETE " + getTableName() + " ID = "+id, null);
            }
            else throw new SQLiteException("Erro ao executar a exclusão do registro.");
        } catch (SQLiteException e){
            e.printStackTrace();
        } finally {
//			database.close();
            DataBaseManager.instance().closeDatabase();
        }
    }

    protected String createForeignKey(String fieldName, String fieldReferenceName, String toTable){
        StringBuffer query = new StringBuffer();
        query.append(" ALTER TABLE ").append(getTableName());
        query.append(" ADD CONSTRAINT ").append(getTableName()).append("_").append(fieldName).append("_FK");
        query.append(" FOREIGN KEY ").append(fieldName);
        query.append(" REFERENCES ").append(toTable).append("(").append(fieldReferenceName).append(")");
        query.append(";");
        return query.toString();
    }

    protected String createIndex(String fieldName, boolean unique){
        StringBuffer query = new StringBuffer();
        query.append(" CREATE ");
        if (unique)query.append(" UNIQUE ");
        query.append(" INDEX IF NOT EXISTS IDX_").append(getTableName()).append("_").append(fieldName);
        query.append(" ON ").append(getTableName());
        query.append(" ( ").append(fieldName).append(" ) ");
        query.append(";");
        return query.toString();
    }

    private Cursor selectByIdCursor(Long id){
        StringBuffer query = new StringBuffer();
        query.append(" SELECT * FROM ");
        query.append(getTableName());
        query.append(" WHERE ");
        query.append(getColumnHack()).append(" = ").append(id);
        SQLiteDatabase database = DataBaseManager.instance().openDatabaseToRead();
        Cursor cursor = database.rawQuery(query.toString(), null);
        return cursor;
    }

    private Cursor selectAllCursor(){
        StringBuffer query = new StringBuffer();
        query.append(" SELECT * FROM ");
        query.append(getTableName());
        Cursor cursor = null;
        try {
            SQLiteDatabase database = DataBaseManager.instance().openDatabaseToRead();
            cursor = database.rawQuery(query.toString(), null);
        } catch (Exception e){
            e.printStackTrace();
        }
        return cursor;
    }

    public List<E> selectAll(){
        List<E> list = cursorToList(selectAllCursor());
        DataBaseManager.instance().closeDatabase();
        return list;
    }

    public E selectById(Long id){
        List<E> list = cursorToList(selectByIdCursor(id));
        DataBaseManager.instance().closeDatabase();
        printCommandMessage("Select * from " + getTableName() + " WHERE ID = "+id, null);
        if (list != null && !list.isEmpty())
            return list.get(0);
        return null;
    }

    protected abstract E cursorToEntity(Cursor cursor);

    protected List<E> getList(String query, String[] params){
        List<E> list = new ArrayList<E>();
        SQLiteDatabase database = null;
        try {
            database = DataBaseManager.instance().openDatabaseToRead();
            Cursor cursor = database.rawQuery(query.toString(), params);
            list = cursorToList(cursor);
        } catch (Exception e){
            e.printStackTrace();
            printCommandMessage("ERROR: " + query +" - "+ e.getMessage(), null);
        } finally {
            DataBaseManager.instance().closeDatabase();
            printCommandMessage(query, null);
        }
        return list;
    }

    protected Long getCount(String query, String[] params){
        long count = 0;
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            database = DataBaseManager.instance().openDatabaseToRead();
            cursor = database.rawQuery(query.toString(), params);
            cursor.moveToFirst();
            count = cursor.getInt(0);

        } catch (Exception e){
            e.printStackTrace();
            printCommandMessage("ERROR: " + query +" - "+ e.getMessage(), null);
        } finally {
            cursor.close();
            DataBaseManager.instance().closeDatabase();
            printCommandMessage(query, null);
        }
        return count;
    }

    public List<E> cursorToList(Cursor cursor){
        List<E> list = new ArrayList<E>();
        if (cursor != null){
            if (cursor.moveToFirst()) {
                do {

                    E entity = cursorToEntity(cursor);
                    list.add(entity);
                } while (cursor.moveToNext());
            }

            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return list;
    }

    protected Boolean getBooleanValueFromCursor(String value){
        Boolean boolValue = null;
        if (value != null && !value.isEmpty()){
            boolValue = value.equalsIgnoreCase("1");
        }
        return boolValue;
    }

    protected Date getDateValueFromCursor(Long value){
        return this.formatDateTimeFromCursor(value, "yyyy-MM-dd");
    }

    private Date formatDateTimeFromCursor(Long value, String pattern){
        if (value != null && value != 0){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(value);
            return calendar.getTime();
        }
        return null;
    }

    protected String booleanToCursor(boolean val){
        return val?"1":"0";
    }

    private void printCommandMessage(String command, E e){
        System.out.println("\n");
        System.out.println("###############################################");
        System.out.println("BEGIN ");
        System.out.println(" ");
        System.out.println("COMMAND EXECUTED IN GenericDaoImpl");
        System.out.println("DB OBJECT: "+getTableName());
        System.out.println("DATE: "+new Date());
        System.out.println("CLASS: "+toString());
        System.out.println("###############################################");
        System.out.println("###############################################");
        System.out.println("-----------------------------------------------");
        System.out.println(command);
        System.out.println("-----------------------------------------------");
        System.out.println(getContentValuesLog(e));
        System.out.println(" ");
        System.out.println("END ");
        System.out.println("###############################################");
        System.out.println(" ");
    }

    private String getContentValuesLog(E e){
        StringBuffer log = new StringBuffer();
        if (e != null){
            ContentValues contentValues = getContentValues(e);
            if (contentValues != null){
                if (contentValues != null){
                    for (Entry<String,Object> content : contentValues.valueSet()){
                        log.append(content.getKey());
                        log.append(" (").append(content.getValue()).append(")");
                        log.append("\n");
                    }
                }
            }
        }
        return log.toString();
    }

}