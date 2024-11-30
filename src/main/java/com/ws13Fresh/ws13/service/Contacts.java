package com.ws13Fresh.ws13.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ws13Fresh.ws13.model.Person;
import com.ws13Fresh.ws13.repo.PersonRepo;
import java.util.Random;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class Contacts {
    @Autowired
    PersonRepo personRepo;
    Random rand = new Random();

    @Value("${pathDir}")
    String pathDir; //inject pathDir from application properties file

    //utils to perform the functions here
    public Map<String,String> getAllUsers() throws IOException{
        Map<String,String> idUserList = new HashMap<String,String>();
        File file = new File(pathDir);
        String[] fileNames = file.list(); //list of file names in the file list
        for (String fileName : fileNames){
            //each item, split and take in the name and id
            FileReader fr = new FileReader(pathDir+File.separator+fileName);
            BufferedReader br = new BufferedReader(fr);
            String fileID = br.readLine();
            String fileuserName = br.readLine();
            idUserList.put(fileID,fileuserName);
            br.close();
        }
        return idUserList;
        //return personRepo.getAllUsers();
    }
    
    public void addUser(Person person) throws IOException{
        String id = String.format("%08x", rand.nextInt());
        // check if id exists, if not create it
        System.out.println("File created successfully at "+pathDir+"/"+id+".");
        FileWriter fw = new FileWriter(pathDir+ File.separator + id +".txt",false);
        BufferedWriter bw = new BufferedWriter(fw);
        personRepo.addUser(person);
        bw.write(id);     
        bw.newLine();
        bw.write(person.getName());   
        bw.newLine();   
        bw.write(person.getEmail());           
        bw.newLine();   
        bw.write(person.getPhoneNumber());        
        bw.newLine();   
        bw.write(person.getDob().toString());     
        bw.newLine();
        bw.flush();
        bw.close();   
    }
    public List<String> findUser(String id) throws IOException{
        FileReader fr = new FileReader(pathDir+ File.separator + id +".txt");
        BufferedReader br = new BufferedReader(fr);
        List<String> userInfo = new ArrayList<>();
        String currLine;
        br.readLine();//throw away the first line which is the ID
        while((currLine=br.readLine())!=null){
            //add the line to the data type
            userInfo.add(currLine);
        }
        br.close();
        
        return userInfo;
    }
    public Boolean deleteUser(Person person){
        return personRepo.delete(person);
    }
    public Boolean updateUser(Person person){
        return personRepo.update(person);
    }

}
