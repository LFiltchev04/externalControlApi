package com.dash.dashboard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


@RestController
public class GenerationsManager{
    
    public CreateEnvironment jsn;
    //creates an environment from the tag name and hashes
    @PostMapping("/createEnv")
    public String GenerationController(@Valid @RequestBody CreateEnvironment jsn){

        
    }

}