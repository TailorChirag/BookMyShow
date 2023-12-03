package com.example.bookmyshow.Services;

import com.example.bookmyshow.Exception.InvalidShowException;
import com.example.bookmyshow.Exception.InvalidUserException;
import com.example.bookmyshow.Exception.ShowSeatNotAvailableException;
import com.example.bookmyshow.Repositories.ShowRepository;
import com.example.bookmyshow.Repositories.ShowSeatRepository;
import com.example.bookmyshow.Repositories.UserRepository;
import com.example.bookmyshow.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private UserRepository userRepository;
    private ShowSeatRepository showSeatRepository;
    private ShowRepository showRepository;
    private PriceCalculator priceCalculator;

    @Autowired
    BookingService(UserRepository userRepository, ShowSeatRepository showSeatRepository,
                   ShowRepository showRepository,PriceCalculator priceCalculator) {
        this.userRepository = userRepository;
        this.showSeatRepository = showSeatRepository;
        this.showRepository = showRepository;
        this.priceCalculator = priceCalculator;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE) // Approach 1
    public Booking bookMovie(Long userId, Long showId, List<Long> showSeatId) throws InvalidUserException, InvalidShowException, ShowSeatNotAvailableException {
        // Actual Logical here
        //STEPS:
        // ------- TAKE A LOCK (Approach 1) -------
        //1. Get user with the userId.
        //2. Get show with the showId.
        //3. Get showSeats with showSeatIds.
        // ------- TAKE A LOCK (Approach 2) -----------
        //4. Check if seats are available or not.
        //5. if no, throw an exception.
        //6. If yes, Mark the seat status as BLOCKED.
        //7. Save the updated status in DB.
        // --------- RELEASE THE LOCK (Approach 2) --------
        //8. Create the booking object with PENDING status.
        //9. Return the booking object.
        // ------- RELEASE THE LOCK (Approach 1) -------

        //1. Get user with the userId.
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()){
            throw new InvalidUserException("Invalid User");
        }

        User user = optionalUser.get();

        //2. Get show with the showId.
        Optional<Show> optionalShow = showRepository.findById(showId);

        if(optionalShow.isEmpty()){
            throw new InvalidShowException("Invalid Show");
        }

        Show show = optionalShow.get();

        //3. Get showSeats with showSeatIds.
        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatId);

        //4. Check if seats are available or not.
        for(ShowSeat showSeat : showSeats){
            if(!showSeat.getSeatStatus().equals(SeatStatus.AVAILABLE)){
                //5. if no, throw an exception.
                throw new ShowSeatNotAvailableException("ShowSeat not Available");
            }
        }

        List<ShowSeat> finalShowSeats = new ArrayList<>();
        for(ShowSeat showSeat : showSeats){
            //6. If yes, Mark the seat status as BLOCKED.
            showSeat.setSeatStatus(SeatStatus.BLOCKED);
            //7. Save the updated status in DB.
            finalShowSeats.add(showSeatRepository.save(showSeat));
        }

        //8. Create the booking object with PENDING status.
        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setTimeOfBooking(new Date());
        booking.setShow(show);
        booking.setUser(user);
        booking.setShowSeats(finalShowSeats);
        booking.setPayments(new ArrayList<>());
        booking.setPrice(priceCalculator.calculatePrice(show,finalShowSeats));

        //9. Return the booking object.
        return booking;
    }
}
