package vn.hoidanit.laptopshop.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Role;
import vn.hoidanit.laptopshop.repository.RoleRepository;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> fetchRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }
}
