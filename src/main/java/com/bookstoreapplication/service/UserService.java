package com.bookstoreapplication.service;

import com.bookstoreapplication.dto.ResponseDTO;
import com.bookstoreapplication.dto.UserDTO;
import com.bookstoreapplication.dto.UserLoginDTO;
import com.bookstoreapplication.exception.BookStoreException;
import com.bookstoreapplication.model.UserRegistration;
import com.bookstoreapplication.repository.UserRegistrationRepository;
import com.bookstoreapplication.util.EmailSenderService;
import com.bookstoreapplication.util.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
/**
 * Created UserService class to serve api calls done by controller layer
 */
public class UserService implements IUserService {

    /**
     * Autowired interface to inject its dependency here
     */
    @Autowired
    private UserRegistrationRepository userRepository;
    @Autowired
    EmailSenderService mailService;
    @Autowired
    TokenUtility util;
/**
 * create a method name as addUser
 * Ability to save user details to repository
 * */
    @Override
    public String addUser(UserDTO userDTO) {
        UserRegistration newUser= new UserRegistration(userDTO);
        userRepository.save(newUser);
        String token = util.createToken(newUser.getUserId());
        mailService.sendEmail(newUser.getEmail(), "Test Email", "Registered SuccessFully, hii: "
                +newUser.getFirstName()+"Please Click here to get data-> "
                +"http://localhost:8081/user/getBy/"+token);
        return token;
    }
/**
 * create a method name as getAllUsers
 * - Ability to get all user data by findAll() method
 * */
    @Override
    public List<UserRegistration> getAllUsers() {
        List<UserRegistration> getUsers= userRepository.findAll();
        return getUsers;
    }
/**
 * create a method name as getUserById
 * - Ability to get user data by Id
 * */
    @Override
    public Object getUserById(String token) {
        int id=util.decodeToken(token);
        Optional<UserRegistration> getUser=userRepository.findById(id);
        if(getUser.isPresent()){
            mailService.sendEmail("prathmesh9890@gmail.com", "Test Email", "Get your data with this token, hii: "
                    +getUser.get().getEmail()+"Please Click here to get data-> "
                    +"http://localhost:8081/user/getBy/"+token);
            return getUser;

        }
        else {
            throw new BookStoreException("Record for provided userId is not found");
        }

    }


/**
 * create a method name as loginUser
 * @param userLoginDTO - user login data (email, password)
 * */

    @Override
    public ResponseDTO loginUser(UserLoginDTO userLoginDTO) {
        ResponseDTO dto = new ResponseDTO();
        Optional<UserRegistration> login = userRepository.findByEmailid(userLoginDTO.getEmail());
        if(login.isPresent()){
            String pass = login.get().getPassword();
            if(login.get().getPassword().equals(userLoginDTO.getPassword())) {
                dto.setMessage("login successful ");
                dto.setData(login.get());
                return dto;
            }else {
                dto.setMessage("Sorry! login is unsuccessful");
                dto.setData("Wrong password");
                return dto;
            }
        }
        return new ResponseDTO("User not found!","Wrong email");
    }
/**
 * create a method name as forgotPassword
 * @param email - user email
 * */
    @Override
    public String forgotPassword(String email, String password) {
        Optional<UserRegistration> isUserPresent = userRepository.findByEmailid(email);

        if(!isUserPresent.isPresent()) {
            throw new BookStoreException("Book record does not found");
        }
        else {
            UserRegistration user = isUserPresent.get();
            user.setPassword(password);
            userRepository.save(user);
            return "Password updated successfully";
        }

    }
/**
 * create a method name as getUserByEmailId
 * - Ability get user data by emailId
 * */
    @Override
    public Object getUserByEmailId(String emailId) {

        return userRepository.findByEmailid(emailId);
    }
/**
 * create a method name as updateUser
 * update  record data by emilId
 * */
    @Override
    public UserRegistration updateUser(String email_id, UserDTO userDTO) {
        Optional<UserRegistration> updateUser = userRepository.findByEmailid(email_id);
        if(updateUser.isPresent()) {
            UserRegistration newBook = new UserRegistration(updateUser.get().getUserId(),userDTO);
            userRepository.save(newBook);
            String token = util.createToken(newBook.getUserId());
            mailService.sendEmail(newBook.getEmail(),"Welcome "+newBook.getFirstName(),"Click here \n http://localhost:8080/user/getBy/"+token);
            return newBook;

        }
        throw new BookStoreException("Book Details for email not found");
    }
/**
 * create a method name as getToken
 * ability get token by particular email id
 * */
    @Override
    public String getToken(String email) {
        Optional<UserRegistration> userRegistration=userRepository.findByEmailid(email);
        String token=util.createToken(userRegistration.get().getUserId());
        mailService.sendEmail(userRegistration.get().getEmail(),"Welcome"+userRegistration.get().getFirstName(),"Token for changing password is :"+token);
        return token;
    }
/**
 * create a method name as getAllUserDataByToken
 * get all data by using token
 * */
    @Override
    public List<UserRegistration> getAllUserDataByToken(String token) {
        int id=util.decodeToken(token);
        Optional<UserRegistration> isContactPresent=userRepository.findById(id);
        if(isContactPresent.isPresent()) {
            List<UserRegistration> listOfUsers=userRepository.findAll();
            mailService.sendEmail("prathmesh9890@gmail.com", "Test Email", "Get your data with this token, hii: "
                    +isContactPresent.get().getEmail()+"Please Click here to get data-> "
                    +"http://localhost:8081/user/getAll/"+token);
            return listOfUsers;
        }else {
            System.out.println("Exception ...Token not found!");
            return null;	}
    }
/**
 * create a method name as updateRecordById
 * @param id - user id
 * */
    @Override
    public UserRegistration updateRecordByToken(Integer id, UserDTO userDTO) {
//        Integer id= util.decodeToken(token);
        Optional<UserRegistration> addressBook = userRepository.findById(id);
        if(addressBook.isPresent()) {
            UserRegistration newBook = new UserRegistration(id,userDTO);
            userRepository.save(newBook);

            return newBook;

        }
        throw new BookStoreException("User Details for id not found");
    }


}