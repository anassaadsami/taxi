package se.anas.taxiCompmay.resourses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String first_name;
    private String last_name;
    private int age ;
/*
 here must we put this annotation otherwise it will be recursion ( we want to get taxi cars also in a user )
 and that's why we use fetch type.EAGER
 */
   // @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinTable(name = "user_taxi",
    joinColumns = @JoinColumn(name= "user_id"),
            inverseJoinColumns = @JoinColumn(name="taxi_id"))
    private List<Taxi> taxiList = new ArrayList<Taxi>();

    public User() {
        System.out.println("object is created");
    }

    public User(String first_name) {
        this.first_name = first_name;
    }



    public User(Long id, String first_name, String last_name, int age , List<Taxi> taxiList) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.taxiList = taxiList;
    }

    public List<Taxi> getTaxiList() {
        return taxiList;
    }

    public void setTaxiList(List<Taxi> taxiList) {
        this.taxiList = taxiList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id != null ? id.equals(user.id) : user.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", age=" + age +
                ",cars=" + this.getTaxiList()+
                '}';
    }
}
