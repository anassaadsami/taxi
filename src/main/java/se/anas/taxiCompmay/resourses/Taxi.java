package se.anas.taxiCompmay.resourses;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Taxi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "taxi_id", nullable = false)
    private Long taxiNO;

    private String model;
    private int year;
/*
 here we (don't want to get the users of a taxi car ) so we use this annotation,
 and we don't need to use fetch type here, and without mappedBy will be TWO new tables in db
 */
    @JsonIgnore
    @ManyToMany(mappedBy = "taxiList")
    private List<User> userList = new ArrayList<User>();

    public Taxi() {
    }

    public Taxi(Long taxiNO, String model, int year , List<User> userList) {
        this.taxiNO = taxiNO;
        this.model = model;
        this.year = year;
        this.userList = userList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Long getTaxiNO() {
        return taxiNO;
    }

    public void setTaxiNO(Long taxiNO) {
        this.taxiNO = taxiNO;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Taxi taxi = (Taxi) o;

        return taxiNO != null ? taxiNO.equals(taxi.taxiNO) : taxi.taxiNO == null;
    }

    @Override
    public int hashCode() {
        return taxiNO != null ? taxiNO.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Taxi{" +
                "taxiNO=" + taxiNO +
                ", model='" + model + '\'' +
                ", year=" + year +
                '}';
    }
}
