package com.example.chaima.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import javax.management.Notification;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer id;
    private String lastname;
    private String name;
    private String email;
    private Integer phonenumber;
    private String post;
    private Date dateEmb;
    private Integer salary;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Reclamation> reclamations;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Feedback> feedback;

    public Integer getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

}
