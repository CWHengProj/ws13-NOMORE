package com.ws13Fresh.ws13.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ws13Fresh.ws13.model.Person;
import com.ws13Fresh.ws13.service.ContactsRedis;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;




@Controller
@RequestMapping("/contact")
public class PersonController {

    @Autowired
    ContactsRedis contacts; //cjanged to integrate redis

    //getMapping leads to a form
    @GetMapping("/")
    public String getForm(Model model) {
        Person p = new Person(); //create new instance of a person
        model.addAttribute("person", p); //add the model for the view to see
        return "homePage";
    }
    //postMapping to submit form, this creates a file if valid. return a created message
    @PostMapping("/")
    public String submitForm(@Valid @ModelAttribute("person") Person person, BindingResult result, Model model) throws IOException {
        if(result.hasErrors()){ 
            return "homePage"; //redirect if validation fails
        }
        contacts.addUser(person);//creates the file with the new user
        
        return "redirect:/contact/allContacts"; //send to the contacts list if checks pass
    }

    //handle get to /contact/id to open contents of the file in the html document
    @GetMapping("/{id}")
    public String openDirectory(@PathVariable String id, Model model) throws IOException {
        try
            {//pass the path variable to the method to be used
            List<String> requestedUser = contacts.findUser(id);
            if (requestedUser==null || requestedUser.isEmpty()){
                return "cannotFind";
            }
            List<String> userInfo = new ArrayList<>();
            for (String s :requestedUser){
                userInfo.add(s);
            }

            model.addAttribute("userInfo",userInfo);//send the info to the model to use
            return "userInfo";}
        catch (IOException e){
            System.err.println("Error accessing file." + e.getMessage());
            e.printStackTrace();//print error message to troubleshoot
            return "cannotFind";
        }    
    }
    //index them by reading through all the files, create a map of UUID and name
    @GetMapping("/allContacts")
    public String getAllContacts(Model model) throws IOException {
        Map<String,String> persons = contacts.getAllUsers();
        model.addAttribute("persons",persons); //give the model access to the variable
        return "allContacts";
    }
    


}
