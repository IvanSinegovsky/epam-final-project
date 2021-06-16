package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.dao.CarDao;
import by.epam.carrentalapp.dao.DaoException;
import by.epam.carrentalapp.dao.DaoProvider;
import by.epam.carrentalapp.bean.entity.Car;
import by.epam.carrentalapp.service.CarService;
import by.epam.carrentalapp.service.ServiceException;
import org.apache.log4j.Logger;

import java.util.List;

public class CarServiceImpl implements CarService {
    private final Logger LOGGER = Logger.getLogger(CarServiceImpl.class);
    private final CarDao carDao;

    public CarServiceImpl() {
        carDao = DaoProvider.getCarDao();
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> cars;

        try {
            cars = carDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("CarServiceImpl getAllCars(): DAO cannot return car list");
            throw new ServiceException(e);
        }

        return cars;
    }
}
