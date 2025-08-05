package com.example.travelplanner.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trip")
@Data // Provides getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Creates a no-argument constructor
@AllArgsConstructor // Creates a constructor with all arguments
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Day must not be null")
    @Min(value = 1, message = "Day must be within 1 to end of itinerary day")
    private Integer tripDay;

    @NotBlank(message = "Input activity type")
    @Size(max = 100, message = "Activity type must be less than or equal to 100 characters")
    private String activityType;

    @NotBlank(message = "Activity description cannot be blank")
    @Size(max = 500, message = "Activity description must be less than or equal to 500 characters")
    private String activityDesc;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be non-negative")
    private Double price;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "itinerary_id", referencedColumnName = "id")
    private Itinerary itinerary;

    //public static Object builder() {
        // TODO Auto-generated method stub
    //    throw new UnsupportedOperationException("Unimplemented method 'builder'");
    //} 

    public static TripBuilder builder() {
        return new TripBuilder();
    }

    public static class TripBuilder {
        private Integer tripDay;
        private String activityType;
        private String activityDesc;
        private Double price;
        private Itinerary itinerary;

        public TripBuilder tripDay(Integer tripDay) {
            this.tripDay = tripDay;
            return this;
        }

        public TripBuilder activityType(String activityType) {
            this.activityType = activityType;
            return this;
        }

        public TripBuilder activityDesc(String activityDesc) {
            this.activityDesc = activityDesc;
            return this;
        }

        public TripBuilder price(Double price) {
            this.price = price;
            return this;
        }

        public TripBuilder itinerary(Itinerary itinerary) {
            this.itinerary = itinerary;
            return this;
        }

        public Trip build() {
            Trip trip = new Trip();
            trip.setTripDay(this.tripDay);
            trip.setActivityType(this.activityType);
            trip.setActivityDesc(this.activityDesc);
            trip.setPrice(this.price);
            trip.setItinerary(this.itinerary);
            return trip;
        }
    }
}