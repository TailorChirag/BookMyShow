package com.example.bookmyshow.Services;

import com.example.bookmyshow.Exception.InvalidUserException;
import com.example.bookmyshow.Repositories.ShowRepository;
import com.example.bookmyshow.Repositories.ShowSeatRepository;
import com.example.bookmyshow.Repositories.UserRepository;
import com.example.bookmyshow.models.Booking;
import com.example.bookmyshow.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private UserRepository userRepository;
    private ShowSeatRepository showSeatRepository;
    private ShowRepository showRepository;

    @Autowired
    BookingService(UserRepository userRepository, ShowSeatRepository showSeatRepository,
                   ShowRepository showRepository) {
        this.userRepository = userRepository;
        this.showSeatRepository = showSeatRepository;
        this.showRepository = showRepository;
    }

    public Booking bookMovie(Long userId, Long showId, List<Long> showSeatId) throws InvalidUserException {
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

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()){
            throw new InvalidUserException("Invalid User");
        }



        return null;
    }
}
