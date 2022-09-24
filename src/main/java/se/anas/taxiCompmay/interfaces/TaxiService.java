package se.anas.taxiCompmay.interfaces;

import se.anas.taxiCompmay.resourses.Taxi;

import java.util.List;

public interface TaxiService {

    Taxi getTaxi(Long taxi_id);

    List<Taxi> getAllTaxi();

    Taxi addTaxi(Taxi taxi);

    Taxi updateTaxi(Long taxi_id, Taxi taxi);

    String deleteTaxi(Long taxi_id);




}
