package carwash.dibo.service;

import java.time.LocalDate;
import java.time.Month;

public interface SalaryService {

    int getBetPerDay(String userName);
    int getTotalByMonth(Month month);
    int getQuantityOfShifts(String userName, Month month);

    boolean isPayed();

    void addNewByMonth();
    void update();
}
