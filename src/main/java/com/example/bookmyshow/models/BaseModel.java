package com.example.bookmyshow.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdAt;
    private Date updatedAt;


    /* ORM - Object Relation Mapping
    * Converts the models into tables when the appication would be started
    * ORM - will take care of writing complex queries behind the scenes
    * We will just call the ORM function and ORM will create query behind the scenes */


}
