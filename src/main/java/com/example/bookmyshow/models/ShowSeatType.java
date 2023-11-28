package com.example.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class ShowSeatType extends BaseModel{

    @ManyToOne
    private Show show;

    @ManyToOne
    private SeatType seatType;
    private int price;

}
