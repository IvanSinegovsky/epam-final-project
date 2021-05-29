package by.epam.carrentalapp.dao;

import by.epam.carrentalapp.entity.Role;

import java.util.List;
import java.util.Optional;

public interface UsersRolesDao {
    String USER_ID_COLUMN_NAME = "users_user_id";
    String ROLE_ID_COLUMN_NAME = "roles_role_id";

    void save(Long userId, Long roleId);
    List<Long> findRoleIdsByUserId(Long userIdToFind);
}