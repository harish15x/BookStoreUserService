package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.UserDTO;
import com.bridgelabz.bookstore.model.UserModel;
import com.bridgelabz.bookstore.util.Response;
import com.bridgelabz.bookstore.util.ResponseClass;

import java.util.List;

public interface IUserService {

    ResponseClass addUser(UserDTO userDTO);

    ResponseClass updateUser(String token, UserDTO userDTO, long userId);

    List<UserModel> getUserData(String token);

    ResponseClass deleteUser(long userId, String token);

    Response login(String email, String password);

    Boolean verifyToken(String token);

    ResponseClass resetPassword(long userId, String token, String newPassword);

    ResponseClass forgetPassword(String emailId);

    Boolean validate(String token);
}
