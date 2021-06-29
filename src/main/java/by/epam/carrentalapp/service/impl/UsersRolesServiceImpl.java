package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.bean.entity.Role;
import by.epam.carrentalapp.dao.DaoException;
import by.epam.carrentalapp.dao.RoleDao;
import by.epam.carrentalapp.dao.UsersRolesDao;
import by.epam.carrentalapp.ioc.Autowired;
import by.epam.carrentalapp.service.ServiceException;
import by.epam.carrentalapp.service.UsersRolesService;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class UsersRolesServiceImpl implements UsersRolesService {
    private final Logger LOGGER = Logger.getLogger(UsersRolesServiceImpl.class);

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UsersRolesDao usersRolesDao;

    @Override
    public List<Role> getAllUserRoles(Long userId) {
        List<Role> userRoles = new ArrayList<>();

        try {
            List<Long> userRolesIds = usersRolesDao.findRoleIdsByUserId(userId);
            Optional<Role> foundRoleOptional;

            for (Long userRoleId : userRolesIds) {
                foundRoleOptional = roleDao.findByRoleId(userRoleId);

                foundRoleOptional.ifPresent(userRoles::add);
            }
        } catch (DaoException e) {
            LOGGER.error("UserServiceImpl login(...): DAO cannot execute operations");
            throw new ServiceException(e);
        }

        return userRoles;
    }
}
