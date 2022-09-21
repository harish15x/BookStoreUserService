package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.UserDTO;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.model.UserModel;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.util.Response;
import com.bridgelabz.bookstore.util.ResponseClass;
import com.bridgelabz.bookstore.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailService mailService;

    @Autowired
    TokenUtil tokenUtil;


    @Override
    public ResponseClass addUser(UserDTO userDTO) {
        UserModel userModel = new UserModel(userDTO);
        userModel.setRegisteredDate(LocalDateTime.now());
        userRepository.save(userModel);
        String body = "User has been added " + userModel.getId();
        String subject = "User registration completed";
        mailService.send(userModel.getEmailId(), body, subject);
        return new ResponseClass(200, "Sucessfull", userModel);
    }

    @Override
    public ResponseClass updateUser(String token, UserDTO userDTO, long userId) {
        Long userToken = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent = userRepository.findById(userToken);
        if (isUserPresent.isPresent()){
            Optional<UserModel> isUserAvailable = userRepository.findById(userId);
            if (isUserAvailable.isPresent()){
                isUserAvailable.get().setFirstName(userDTO.getFirstName());
                isUserAvailable.get().setLastName(userDTO.getLastName());
                isUserAvailable.get().setKyc(userDTO.getKyc());
                isUserAvailable.get().setPassword(userDTO.getPassword());
                isUserAvailable.get().setEmailId(userDTO.getEmailId());
                String body = "Users details updated " + isUserAvailable.get().getId();
                String subject = "Users details updated successfully";
                mailService.send(isUserAvailable.get().getEmailId(), body, subject);
                userRepository.save(isUserAvailable.get());
                return new ResponseClass(200, "Sucessfull", isUserAvailable.get());
            }
            throw new UserNotFoundException(400, "User Not Found");
        }
        throw new UserNotFoundException(400, "Token is Wrong");
    }

    @Override
    public List<UserModel> getUserData(String token) {
        List<UserModel> getUserData = userRepository.findAll();
        if (getUserData.size() > 0){
            return getUserData;
        }
        throw new UserNotFoundException(400, "User Not Found");
    }

    @Override
    public ResponseClass deleteUser(long userId, String token) {
        Long userToken = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent = userRepository.findById(userToken);
        if (isUserPresent.isPresent()){
            Optional<UserModel> isUserAvailable = userRepository.findById(userId);
            if (isUserAvailable.isPresent()){
                userRepository.save(isUserAvailable.get());
                String body = "User deleted: " + isUserAvailable.get().getId();
                String subject = "User deleted successfully";
                mailService.send(isUserAvailable.get().getEmailId(), body, subject);
                return new ResponseClass(200, "Sucessfully", isUserAvailable.get());
            }
            throw new UserNotFoundException(400, "User Not Found");
        }
        throw new UserNotFoundException(400, "Token is wrong");
    }

    @Override
    public Response login(String email, String password) {
        Optional<UserModel> isUserAvailable = userRepository.findByEmailId(email);
        if (isUserAvailable.isPresent()){
            if (isUserAvailable.get().getPassword().equals(password)){
            String token = tokenUtil.createToken(isUserAvailable.get().getId());
            return new Response("Sucessfull", 400, token);
            }
            throw new UserNotFoundException(400, "password is wrong");
        }
          throw new UserNotFoundException(400, "user not found");
    }

    @Override
    public Boolean verifyToken(String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent = userRepository.findById(userId);
        if (isUserPresent.isPresent()){
            return true;
        }
        return false;
    }

    @Override
    public ResponseClass resetPassword(long userId, String token, String newPassword) {
        Long userToken = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent = userRepository.findById(userToken);
        if (isUserPresent.isPresent()){
            Optional<UserModel> isUserAvailable = userRepository.findById(userId);
            if (isUserAvailable.isPresent()){
                isUserAvailable.get().setPassword(newPassword);
                userRepository.save(isUserAvailable.get());
            }
            throw new UserNotFoundException(400, "token is wrong");
        }
        throw new UserNotFoundException(400, "user not found");
    }

    public ResponseClass forgetPassword(String emailId) {
    Optional<UserModel> isEmailPresent = userRepository.findByEmailId(emailId);
        if (isEmailPresent.isPresent()) {
        String token = tokenUtil.createToken(isEmailPresent.get().getId());
        String url = "http://localhost:8095/user/forgetPassword";
        String subject = "forget password";
        String body = "For forget password click this link" + url + "use this to reset" + token;
        mailService.send(isEmailPresent.get().getEmailId(), body, subject);
    }
        return new ResponseClass(200, "Success", isEmailPresent.get());
    }

        @Override
        public Boolean validate(String token){
            Long userId = tokenUtil.decodeToken(token);
            Optional<UserModel> isUser = userRepository.findById(userId);
            if (isUser.isPresent()){
                return true;
            }
            return false;
        }

}
