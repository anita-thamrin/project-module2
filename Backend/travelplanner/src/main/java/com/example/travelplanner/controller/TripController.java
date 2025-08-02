package com.example.travelplanner.controller;

import com.example.travelplanner.entity.Trip;
import com.example.travelplanner.service.TripService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public ResponseEntity<List<Trip>> getAllTrips() {
        List<Trip> trips = tripService.getAllTrips();
        return ResponseEntity.ok(trips);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        return tripService.getTripById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Trip> createTrip(@Valid @RequestBody Trip trip) {
        Trip createdTrip = tripService.createTrip(trip);
        return new ResponseEntity<>(createdTrip, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable Long id, @Valid @RequestBody Trip tripDetails) {
        Trip updatedTrip = tripService.updateTrip(id, tripDetails);
        return ResponseEntity.ok(updatedTrip);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/day/{day}")
    public ResponseEntity<List<Trip>> getTripsByDay(@PathVariable Integer day) {
        List<Trip> trips = tripService.getTripsByDay(day);
        return ResponseEntity.ok(trips); // Returns an empty list with 200 OK if no trips are found
    }
}
