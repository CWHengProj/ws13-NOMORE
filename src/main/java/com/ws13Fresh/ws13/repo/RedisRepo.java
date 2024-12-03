package com.ws13Fresh.ws13.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.ws13Fresh.ws13.Util.Util;
import com.ws13Fresh.ws13.model.Person;

@Repository
public class RedisRepo { //TODO convert to redisRepo instead of local repo
    @Autowired
    @Qualifier(Util.template01)
    RedisTemplate<String,Object> template;
    
    //My data structure is , List of persons, where person has values as well
    //person still based on model
    //perform data based operations here
    public void addUser(String id,Person person){
        //adds the user to the database
        template.opsForHash().put("Person",id,person);
    }
    public List<Object> getAllUsers(){
        return template.opsForHash().values("Person");
        //goes through all the files and returns the user details.
    }
}
