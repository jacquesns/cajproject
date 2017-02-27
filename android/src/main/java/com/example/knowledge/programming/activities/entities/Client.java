package com.example.knowledge.programming.activities.entities;

import java.util.Date;

/**
 * Created by jacquesdossantos on 2017-02-25.
 */

public class Client extends BaseEntity {

    private static final long serialVersionUID = -3883056944932590667L;

    private String name;
    private String surname;
    private Date birthday;
    private String login;
    private String password;
    private boolean active;

    public Client(){}

    public Client(Long id, String name, String surname, Date birthday, String login, String password, boolean active) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.login = login;
        this.password = password;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
