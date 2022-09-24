package se.anas.taxiCompmay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.anas.taxiCompmay.interfaces.TaxiService;
import se.anas.taxiCompmay.repositories.TaxiRepo;
import se.anas.taxiCompmay.resourses.Taxi;

import java.util.List;

@Service
public class TaxiServiceImp implements TaxiService {

    private final TaxiRepo taxiRepo;

    @Autowired
    public TaxiServiceImp(TaxiRepo taxiRepo) {
        this.taxiRepo = taxiRepo;
    }
    @Override
    public Taxi getTaxi(Long taxi_id){
        return taxiRepo.findById(taxi_id).get();
    }
    @Override
    public List<Taxi> getAllTaxi(){
        return taxiRepo.findAll();
    }
    @Override
    public Taxi addTaxi(Taxi taxi){
        return taxiRepo.save(taxi);
    }
    @Override
    public Taxi updateTaxi(Long taxi_id , Taxi taxi){
        Taxi savedTaxi = taxiRepo.findById(taxi_id).get();
        if(taxiRepo.existsById(taxi_id)){
            savedTaxi.setModel(taxi.getModel());
            savedTaxi.setYear(taxi.getYear());
            taxiRepo.save(savedTaxi);
            return taxiRepo.save(savedTaxi);
        }
        System.out.println(String.format("taxi id %d is not found",taxi_id));
        return null;
    }
    @Override
    public String deleteTaxi(Long taxi_id){
        Taxi taxi;
        if(taxiRepo.existsById(taxi_id)){
            taxi = taxiRepo.findById(taxi_id).get();
            taxiRepo.delete(taxi);
          return  String.format("taxi id %d is deleted",taxi_id);
        }
          return   String.format("taxi id %d is not found",taxi_id);
    }

    // to get all taxi have this specific model
    public List<Taxi> getTaxiByModel(String name){
        return taxiRepo.findAllByModel(name);
    }

    // to get all taxi have year greater than this specific year
    public List<Taxi> getTaxiByYear(int year){
        return taxiRepo.findAllByYearGreaterThan(year);
    }

}
