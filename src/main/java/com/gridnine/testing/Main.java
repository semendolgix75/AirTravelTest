package com.gridnine.testing;

import java.util.List;

public class Main {
    private static final FilterFlightImpl filterFlight = new FilterFlightImpl();
    private static final List<Flight> flights = FlightBuilder.createFlights();
    public static void main(String[] args) {

        System.out.println("Список всех полетов:");
        allFlightList();

        System.out.println("Все полеты с актуальной датой:");
        System.out.println(filterFlight.filterFlightBeforeCurrentTime(flights));

        System.out.println("Сегменты с датой прилета раньше даты вылета");
        System.out.println(filterFlight.filterSegmentsWithArrivalDateEarlierThatDepartureDate(flights));

        System.out.println("Перелеты, где общее время, проведенное на земле превышает два часа");
        System.out.println(filterFlight.flightsWhereTotalTimeExceedsTwoHours(flights));
    }



    private static void allFlightList() {

        System.out.println(flights);
    }
}