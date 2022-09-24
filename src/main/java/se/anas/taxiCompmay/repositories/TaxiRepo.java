package se.anas.taxiCompmay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.anas.taxiCompmay.resourses.Taxi;

import java.util.List;

public interface TaxiRepo extends JpaRepository<Taxi, Long> {

    List<Taxi> findAllByModel(String model);
    List<Taxi> findAllByYearGreaterThan(int year);

}
