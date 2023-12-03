package com.example.bookmyshow.Controller;

import com.example.bookmyshow.Services.BookingService;
import com.example.bookmyshow.dtos.BookMovieRequestDto;
import com.example.bookmyshow.dtos.BookMovieResponseDto;
import com.example.bookmyshow.models.ResponseStatus;
import com.example.bookmyshow.models.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookingController {

    private BookingService bookingService;

    // Dependency Injection -> Spring is a dependency Injection framework.

    @Autowired
    BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    BookMovieResponseDto bookMovie(BookMovieRequestDto requestDto){
        BookMovieResponseDto responseDto = new BookMovieResponseDto();

        try {
            Long userId = requestDto.getUserId();;
            Long showId = requestDto.getShowId();
            List<Long> showSeatId = requestDto.getShowSeatId();
            Booking booking = bookingService.bookMovie(userId,showId,showSeatId);

            responseDto.setBoookingId(booking.getId());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            responseDto.setAmount(booking.getPrice());
        }catch (Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;

    }
}
