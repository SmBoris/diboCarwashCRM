package carwash.dibo.service;

import carwash.dibo.model.WorkingDay;

import java.util.List;

public interface WorkingDayService {

    List<WorkingDay> getLastSevenDays();
    List<WorkingDay> findByOpenTrue();

    boolean firstTimeOpenToday();

    void openWorkingDay();
    void closeWorkingDay(WorkingDay openDay ,int tenCoins, int DiboCoins, int cashOnBox, int cashBillAcceptor);
    void save(WorkingDay workingDay);


}
