package vn.hoidanit.laptopshop.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public String handleGetHomePage() {
        return "Hello From Service";
    }
}
