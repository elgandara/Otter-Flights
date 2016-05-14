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
    private Double totalAmount;
    private String time;

    public Transaction() {
        this.id = 0;
        this.type = "";
        this.username = "";
        this.flightNumber = "";
        this.departureLocation = "";
        this.arrivalLocation = "";
        this.ticketQuantity = 0;
        this.totalAmount = 0.0;
        this.time = "";
    }

    public Integer getId() {return id;}
    public String getType() {return type;}
    public String getUsername() {return username;}
    public String getFlightNumber() {return flightNumber;}
    public String getDepartureLocation() {return departureLocation;}
    public String getArrivalLocation() {return arrivalLocation;}
    public Integer getTicketQuantity() {return this.ticketQuantity;}
    public Double getTotalAmount() {return this.totalAmount;}
    public String getTime() {return this.time;}

    public void setId(Integer id) {this.id = id;}
    public void setType(String type) {this.type = type;}
    public void setUsername(String username) {this.username = username;}
    public void setFlightNumber(String flightNumber) {this.flightNumber = flightNumber;}
    public void setDepartureLocation(String departureLocation) {this.departureLocation = departureLocation;}
    public void setArrivalLocation(String arrivalLocation) {this.arrivalLocation = arrivalLocation;}
    public void setTicketQuantity(Integer ticketQuantity) {this.ticketQuantity = ticketQuantity;}
    public void setTotalAmount(Double totalAmount) {this.totalAmount = totalAmount;}
    public void setTime(String time) {this.time = time;}

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
