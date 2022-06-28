package carwash.dibo.validator;

import carwash.dibo.model.WorkingDay;
import carwash.dibo.service.WorkingDayService;
import carwash.dibo.utils.DateConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class WorkingDayValidator {
    private final WorkingDayService workingDayService;

    public String openCloseShiftValidate(){
        LocalDate openDate = DateConverter.convertToLocalDate(new Date());

        WorkingDay lastWorkingDay = workingDayService.findTopByOrderByIdDesc();
        LocalDate lastOpenDate = DateConverter.convertToLocalDate(lastWorkingDay.getOpenDate());

        if(openDate.isAfter(lastOpenDate)) {
            return  "Сегодня смена уже открыта";
        }

        if (workingDayService.findByOpenTrue().size() > 0){
            return  "Закройте предыдущую смену";
        }

        if (workingDayService.findByOpenTrue().size() < 1) {
            return "Смена еще не открыта";
        }

        return "";
    }
}
