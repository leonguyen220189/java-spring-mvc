package vn.hoidanit.laptopshop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.ui.Model;
import vn.hoidanit.laptopshop.service.UserService;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    // final theo chuẩn dependency injection (ko thay đổi giá trị sau khi khởi tạo)
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("")
    public String getHomaPage(Model model) {
        String test = this.userService.handleGetHomePage();// lấy dữ liệu từ database
        model.addAttribute("test", test);
        String SinhVien = "Thanh Trong";
        model.addAttribute("TenSinhVien", SinhVien);
        return "hellofromjsp";
    }

    @RequestMapping(value = "/admin/user/create")
    public String getPageCreateUser(Model model) {
        model.addAttribute("newUser", new User());
        // chỉ để tránh lỗi phải có newUSer ở đây khi có modelAttribute newUser ở JSP
        return "/admin/user/Create";
    }

    @RequestMapping(value = "/admin/user/createuser", method = RequestMethod.POST)
    public String CreateUser(Model model, @ModelAttribute("newUser") User user) {
        this.userService.handleSaveUser(user);
        List<User> users = this.userService.getAllUserByEmailAndAddress("1@gmail.com", "b");
        System.out.println(users.toString());
        return "hellofromjsp";
    }

    @RequestMapping(value = "/admin/user")
    public String getPageUser(Model model) {
        return "/admin/user/info";
    }

}
