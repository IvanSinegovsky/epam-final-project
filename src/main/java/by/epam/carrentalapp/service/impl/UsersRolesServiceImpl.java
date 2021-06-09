package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.dao.RoleDao;
import by.epam.carrentalapp.dao.UsersRolesDao;
import by.epam.carrentalapp.dao.DaoFactory;
import by.epam.carrentalapp.bean.entity.Role;
import by.epam.carrentalapp.service.UsersRolesService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRolesServiceImpl implements UsersRolesService {
    private final Logger LOGGER = Logger.getLogger(UsersRolesServiceImpl.class);

    private final RoleDao roleDao;
    private final UsersRolesDao usersRolesDao;

    public UsersRolesServiceImpl() {
        roleDao = DaoFactory.getRoleDao();
        usersRolesDao = DaoFactory.getUsersRolesDao();
    }

    @Override
    public List<Role> getAllUserRoles(Long userId) {
        List<Long> userRolesIds = usersRolesDao.findRoleIdsByUserId(userId);
        Optional<Role> foundRoleOptional;
        List<Role> userRoles = new ArrayList<>();

        for (Long userRoleId : userRolesIds) {
            foundRoleOptional = roleDao.findByRoleId(userRoleId);

            foundRoleOptional.ifPresent(userRoles::add);
        }

        return userRoles;
    }
}
