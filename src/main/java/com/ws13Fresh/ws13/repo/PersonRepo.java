package com.ws13Fresh.ws13.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.ws13Fresh.ws13.model.Person;

@Repository
public class PersonRepo {
    
    List<Person> personList = new ArrayList<Person>();
    //perform data based operations here
    public void addUser(Person person){
        //adds the user to the database
        personList.add(person);
    }
    public List<Person> getAllUsers(){
        //goes through all the files and returns the user details.
        return personList;
    }
    public Boolean delete(Person person) {
        int iFoundPerson = personList.indexOf(person);

        if (iFoundPerson >= 0) {
            personList.remove(person);
            return true;
        }
        return false;
    }
    public Boolean update(Person person) {
        List<Person> filteredPerson = personList
        .stream()
        .filter(p -> p.getName().equals(person.getName()))
        .collect(Collectors.toList());

        //TODO double check the filter portion if it makes sense

        if (filteredPerson.size() > 0) {
            personList.remove(filteredPerson.getFirst());
            personList.add(person);
            return true;
        }
        return false;
    }
}
