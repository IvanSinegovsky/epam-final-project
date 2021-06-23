package by.epam.carrentalapp.service;

import by.epam.carrentalapp.bean.entity.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    List<Car> getAllCars();
    List<Car> getCarsPage(int limit, int offset);
    Optional<Car> getCarById(Long carId);
    void addCar(Car car);
    int getCarsQuantity();
}
