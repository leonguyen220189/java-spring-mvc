package vn.hoidanit.laptopshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.laptopshop.service.UserService;

// @Controller
// public class UserController {

//     private UserService userService;

//     public UserController(UserService userService) {
//         this.userService = userService;
//     }

//     @RequestMapping("")
//     public String getHomaPage() {
//         return this.userService.handleGetHomePage();
//     }
// }

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String getHomaPage() {
        return this.userService.handleGetHomePage();
    }
}