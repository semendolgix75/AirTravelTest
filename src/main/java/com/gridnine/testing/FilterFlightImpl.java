package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис фильтрации набора перелётов согласно различным правилам
 */
public class FilterFlightImpl implements FilterFlight {
    /**
     * Исключение вылетов до текущего момента времени из общего списка
     *
     * @param flights общий список перелетов
     * @return отфильтрованный список без вылетов до текущего момента времени
     */

    @Override
    public List<Flight> filterFlightBeforeCurrentTime(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .anyMatch(segment -> segment.getDepartureDate().isAfter(LocalDateTime.now())))
                .collect(Collectors.toList());
    }

    /**
     * Исключение перелетов, где сегменты с датой прилёта раньше даты вылета из общего списка
     *
     * @param flights общий список перелетов
     * @return отфильтрованный список без перелетов,
     * где сегменты с датой прилёта раньше даты вылета из общего списка
     */
    @Override
    public List<Flight> filterSegmentsWithArrivalDateEarlierThatDepartureDate(List<Flight> flights) {

        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .allMatch(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate())))
                .collect(Collectors.toList());
    }

    /**
     * Исключение перелетов, где общее время, проведенное на земле, превышает два часа
     *
     * @param flights общий список перелетов
     * @return отфильтрованный список без перелетов, где общее время превышает два часа
     */
    @Override
    public List<Flight> flightsWhereTotalTimeExceedsTwoHours(List<Flight> flights) {

        return flights.stream()
                .filter(flight -> {
                    if (flight.getSegments().size() > 1) {
                        for (int i = 0; i < flight.getSegments().size() - 1; ) {
                            if (flight.getSegments().get(i + 1).getDepartureDate().getHour()
                                    - flight.getSegments().get(i).getArrivalDate().getHour() > 2) {
                                return false;
                            } else {
                                i++;
                            }
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}
