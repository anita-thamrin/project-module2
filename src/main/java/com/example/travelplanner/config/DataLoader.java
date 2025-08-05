package com.example.travelplanner.config;

import com.example.travelplanner.entity.Itinerary;
import com.example.travelplanner.entity.Trip;
import com.example.travelplanner.entity.User;
import com.example.travelplanner.repository.ItineraryRepository;
import com.example.travelplanner.repository.TripRepository;
import com.example.travelplanner.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;

@Component
public class DataLoader {
    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    private final UserRepository userRepository;
    private final ItineraryRepository itineraryRepository;
    private final TripRepository tripRepository;

    @Value("${app.dataloader.enabled:false}")
    private boolean dataLoaderEnabled;

    public DataLoader(UserRepository userRepository, ItineraryRepository itineraryRepository, TripRepository tripRepository) {
        this.userRepository = userRepository;
        this.itineraryRepository = itineraryRepository;
        this.tripRepository = tripRepository;
    }

    @PostConstruct
    public void loadData() {
        if (!dataLoaderEnabled) {
            logger.info("DataLoader is disabled, skipping data load");
            return;
        }

        logger.info("DataLoader enabled, clearing and loading data");
        
        try {
            // Clear existing data in reverse order to respect foreign key constraints
            tripRepository.deleteAll();
            itineraryRepository.deleteAll();
            userRepository.deleteAll();

            // Create users
            User user1 = User.builder()
                    .firstName("Nicole")
                    .lastName("Kidman")
                    .contactNo("12345678")
                    .email("nicole.kidman@gmail.com")
                    .countryOfOrigin("USA")
                    .build();
            User user2 = User.builder()
                    .firstName("Nicole")
                    .lastName("Changmin")
                    .contactNo("12345678")
                    .email("nicole.changmin@gmail.com")
                    .countryOfOrigin("Singapore")
                    .build();
            User user3 = User.builder()
                    .firstName("Mona")
                    .lastName("Lisa")
                    .contactNo("12345678")
                    .email("mona.lisa@gmail.com")
                    .countryOfOrigin("Italy")
                    .build();
            User user4 = User.builder()
                    .firstName("Peter")
                    .lastName("Parker")
                    .contactNo("12345678")
                    .email("peter.parker@gmail.com")
                    .countryOfOrigin("USA")
                    .build();

            // Save users
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);

            // Create itineraries for user1
            Itinerary itinerary1 = Itinerary.builder()
                    .destination("Paris")
                    .startDate(LocalDate.of(2025, 9, 1))
                    .endDate(LocalDate.of(2025, 9, 7))
                    .user(user1)
                    .notes("Visit Eiffel Tower")
                    .build();
            Itinerary itinerary2 = Itinerary.builder()
                    .destination("Rome")
                    .startDate(LocalDate.of(2025, 10, 1))
                    .endDate(LocalDate.of(2025, 10, 7))
                    .user(user1)
                    .notes("Visit Colosseum")
                    .build();

            // Save itineraries
            itineraryRepository.save(itinerary1);
            itineraryRepository.save(itinerary2);

            // Create trips for itinerary1
            Trip trip1 = Trip.builder()
                    .tripDay(1)
                    .activityType("Sightseeing")
                    .activityDesc("Visit Eiffel Tower")
                    .price(20.0)
                    .itinerary(itinerary1)
                    .build();
            Trip trip2 = Trip.builder()
                    .tripDay(2)
                    .activityType("Dining")
                    .activityDesc("Dinner at Le Jules Verne")
                    .price(150.0)
                    .itinerary(itinerary1)
                    .build();

            // Save trips
            tripRepository.save(trip1);
            tripRepository.save(trip2);

            logger.info("Data loaded successfully: {} users, {} itineraries, {} trips",
                        userRepository.count(), itineraryRepository.count(), tripRepository.count());
        } catch (Exception e) {
            logger.error("Error loading data: {}", e.getMessage());
            throw e; // Re-throw to halt startup if critical
        }
    }
}