package by.epam.carrentalapp.dao;

import by.epam.carrentalapp.bean.entity.Car;

import java.util.List;
import java.util.Optional;

public interface CarDao {
    String CAR_ID_COLUMN_NAME = "car_id";
    String MODEL_COLUMN_NAME = "model";
    String NUMBER_COLUMN_NAME = "number";
    String HOURLY_COST_COLUMN_NAME = "hourly_cost";
    String ASSET_URL_COLUMN_NAME = "asset_url";

    List<Car> findAll();
    Optional<Car> findById(Long carIdRoFind);
    Optional<Car> findByModel(String carModelToFind);
    List<Car> findAllByLimitAndOffset(int limit, int offset);
    Optional<Long> save(Car car);
    Optional<Integer> findCount();
}
