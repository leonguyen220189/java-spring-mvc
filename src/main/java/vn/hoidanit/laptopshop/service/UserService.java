package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    public User updateUser(User user) {
        return this.userRepository.save(user);
    }

    public List<User> fetchUsers() {
        return this.userRepository.findAll();
    }

    public List<User> fetchUsersByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public List<User> fetchUsersByEmailAndAddress(String email, String Address) {
        return this.userRepository.findByEmailAndAddress(email, Address);
    }

    public Optional<User> fetchUserById(long id) {
        return this.userRepository.findById(id);
    }

    public String getHomePage() {
        return "Hello From Service";
    }

    public void deleteUserById(long id) {
        this.userRepository.deleteById(id);
    }
}
