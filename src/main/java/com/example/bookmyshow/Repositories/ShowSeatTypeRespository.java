package com.example.bookmyshow.Repositories;

import com.example.bookmyshow.models.Show;
import com.example.bookmyshow.models.ShowSeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatTypeRespository extends JpaRepository<ShowSeatType,Long> {

    List<ShowSeatType> findAllByShow(Show show);
}
