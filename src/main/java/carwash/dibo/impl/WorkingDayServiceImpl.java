package carwash.dibo.impl;

import carwash.dibo.integration.weather.DailyWeatherService;
import carwash.dibo.integration.weather.WeatherService;
import carwash.dibo.model.DailyWeather;
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
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class WorkingDayServiceImpl implements WorkingDayService {

    private final WorkingDayRepository workingDayRepository;
    private final UserService userService;
    private final WeatherService weatherService;
    private final DailyWeatherService dailyWeatherService;
    private final DailyWeather dailyWeather;

    @Override
    public void openWorkingDay() {
        WorkingDay workingDay = new WorkingDay();
        User currentUser = userService .getCurrentUser();
        currentUser.getWorkingDays().add(workingDay);
        workingDay.setUser(currentUser);
        workingDay.setOpenDate(new Date());
        workingDay.setOpen(true);
        save(workingDay);
        log.info("Employee: " + workingDay.getUser().getUsername() + " open work shift at " + LocalDate.now());
    }

    @Override
    public void closeWorkingDay(int tenCoins, int diboCoins, int cashOnBox, int nonCash) {

        dailyWeatherService.updateDailyWeather(
                weatherService.getDailyAverageTemp(),
                weatherService.getDailyAverageWindSpeed(),
                weatherService.getDailyAverageCondition());

        dailyWeatherService.save(dailyWeather);
        weatherService.clearCurrentHistory();

        WorkingDay openDay = workingDayRepository.findByOpenTrue().get(0);
        openDay.setTenCoins(tenCoins);
        openDay.setDiboCoins(diboCoins);
        openDay.setCashOnBox(cashOnBox);
        openDay.setNonCash(nonCash);
        openDay.setOpen(false);
        openDay.setCloseDate(new Date());
        openDay.setDailyWeather(dailyWeather);

        workingDayRepository.save(openDay);

        log.info("Employee: " + openDay.getUser().getUsername() + " closed shift at: " + LocalDate.now());
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
        return workingDayRepository.findTop7ByOrderByIdDesc();
    }

    @Override
    public List<WorkingDay> findByOpenTrue() {
        return workingDayRepository.findByOpenTrue();
    }

    @Override
    public WorkingDay findTopByOrderByIdDesc() {
        return workingDayRepository.findTopByOrderByIdDesc();
    }
}
