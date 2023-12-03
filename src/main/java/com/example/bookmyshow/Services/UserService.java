package com.example.bookmyshow.Services;

import com.example.bookmyshow.Exception.InvalidUserException;
import com.example.bookmyshow.Repositories.UserRepository;
import com.example.bookmyshow.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signUp(String emailId, String password) throws InvalidUserException {
        Optional<User> optionalUser = userRepository.findByEmail(emailId);

        // If the user is present in the DB then go to login workFlow else call the signup workFlow.
        if (optionalUser.isPresent()){
            login(emailId, password);
        }
        User user = new User();
        user.setBookings(new ArrayList<>());
        user.setEmail(emailId);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(password));

        return userRepository.save(user);
    }
    public boolean login(String emailId, String password){
        Optional<User> optionalUser = userRepository.findByEmail(emailId);
        String passwordStoredInDB = optionalUser.get().getPassword();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        return bCryptPasswordEncoder.matches(password,passwordStoredInDB);


    }
}
