package com.bookstoreapplication.controller;

import com.bookstoreapplication.dto.ResponseDTO;
import com.bookstoreapplication.dto.UserDTO;
import com.bookstoreapplication.dto.UserLoginDTO;
import com.bookstoreapplication.model.UserRegistration;
import com.bookstoreapplication.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
/**
 * In Controller class we write the API's here
 */
@RestController
@RequestMapping("/user")

public class UserRegistrationController {
    /**
     * Autowired IUserService to inject its dependency here
     */
    @Autowired
    IUserService userRegistrationService;
    //Ability to call api to register user record
    //Add User
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> addUserInBookStore( @Valid @RequestBody UserDTO userDTO){
        String newUser= userRegistrationService.addUser(userDTO);
        ResponseDTO responseDTO=new ResponseDTO("User Registered Successfully",newUser);
        return new ResponseEntity(responseDTO, HttpStatus.CREATED);
    }
    //Ability to call api to login user

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> userLogin(@RequestBody UserLoginDTO userLoginDTO) {
        return new ResponseEntity<ResponseDTO>(userRegistrationService.loginUser(userLoginDTO),HttpStatus.OK);
    }
   // Ability to call api to retrieve all user records
    //Get All Users
    @GetMapping(value = "/getAll")
    public ResponseEntity<String> getAllUser()
    {
        List<UserRegistration> listOfUsers = userRegistrationService.getAllUsers();
        ResponseDTO dto = new ResponseDTO("User retrieved successfully (:",listOfUsers);
        return new ResponseEntity(dto,HttpStatus.OK);
    }

    //Ability to call api to retrieve All user record by toke

    @GetMapping(value = "/getAll/{token}")
    public ResponseEntity<ResponseDTO> getAllUserDataByToken(@PathVariable String token)
    {
        List<UserRegistration> listOfUser = userRegistrationService.getAllUserDataByToken(token);
        ResponseDTO dto = new ResponseDTO("Data retrieved successfully (:",listOfUser);
        return new ResponseEntity(dto,HttpStatus.OK);
    }
    //Ability to call api to retrieve user record by emailId
    //Get by email id
    @GetMapping("/getByEmailId/{emailId}")
    public ResponseEntity<ResponseDTO> getUserByEmailId(@PathVariable("emailId") String emailId) {
        return new ResponseEntity<ResponseDTO>( new
                ResponseDTO("Get User Data by Email",
                userRegistrationService.getUserByEmailId(emailId)), HttpStatus.OK);
    }
    //Ability to call api to retrieve user record by token
    //Get user by user id
    @GetMapping("/getBy/{token}")
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable String token) {
        return new ResponseEntity<ResponseDTO>( new
                ResponseDTO("Get User Data By Id",
                userRegistrationService.getUserById(token)), HttpStatus.OK);
    }

   // Ability to call api to change password if old password has forgotten

    //Forget password by email
    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestParam String email, @RequestParam String password) {
        String resp = userRegistrationService.forgotPassword(email,password);
        return new ResponseEntity(resp, HttpStatus.OK);
    }
   // Ability to call api to update User By Email
    //Update user by id
    @PutMapping("/updateUserByEmail/{email}")
    public ResponseEntity<ResponseDTO> updateUserById(@PathVariable String email,@Valid @RequestBody UserDTO userDTO){
        UserRegistration updateUser= userRegistrationService.updateUser(email,userDTO);
        ResponseDTO dto = new ResponseDTO(" User Record updated successfully",updateUser);
        return new ResponseEntity(dto,HttpStatus.ACCEPTED);
    }
    //Ability to call api to update User By id
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRecordById(@PathVariable Integer id,@Valid @RequestBody UserDTO userDTO){
        UserRegistration entity = userRegistrationService.updateRecordByToken(id,userDTO);
        ResponseDTO dto = new ResponseDTO("User Record updated successfully",entity);
        return new ResponseEntity(dto,HttpStatus.ACCEPTED);
    }
    //Ability to call api to get token if forgot password
    @GetMapping("/getToken/{email}")
    public ResponseEntity<ResponseDTO> getToken(@PathVariable String email){
        String generatedToken=userRegistrationService.getToken(email);
        ResponseDTO responseDTO=new ResponseDTO("Token for mail id sent on mail successfully",generatedToken);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }


}