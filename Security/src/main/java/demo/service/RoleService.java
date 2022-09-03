package demo.service;

import demo.model.Role;

import java.util.List;

public interface RoleService {
    List <Role> findAll();

    Role findById(Long id);

    Role findByName(String name);
}
