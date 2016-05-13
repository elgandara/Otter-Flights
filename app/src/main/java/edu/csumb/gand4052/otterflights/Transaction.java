package edu.csumb.gand4052.otterflights;

/**
 * Author: elgandara
 * Created: 5/11/16
 */
public class Transaction {

    private Integer id;
    private String type;
    private String username;
    private String flightNumber;
    private String departureLocation;
    private String arrivalLocation;
    private Integer ticketQuantity;
    private String time;

    public Transaction() {
        this.id = 0;
        this.type = "";
        this.username = "";
        this.flightNumber = "";
        this.departureLocation = "";
        this.arrivalLocation = "";
        this.ticketQuantity = 0;
        this.time = "";
    }

    public Transaction(Integer id, String type, String username,
                       String flightNumber, String departureLocation,
                       String arrivalLocation, Integer ticketQuantity,
                       String time) {
        this.id = id;
        this.type = type;
        this.username = username;
        this.flightNumber = flightNumber;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.ticketQuantity = ticketQuantity;
        this.time = time;
    }

    public Integer getId() {return id;}
    public String getType() {return type;}
    public String getUsername() {return username;}
    public String getFlightNumber() {return flightNumber;}
    public String getDepartureLocation() {return departureLocation;}
    public String getArrivalLocation() {return arrivalLocation;}
    public Integer getTicketQuantity() {return this.ticketQuantity;}
    public String getTime() {return this.time;}

    public boolean equals(Object object) {
        if (object instanceof Transaction) {
            Transaction transaction = (Transaction) object;

            return (this.id.equals(transaction.id) );
        }
        else {
            return false;
        }
    }

    public String toString() {

        return ("Transaction Id: " + this.id.toString() );
    }
}
