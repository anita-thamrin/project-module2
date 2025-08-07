package com.example.travelplanner.service;

import com.example.travelplanner.dto.ItineraryWithTotalDTO;
import com.example.travelplanner.entity.Itinerary;
import com.example.travelplanner.entity.Trip;
import com.example.travelplanner.exception.ItineraryNotFoundException;
import com.example.travelplanner.repository.ItineraryRepository;
import com.example.travelplanner.repository.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ItineraryServiceImplTest {

    private ItineraryRepository itineraryRepository;
    private TripRepository tripRepository;
    private ItineraryServiceImpl itineraryService;

    @BeforeEach
    void setUp() {
        itineraryRepository = Mockito.mock(ItineraryRepository.class);
        tripRepository = Mockito.mock(TripRepository.class);
        itineraryService = new ItineraryServiceImpl(itineraryRepository, tripRepository);
    }

    @Test
    void shouldReturnItinerary_WhenFound() {
        Itinerary itinerary = Itinerary.builder()
                .id(1L)
                .destination("Paris")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(5))
                .build();

        when(itineraryRepository.findById(1L)).thenReturn(Optional.of(itinerary));

        ItineraryWithTotalDTO result = itineraryService.getItinerary(1L);

        assertThat(result).isEqualTo(itinerary);
    }

    @Test
    void shouldThrowException_WhenItineraryNotFound() {
        when(itineraryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> itineraryService.getItinerary(1L))
                .isInstanceOf(ItineraryNotFoundException.class)
                .hasMessageContaining("Itinerary not found with ID");
    }

    @Test
    void shouldAddTripToItinerary() {
        Itinerary itinerary = new Itinerary();
        itinerary.setId(1L);
        itinerary.setTrips(new ArrayList<>());

        Trip trip = new Trip();
        trip.setTripDay(1);
        trip.setActivityType("Sightseeing");

        when(itineraryRepository.findById(1L)).thenReturn(Optional.of(itinerary));
        when(tripRepository.save(trip)).thenReturn(trip);

        Trip result = itineraryService.addTripToItinerary(1L, trip);

        assertThat(result).isEqualTo(trip);
        assertThat(trip.getItinerary()).isEqualTo(itinerary);
    }
}
