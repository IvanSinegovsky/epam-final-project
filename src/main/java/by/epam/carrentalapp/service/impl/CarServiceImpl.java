package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.bean.entity.Car;
import by.epam.carrentalapp.dao.CarDao;
import by.epam.carrentalapp.dao.DaoException;
import by.epam.carrentalapp.dao.impl.DaoProvider;
import by.epam.carrentalapp.service.CarService;
import by.epam.carrentalapp.service.ServiceException;
import by.epam.carrentalapp.service.impl.validator.Validator;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

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

    public List<Car> getCarsPage(int limit, int offset) {
        List<Car> carsPage;

        try {
            carsPage = carDao.findAllByLimitAndOffset(limit, offset);
        } catch (DaoException e) {
            LOGGER.error("CarServiceImpl getCarsPage(): DAO cannot return car list");
            throw new ServiceException(e);
        }

        return carsPage;
    }

    @Override
    public Optional<Car> getCarById(Long carId) {
        Optional<Car> carOptional = Optional.empty();

        try {
            carOptional = carDao.findById(carId);
        } catch (DaoException e) {
            LOGGER.error("CarServiceImpl getCarById(...): DAO cannot return car");
            throw new ServiceException(e);
        }

        return carOptional;
    }

    @Override
    public void addCar(Car car) {
        try {
            if (!Validator.validateCar(car)) {
                throw new ServiceException("Invalid car data");
            }

            Optional<Long> savedCarIdOptional = carDao.save(car);

            if (savedCarIdOptional.isEmpty()) {
                throw new ServiceException("CarServiceImpl addCar(...): DAO cannot return generated key");
            }
        } catch (DaoException e) {
            LOGGER.error("CarServiceImpl addCar(...): DAO cannot save car");
            throw new ServiceException(e);
        }
    }

    @Override
    public int getCarsQuantity() {
        int carsQuantity = 0;

        try {
            carsQuantity = carDao.findCount().orElseThrow(
                    () -> new ServiceException("CarServiceImpl getCarsQuantity(...): DAO cannot cars count")
            );
        } catch (DaoException e) {
            LOGGER.error("CarServiceImpl addCar(...): DAO cannot save car");
            throw new ServiceException(e);
        }

        return carsQuantity;
    }
}
