package by.epam.carrentalapp.dao;

import java.util.List;

public interface UsersRolesDao {
    String USER_ID_COLUMN_NAME = "users_user_id";
    String ROLE_ID_COLUMN_NAME = "roles_role_id";

    void save(Long userId, Long roleId);
    List<Long> findRoleIdsByUserId(Long userIdToFind);
}