package vn.hoidanit.laptopshop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/")
    public String index() {
        return "Hello world from thanh trong";
    }

    @GetMapping("/user")
    public String userPage() {
        return "Hello world from user";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "Hello world from admin";
    }

}
