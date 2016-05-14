package edu.csumb.gand4052.otterflights;

/**
 * Project: ${PROJECT}
 * File: ${FILE}
 * Author: elgandara
 * Create: 5/12/16
 */
public class Flight {

    private String flightNumber;
    private String departureLocation;
    private String arrivalLocation;
    private String departureTime;
    private Integer flightCapacity;
    private Double price;

    public Flight() {
        this.flightNumber = "";
        this.departureLocation = "";
        this.arrivalLocation = "";
        this.departureTime = "";
        this.flightCapacity = 0;
        this.price = 0.00;
    }

    public Flight(String flightNumber, String departureLocation,
                  String arrivalLocation, String departureTime,
                  Integer flightCapacity, Double price) {
        this.flightNumber = flightNumber;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureTime = departureTime;
        this.flightCapacity = flightCapacity;
        this.price = price;
    }

    // Accessor methods
    public String getFlightNumber() {return this.flightNumber;}
    public String getDepartureLocation() {return this.departureLocation;}
    public String getArrivalLocation() {return this.arrivalLocation;}
    public String getDepartureTime() {return this.departureTime;}
    public Integer getFlightCapacity() {return this.flightCapacity;}
    public Double getPrice() {return this.price;}

    // Mutator methods
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public void setArrivalLocation(String arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setFlightCapacity(Integer flightCapacity) {
        this.flightCapacity = flightCapacity;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    // Equality and toString
    public boolean equals(Object object) {
        if (object instanceof Flight) {
            Flight flight = (Flight) object;

            return (this.getFlightNumber().equals(flight.getFlightNumber() ) );
        }
        else {
            return false;
        }
    }

    public String toString() {

        return ("Flight Number: " + flightNumber);

    }

}
