package pl.dernovyi.pracadomowa3.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dernovyi.pracadomowa3.model.Car;
import pl.dernovyi.pracadomowa3.service.CarServiceImpl;


import java.util.List;
import java.util.stream.Collectors;




@RestController
@RequestMapping(value = "/api/cars", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200")
public class CarApi {
    private CarServiceImpl carService;

    @Autowired
    public CarApi(CarServiceImpl carService) {
        this.carService = carService;
    }

    @GetMapping("/years")
    private ResponseEntity<List<Integer>> getYears(){
        return new ResponseEntity<>(carService.getYear(), HttpStatus.OK);
    }

    @GetMapping("/{start}/{end}")
    private ResponseEntity<List<Car>> getAllCars(@PathVariable int start, @PathVariable int end ){
        List<Car> cars = carService.findAllCars();
        return new ResponseEntity<>(  cars.stream()
                .filter(car -> car.getYear() >= start
                        && car.getYear() <= end)
                .collect(Collectors.toList()) , HttpStatus.OK);
    }
    @PostMapping
    private ResponseEntity<Car> addCar(@RequestBody Car newCar){
            carService.saveCar(newCar);
            return new ResponseEntity<>(newCar,HttpStatus.CREATED);
    }

    @PutMapping
    private ResponseEntity<Car> changeCar(@RequestBody Car newCar){

        carService.changeCarById(newCar);
       return new ResponseEntity<>(newCar, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    private ResponseEntity<Car> getCarById(@PathVariable int id){
       Car car =  carService.getCarById(id);
       return new ResponseEntity<>(car, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    private ResponseEntity<Car> removeCar(@PathVariable int id){
        Car car = carService.getCarById(id);
        if(car!=null){
            carService.removeCarById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
