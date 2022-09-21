package com.bridgelabz.bookstore.controller;

import com.bridgelabz.bookstore.dto.UserDTO;
import com.bridgelabz.bookstore.model.UserModel;
import com.bridgelabz.bookstore.service.IUserService;
import com.bridgelabz.bookstore.util.Response;
import com.bridgelabz.bookstore.util.ResponseClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bookstore")
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping("message")
    public String message(){
        return "Welcome to BookStore";
    }

    @PostMapping("/adduser")
    public ResponseEntity<ResponseClass> addUser(@RequestBody UserDTO userDTO){
        ResponseClass responseClass = userService.addUser(userDTO);
        return new ResponseEntity<>(responseClass, HttpStatus.OK);
    }

    @PutMapping("update/{userId}")
    public ResponseEntity<ResponseClass> updateUser(@RequestHeader String token, @RequestBody UserDTO userDTO, @PathVariable long userId){
        ResponseClass responseClass = userService.updateUser(token, userDTO, userId);
        return new ResponseEntity<>(responseClass, HttpStatus.OK);
    }

    @GetMapping("/getuserdata")
    public ResponseEntity<List<?>> getUserData(@RequestParam String token){
        List<UserModel> responseclass = userService.getUserData(token);
        return new ResponseEntity<>(responseclass, HttpStatus.OK);
    }

    @DeleteMapping("delete/{userId}")
    public ResponseEntity<ResponseClass> deleteUser(@PathVariable long userId, @RequestHeader String token){
        ResponseClass responseClass = userService.deleteUser(userId, token);
        return new ResponseEntity<>(responseClass, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestParam String email, @RequestParam String password){
        Response response = userService.login(email, password);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/verify/{token}")
    public Boolean verifyToken(@PathVariable String token){
        return userService.verifyToken(token);
    }

    @PutMapping("resetpassword")
    public ResponseEntity<ResponseClass> resetPassword(@PathVariable long userId, @RequestHeader String token, @RequestParam String newPassword){
        ResponseClass responseClass = userService.resetPassword(userId, token, newPassword);
        return new ResponseEntity<>(responseClass, HttpStatus.OK);
    }

    @PutMapping("/forgetpassword")
    public ResponseEntity<ResponseClass> forgetPassword(@RequestParam String emailId){
        ResponseClass responseClass = userService.forgetPassword(emailId);
        return new ResponseEntity<>(responseClass, HttpStatus.OK);
    }

    @GetMapping("/validate/{token}")
    public Boolean validate(@PathVariable String token){
        return userService.validate(token);
    }
}
