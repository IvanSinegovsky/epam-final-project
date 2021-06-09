package by.epam.carrentalapp.service;

import by.epam.carrentalapp.bean.entity.Role;

import java.util.List;

public interface UsersRolesService {
    List<Role> getAllUserRoles(Long userId);
}
