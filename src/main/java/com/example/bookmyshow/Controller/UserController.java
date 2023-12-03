package com.example.bookmyshow.Controller;

import com.example.bookmyshow.Services.UserService;
import com.example.bookmyshow.dtos.SignUpReponseDto;
import com.example.bookmyshow.dtos.SignUpRequestDto;
import com.example.bookmyshow.models.ResponseStatus;
import com.example.bookmyshow.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    public SignUpReponseDto singUp(SignUpRequestDto requestDto){
        User user;
        SignUpReponseDto signUpReponseDto = new SignUpReponseDto();

        try {
            user = userService.signUp(requestDto.getEmailId(), requestDto.getPassword());
            signUpReponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch (Exception e){
            signUpReponseDto.setResponseStatus(ResponseStatus.FAILURE);
            signUpReponseDto.setUserId(-1L);
        }

        return signUpReponseDto;
    }
}
