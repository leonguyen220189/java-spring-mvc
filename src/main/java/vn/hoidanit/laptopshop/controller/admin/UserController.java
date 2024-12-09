package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import vn.hoidanit.laptopshop.service.RoleService;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;
import vn.hoidanit.laptopshop.domain.Role;
import vn.hoidanit.laptopshop.domain.User;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    // final theo chuẩn dependency injection (ko thay đổi giá trị sau khi khởi tạo)
    private final UserService userService;
    private final RoleService roleService;

    // upload file
    private final UploadService uploadService;
    // hashing
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UploadService uploadService,
            PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    // method mặc định là GET sẽ trả về view khi nhấn vào Create User ở page:
    // /admin/user
    @RequestMapping(value = "/admin/user/create")
    public String getPageCreateUser(Model model) {
        model.addAttribute("newUser", new User());
        return "/admin/user/create";
    }

    // mặt dù url giống nhau nhưng method khác nhau -> Spring sẽ hiểu là xử lý khác
    // nhau
    // khi nhấn submit ở form tạo Create User (action=/admin/user/create) sẽ trả về
    // đây vì đây method=POST
    @PostMapping(value = "/admin/user/create")
    public String postUserCreate(Model model, @ModelAttribute("newUser") @Valid User user,
            BindingResult newUserBindingResult,
            @RequestParam("nameAvatarFile") MultipartFile avatarFile) {

        // view error
        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>>" + error.getField() + " - " + error.getDefaultMessage());
        }

        if (newUserBindingResult.hasErrors()) {
            return "admin/user/create";
        }

        String nameAvatarFile = this.uploadService.handleSaveUploadFile(avatarFile, "avatar");
        String hashedPw = this.passwordEncoder.encode(user.getPassword());
        Role role = this.roleService.getRoleByName(user.getRole().getName());
        user.setRole(role);
        user.setAvatar(nameAvatarFile);
        user.setPassword(hashedPw);
        this.userService.handleSaveUser(user);
        return "redirect:/admin/user";
    }

    // page: /admin/user
    @RequestMapping(value = "/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUser();
        model.addAttribute("users", users);
        return "/admin/user/show";
    }

    // page: /admin/user/user.id trang detail của 1 user khi nhấn vào view ở
    // page:/admin/user
    @RequestMapping(value = "/admin/user/{id}") // ở đây có tên tham số là cái gì cũng đc
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User users = this.userService.getUserById(id);
        model.addAttribute("user", users);
        return "/admin/user/detail";
    }

    @RequestMapping(value = "/admin/user/update/{id}") // ở đây có tên tham số là cái gì cũng đc
    public String getUserUpdatePage(Model model, @PathVariable long id) {
        User updatedUser = this.userService.getUserById(id);
        model.addAttribute("updatedUser", updatedUser);
        return "/admin/user/update";
    }

    @PostMapping("/admin/user/update") // @PostMapping=RequestMapping("", method = RequestMethod.POST)
    public String postUserUpdate(Model model, @ModelAttribute("updatedUser") User updated_user,
            @RequestParam("nameAvatarFile") MultipartFile avatarFile) {
        User currentUser = this.userService.getUserById(updated_user.getId());
        // nếu id của người dùng != null -> nó sẽ hiểu là update, ở đây email và pw =
        // null thì ko qtr vì ko update chúng
        // set lại user
        currentUser.setAddress(updated_user.getAddress());
        currentUser.setFullName(updated_user.getFullName());
        currentUser.setPhoneNumber(updated_user.getPhoneNumber());
        currentUser.setRole(this.roleService.getRoleByName(updated_user.getRole().getName()));
        currentUser.setAvatar(this.uploadService.handleSaveUploadFile(avatarFile, "avatar"));
        this.userService.handleSaveUser(currentUser);
        // java spring tự làm cho chúng ta hàm save() nếu đã có user -> update ko thì
        // create
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}") // @GetMapping = @RequestMapping
    public String getUserDeletePage(Model model, @PathVariable long id) {
        User deletedUser = this.userService.getUserById(id);
        model.addAttribute("deletedUser", deletedUser);
        return "/admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postUserDelete(Model model, @ModelAttribute("deletedUser") User deleted_user) {
        this.userService.handleDeleteUserById(deleted_user.getId());
        return "redirect:/admin/user";
    }

}
