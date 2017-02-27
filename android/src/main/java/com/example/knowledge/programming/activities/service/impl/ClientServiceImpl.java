package com.example.knowledge.programming.activities.service.impl;

import com.example.knowledge.programming.activities.dao.impl.ClientDao;
import com.example.knowledge.programming.activities.entities.Client;
import com.example.knowledge.programming.activities.service.ClientService;

/**
 * Created by jacquesdossantos on 2017-02-25.
 */

public class ClientServiceImpl implements ClientService {

    private ClientDao clientDao;

    public void save(Client client){
        clientDao = new ClientDao();
        //clientDao.


    }
}
