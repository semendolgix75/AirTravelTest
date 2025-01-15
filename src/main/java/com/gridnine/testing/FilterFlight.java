package com.gridnine.testing;

import java.util.List;
/**
        * Сервис фильтрации набора перелётов согласно различным правилам
 */
public interface FilterFlight {

    /**
     * Исключение вылетов до текущего момента времени из общего списка
     *
     * @param flights общий список перелетов
     * @return отфильтрованный список без вылетов до текущего момента времени
     */
    List<Flight> filterFlightBeforeCurrentTime(List<Flight> flights);
    /**
     * Исключение перелетов, где сегменты с датой прилёта раньше даты вылета из общего списка
     *
     * @param flights общий список перелетов
     * @return отфильтрованный список без перелетов,
     * где сегменты с датой прилёта раньше даты вылета из общего списка
     */

    List<Flight> filterSegmentsWithArrivalDateEarlierThatDepartureDate(List<Flight> flights);
    /**
     * Исключение перелетов, где общее время, проведенное на земле, превышает два часа
     *
     * @param flights общий список перелетов
     */
    List<Flight> flightsWhereTotalTimeExceedsTwoHours(List<Flight> flights);
}
