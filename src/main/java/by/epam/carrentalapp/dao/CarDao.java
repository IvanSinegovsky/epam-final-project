package by.epam.carrentalapp.dao;

import by.epam.carrentalapp.entity.Car;

import java.util.List;

public interface CarDao {
    //todo all methods contracts like spring repo
    List<Car> getAll();
}
