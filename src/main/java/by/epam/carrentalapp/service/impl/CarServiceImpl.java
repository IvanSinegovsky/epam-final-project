package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.dao.CarDao;
import by.epam.carrentalapp.dao.DaoFactory;
import by.epam.carrentalapp.entity.Car;
import by.epam.carrentalapp.service.CarService;
import org.apache.log4j.Logger;

import java.util.List;

public class CarServiceImpl implements CarService {
    private final Logger LOGGER = Logger.getLogger(CarServiceImpl.class);
    private final CarDao carDao;

    public CarServiceImpl() {
        carDao = DaoFactory.getCarDao();
    }

    @Override
    public List<Car> getAllCars() {
        return carDao.findAll();
    }
}
