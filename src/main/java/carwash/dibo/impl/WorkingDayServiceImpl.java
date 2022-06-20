package carwash.dibo.impl;

import carwash.dibo.model.User;
import carwash.dibo.model.WorkingDay;
import carwash.dibo.repository.WorkingDayRepository;
import carwash.dibo.service.UserService;
import carwash.dibo.service.WorkingDayService;
import carwash.dibo.utils.DateConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class WorkingDayServiceImpl implements WorkingDayService {
    private final WorkingDayRepository workingDayRepository;
    private final UserService userService;

    @Override
    public void openWorkingDay() {
        WorkingDay workingDay = new WorkingDay();
        User currentUser = userService .getCurrentUser();
        currentUser.getWorkingDays().add(workingDay);
        workingDay.setUser(currentUser);
        workingDay.setOpenDate(new Date());
        workingDay.setOpen(true);
        save(workingDay);
    }

    @Override
    public void closeWorkingDay(WorkingDay openDay ,int tenCoins, int diboCoins, int cashOnBox, int nonCash) {
        openDay.setTenCoins(tenCoins);
        openDay.setDiboCoins(diboCoins);
        openDay.setCashOnBox(cashOnBox);
        openDay.setNonCash(nonCash);
        openDay.setOpen(false);
        openDay.setCloseDate(new Date());
        workingDayRepository.save(openDay);
    }

    @Override
    public void save(WorkingDay workingDay) {
        workingDayRepository.save(workingDay);
    }

    @Override
    public boolean firstTimeOpenToday() {
        LocalDate openDate = DateConverter.convertToLocalDate(new Date());

        WorkingDay lastWorkingDay = workingDayRepository.findTopByOrderByIdDesc();
        if (lastWorkingDay == null) {
            return true;
        }

        LocalDate lastOpenDate = DateConverter.convertToLocalDate(lastWorkingDay.getOpenDate());

        return openDate.isAfter(lastOpenDate);
    }

    @Override
    public List<WorkingDay> getLastSevenDays(){
        List<WorkingDay> allDays = workingDayRepository.findAll();
        List<WorkingDay> lastSevenDays = new ArrayList<>();

        if (allDays.size() < 8) {
            Collections.reverse(allDays);
            return allDays;
        }

        for (int i = allDays.size() - 1; i > allDays.size() - 9; i--) {
            lastSevenDays.add(allDays.get(i));
        }

        return lastSevenDays;
    }

    @Override
    public List<WorkingDay> findByOpenTrue() {
        return workingDayRepository.findByOpenTrue();
    }
}
