package com.helpmycity.controller;

import com.helpmycity.Status;
import com.helpmycity.StatusCode;
import com.helpmycity.auth.model.User;
import com.helpmycity.auth.service.UserService;
import com.helpmycity.util.Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.POST)
    public Object login(@RequestParam String email, @RequestParam String password) {
        User user = userService.findUserByEmail(email);
        if (user == null)
            return new Status(StatusCode.FAILED, "Email and password incorrect");
        else if (user.getPassword().equals(Crypt.getSecurePassword(password)))
            return user;

        else
            return new Status(StatusCode.FAILED, "Password incorrect");
    }


    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public Object createNewUser(@RequestParam String email, @RequestParam String password, @RequestParam String name, @RequestParam String lastName) {
        User userExists = userService.findUserByEmail(email);
        if (userExists != null) {
            return new Status(StatusCode.FAILED, "There is already a user registered with the email provided");

        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setName(name);
        newUser.setLastName(lastName);

        userService.saveUser(newUser);

        return newUser;
    }


}