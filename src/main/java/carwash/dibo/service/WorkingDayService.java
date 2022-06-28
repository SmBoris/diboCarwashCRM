package carwash.dibo.service;

import carwash.dibo.model.WorkingDay;

import java.util.List;

public interface WorkingDayService {

    List<WorkingDay> getLastSevenDays();
    List<WorkingDay> findByOpenTrue();
    WorkingDay findTopByOrderByIdDesc();

    boolean firstTimeOpenToday();

    void openWorkingDay();
    void closeWorkingDay(int tenCoins, int DiboCoins, int cashOnBox, int cashBillAcceptor);
    void save(WorkingDay workingDay);


}
