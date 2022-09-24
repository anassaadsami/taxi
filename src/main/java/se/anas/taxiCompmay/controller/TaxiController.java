package se.anas.taxiCompmay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.anas.taxiCompmay.resourses.Taxi;
import se.anas.taxiCompmay.service.TaxiServiceImp;

import java.util.List;

@RestController
@RequestMapping("taxi")
public class TaxiController {

    private final TaxiServiceImp taxiServiceImp;
    @Autowired
    public TaxiController(TaxiServiceImp taxiServiceImp) {
        this.taxiServiceImp = taxiServiceImp;
    }
    @GetMapping("/get/path/{id}")
    public ResponseEntity<Taxi> getTaxi(@PathVariable("id") Long taxi_id){
        return new ResponseEntity<Taxi>(taxiServiceImp.getTaxi(taxi_id), HttpStatus.FOUND);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<Taxi>> getAll(){
        return new ResponseEntity<List<Taxi>>(taxiServiceImp.getAllTaxi(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Taxi> addTaxi(@RequestBody Taxi taxi){
        return new ResponseEntity<Taxi>(taxiServiceImp.addTaxi(taxi), HttpStatus.CREATED);
    }
    @PutMapping("/update/header")
    public ResponseEntity<Taxi> updateTaxi(@RequestHeader Long taxi_id , @RequestBody Taxi taxi){
        return  new ResponseEntity<Taxi>(taxiServiceImp.updateTaxi(taxi_id, taxi) , HttpStatus.OK);
    }

    /*
     here if taxiService.deleteTaxi(taxi_id) return String and do the job
     if the id is exist so it will be deleted and return String statement is gone, otherwise we
     get String statement this object with this Id is not found
     */
    @DeleteMapping("/delete/param")
    public ResponseEntity<String > deleteTaxi(@RequestParam Long taxi_id){
        return  new ResponseEntity<String>(taxiServiceImp.deleteTaxi(taxi_id),HttpStatus.GONE);
    }

    @GetMapping("/getByModel/{model}")
    public ResponseEntity<List<Taxi>> getTaxiByModel(@PathVariable String model){
        return new ResponseEntity<List<Taxi>>(taxiServiceImp.getTaxiByModel(model), HttpStatus.FOUND);
    }

    @GetMapping("/getByYear/{year}")
    public ResponseEntity<List<Taxi>> getTaxiByYear(@PathVariable int year){
        return new ResponseEntity<List<Taxi>>(taxiServiceImp.getTaxiByYear(year), HttpStatus.FOUND);
    }




}
