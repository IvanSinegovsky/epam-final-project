package by.epam.carrentalapp.dao;

import by.epam.carrentalapp.bean.entity.Role;

import java.util.Optional;

public interface RoleDao {
    String ROLE_ID_COLUMN_NAME = "role_id";
    String ROLE_TITLE_COLUMN_NAME = "role_title";

    Optional<Role> findByRoleId(Long roleIdToFind);
    Optional<Role> findByTitle(String titleToFind);
}
