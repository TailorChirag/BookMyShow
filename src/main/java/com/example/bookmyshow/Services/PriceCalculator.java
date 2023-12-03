package com.example.bookmyshow.Services;

import com.example.bookmyshow.Repositories.ShowSeatTypeRespository;
import com.example.bookmyshow.models.Show;
import com.example.bookmyshow.models.ShowSeat;
import com.example.bookmyshow.models.ShowSeatType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PriceCalculator {

    private ShowSeatTypeRespository showSeatTypeRespository;

    @Autowired
    PriceCalculator(ShowSeatTypeRespository showSeatTypeRespository) {
        this.showSeatTypeRespository = showSeatTypeRespository;
    }

    public int calculatePrice(Show show, List<ShowSeat> showSeats){
        //1. Get the ShowSeatTypes for the input show.
        List<ShowSeatType> showSeatTypes = showSeatTypeRespository.findAllByShow(show);

        //2. Get the type of the input showSeats.
        int amount = 0;
        for (ShowSeat showSeat : showSeats) {
            for (ShowSeatType showSeatType : showSeatTypes) {
                if (showSeat.getSeat().getSeatType().equals(showSeatType.getSeatType())){
                    amount += showSeatType.getPrice();
                    break;
                }
            }
        }

        //3. Add the corresponding price.
        return amount;
    }
}
