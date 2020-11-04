package pl.dernovyi.pracadomowa3.service;

import pl.dernovyi.pracadomowa3.model.Car;

import java.util.List;

public interface CarService {

    void saveCar(Car newCar);

    List<Car> findAllCars();

    void changeCarById( Car newCar);

    Car getCarById(int id);

    void removeCarById(int id);
    List<Integer> getYear();
}
