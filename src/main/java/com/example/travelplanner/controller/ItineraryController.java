
package com.example.travelplanner.controller;

import com.example.travelplanner.entity.Itinerary;
import com.example.travelplanner.service.ItineraryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itineraries")
public class ItineraryController {

    private final ItineraryService service;

    public ItineraryController(ItineraryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Itinerary> create(@Valid @RequestBody Itinerary itinerary) {
        return ResponseEntity.ok(service.createItinerary(itinerary));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Itinerary> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getItinerary(id));
    }

    @GetMapping
    public ResponseEntity<List<Itinerary>> all() {
        return ResponseEntity.ok(service.getAllItineraries());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteItinerary(id);
        return ResponseEntity.noContent().build();
    }
}
