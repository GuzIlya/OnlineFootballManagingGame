package service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import service.forms.UserForm;
import service.services.SignupService;

@RestController
public class SignupController {
    @Autowired
    private SignupService signupService;

    @PostMapping("/signUp")
    public ResponseEntity<Object> addUser(@RequestBody UserForm userForm){
        signupService.signUp(userForm);
        return ResponseEntity.ok().build();
    }
}
