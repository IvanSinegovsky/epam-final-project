package by.epam.carrentalapp.service;

import by.epam.carrentalapp.entity.Role;

import java.util.List;

public interface UsersRolesService {
    List<Role> getAllUserRoles(Long userId);
}
