package com.example.knowledge.programming.activities.dao.impl;

/**
 * Created by jacquesdossantos on 2017-02-25.
 */
import java.util.Date;
import java.util.List;
import android.content.ContentValues;
import android.database.Cursor;
import com.example.knowledge.programming.activities.dao.IClientDao;
import com.example.knowledge.programming.activities.entities.Client;


public class ClientDao extends BaseDaoImpl<Client> implements IClientDao {

    static final String TABLE_NAME = "CLIENT";

    enum EField{
        ID ("id"),
        NAME ("name"),
        SURNAME ("surname"),
        BIRTHDAY ("birthtday"),
        LOGIN ("nation"),
        PASSWORD ("password"),
        ACTIVE ("true");

        private String name;

        private EField(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }

    private static IClientDao instance;
    public static IClientDao instance(){
        if (instance == null){
            instance = new ClientDao();
        }
        return instance;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getColumnHack() {
        return EField.ID.getName();
    }

    @Override
    public ContentValues getContentValues(Client entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EField.ID.getName(), entity.getId());
        contentValues.put(EField.NAME.getName(), entity.getName());
        contentValues.put(EField.SURNAME.getName(), entity.getSurname());
        //contentValues.put(EField.BIRTHDAY.getName(), entity.getBirthday());
        contentValues.put(EField.LOGIN.getName(), entity.getLogin());
        contentValues.put(EField.PASSWORD.getName(), entity.getPassword());
        contentValues.put(EField.ACTIVE.getName(), entity.isActive());
        return contentValues;
    }

    @Override
    protected String setTableFields() {
        StringBuffer query = new StringBuffer();
        query.append(EField.ID.name).append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
        query.append(EField.NAME.name).append(" TEXT not null,");
        query.append(EField.SURNAME.name).append(" TEXT not null,");
        query.append(EField.LOGIN.name).append(" TEXT not null,");
        query.append(EField.PASSWORD.name).append(" TEXT not null,");
        query.append(EField.ACTIVE.name).append(" BOOLEAN default false, ");
        query.append(EField.BIRTHDAY.name).append(" DATE ");
        return query.toString();
    }

    @Override
    protected String getScriptCreateIndex() {
        StringBuffer query = new StringBuffer();
        query.append(createIndex(EField.NAME.name, false));
        query.append(createIndex(EField.SURNAME.name, false));
        query.append(createIndex(EField.LOGIN.name, false));
        query.append(createIndex(EField.PASSWORD.name, false));
        query.append(createIndex(EField.BIRTHDAY.name, false));
        query.append(createIndex(EField.ACTIVE.name, true));
        return query.toString();
    }

    @Override
    protected Client cursorToEntity(Cursor cursor) {
        Long id = cursor.getLong(getColumnByName(EField.ID.name, cursor));
        String name = cursor.getString(cursor.getColumnIndex(EField.NAME.name));
        String surname = cursor.getString(cursor.getColumnIndex(EField.SURNAME.name));
        String login = cursor.getString(cursor.getColumnIndex(EField.LOGIN.name));
        String password = cursor.getString(cursor.getColumnIndex(EField.PASSWORD.name));
        Boolean active = getBooleanValueFromCursor(cursor.getString(cursor.getColumnIndex(EField.ACTIVE.name)));
        Date birthday = getDateValueFromCursor(cursor.getLong(cursor.getColumnIndex(EField.BIRTHDAY.name)));
        return new Client(id, name, surname, birthday, login, password, active);
    }

    public List<Client> selectRecents() {
        StringBuffer query = new StringBuffer();
        query.append(" SELECT * FROM ");
        query.append(TABLE_NAME);
        query.append(" WHERE ");
        query.append(EField.BIRTHDAY.name).append(" is not null ");
        query.append(" ORDER BY ").append(EField.NAME.name).append(" DESC ");
        return getList(query.toString(), null);
    }

    public List<Client> selectActives() {
        StringBuffer query = new StringBuffer();
        query.append(" SELECT * FROM ");
        query.append(TABLE_NAME);
        query.append(" WHERE ");
        query.append(EField.ACTIVE.name).append(" = ").append(booleanToCursor(true));
        return getList(query.toString(), null);
    }

}

