package vn.hoidanit.laptopshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.qos.logback.core.model.Model;

@Controller
public class DashBoardController {

    @GetMapping("/admin")
    public String getAdminPage(Model model) {
        return "admin/dashboard/show";
    }
}
