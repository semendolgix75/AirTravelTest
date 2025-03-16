package com.gridnine.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тестируем класс бизнес-логики по работе с фильтрацией набора перелётов согласно различным правилам
 */
class FilterFlightImplTest {

    private static final LocalDateTime DEPARTURE_DATE_ACTUAL = LocalDateTime.now().plusDays(1);
    private static final LocalDateTime DEPARTURE_DATE_NOT_ACTUAL = LocalDateTime.now().minusDays(1);
    private static final LocalDateTime ARRIVAL_DATE = DEPARTURE_DATE_ACTUAL.plusHours(4);
    private static final LocalDateTime ARRIVAL_DATE_NOT_CORRECT = LocalDateTime.now().minusHours(4);
    private Segment segmentCorrect;
    private Segment segmentNotCorrect1;
    private Segment segmentNotCorrect2;
    private final List<Flight> flightList = new ArrayList<>();
    private final List<Segment> segmentList1 = new ArrayList<>();
    private final List<Segment> segmentList2 = new ArrayList<>();
    private FilterFlightImpl filterService;

    @BeforeEach
    public void setup() {

        filterService = new FilterFlightImpl();

        segmentCorrect = new Segment(DEPARTURE_DATE_ACTUAL, ARRIVAL_DATE);
        segmentNotCorrect1 = new Segment(DEPARTURE_DATE_NOT_ACTUAL, ARRIVAL_DATE);
        segmentNotCorrect2 = new Segment(DEPARTURE_DATE_ACTUAL, ARRIVAL_DATE_NOT_CORRECT);
    }

    @Test
    public void testFilterFlightBeforeCurrentTime() {

        segmentList1.add(segmentCorrect);
        segmentList2.add(segmentNotCorrect1);

        flightList.add(new Flight(segmentList1));
        flightList.add(new Flight(segmentList2));

        assertEquals(2, flightList.size());

        List<Flight> result = filterService.filterFlightBeforeCurrentTime(flightList);

        assertEquals(1, result.size());
        assertEquals(DEPARTURE_DATE_ACTUAL, result.get(0).getSegments().get(0).getDepartureDate());
    }

    @Test
    public void testFilterSegmentsWithArrivalDateEarlierThatDepartureDate() {

        segmentList1.add(segmentCorrect);
        segmentList2.add(segmentNotCorrect2);

        flightList.add(new Flight(segmentList1));
        flightList.add(new Flight(segmentList2));

        assertEquals(2, flightList.size());

        List<Flight> result = filterService.filterSegmentsWithArrivalDateEarlierThatDepartureDate(flightList);

        assertEquals(1, result.size());
        assertEquals(ARRIVAL_DATE, result.get(0).getSegments().get(0).getArrivalDate());
    }

    @Test
    public void testFlightsWhereTotalTimeExceedsTwoHours() {

        segmentList1.add(segmentCorrect);
        segmentList2.add(segmentNotCorrect2);
        segmentList2.add(segmentNotCorrect1);

        flightList.add(new Flight(segmentList1));
        flightList.add(new Flight(segmentList2));

        assertEquals(2, flightList.size());

        List<Flight> result = filterService.flightsWhereTotalTimeExceedsTwoHours(flightList);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getSegments().size());
        assertEquals(ARRIVAL_DATE, result.get(0).getSegments().get(0).getArrivalDate());
    }
}