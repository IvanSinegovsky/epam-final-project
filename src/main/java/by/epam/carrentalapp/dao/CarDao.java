package by.epam.carrentalapp.dao;

import by.epam.carrentalapp.entity.Car;

import java.util.List;

public interface CarDao {
    String CAR_ID_COLUMN_NAME = "car_id";
    String MODEL_COLUMN_NAME = "model";
    String NUMBER_COLUMN_NAME = "number";
    String HOURLY_COST_COLUMN_NAME = "hourly_cost";

    //todo all methods contracts like spring repo
    List<Car> findAll();
}
