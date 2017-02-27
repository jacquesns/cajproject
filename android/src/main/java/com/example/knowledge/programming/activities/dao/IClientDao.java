package com.example.knowledge.programming.activities.dao;

/**
 * Created by jacquesdossantos on 2017-02-25.
 */
import java.util.List;

import com.example.knowledge.programming.activities.entities.Client;


public interface IClientDao extends BaseDao<Client> {

    public List<Client> selectRecents();

    public List<Client> selectActives();

}

