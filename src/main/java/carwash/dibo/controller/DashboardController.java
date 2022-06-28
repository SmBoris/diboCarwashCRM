package carwash.dibo.controller;

import carwash.dibo.integration.WeatherService;
import carwash.dibo.common.AutoChemistryGood;
import carwash.dibo.model.Malfunctions;
import carwash.dibo.model.WorkingDay;
import carwash.dibo.service.*;
import carwash.dibo.validator.WorkingDayValidator;
import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
public class DashboardController {

    private final StoreQuantityService storeQuantityService;
    private final WorkingDayService workingDayService;
    private final MalfunctionsService malfunctionsService;
    private final WeatherService weatherService;
    private final WorkingDayValidator workingDayValidator;

    @GetMapping("/dashboard")
    public String dashboardPage(){
        return "dashboard";
    }

    @ModelAttribute("weather")
    public double weatherTemp() throws InterruptedException, JSONException, IOException {
        return weatherService.getCurrentTemperature();
    }

    @ModelAttribute("oneWeekWorkingList")
    public List<WorkingDay> getWorkingDays() {
        return workingDayService.getLastSevenDays();
    }

    @ModelAttribute("storeQuantityFoam")
    public int getCurrentQuantityFoam(){
        return storeQuantityService.getCurrentQuantityByName(AutoChemistryGood.ACTIVE_FOAM.getName());
    }

    @ModelAttribute("malfunctionsList")
    public List<Malfunctions> getAllByOpenByDate(){
        return malfunctionsService.findAllByOpenByDateDesc();
    }

    @ModelAttribute("storeQuantityWax")
    public int getCurrentQuantityWax(){
        return storeQuantityService.getCurrentQuantityByName(AutoChemistryGood.WAX.getName());
    }

    @GetMapping("/openDay")
    public String openDay(Model model) {

        String error = workingDayValidator.openCloseShiftValidate();
        if (!error.isEmpty()){
            model.addAttribute("error", error);
            return "dashboard";
        }

        workingDayService.openWorkingDay();

        return "redirect:/dashboard";
    }

    @PostMapping("/closeDay")
    public String closeDay(@ModelAttribute WorkingDay day, Model model, BindingResult bindingResult){

        String error = workingDayValidator.openCloseShiftValidate();
        if (!error.isEmpty()) {
            model.addAttribute("error", error);
            return "dashboard";
        }

        workingDayService.closeWorkingDay(day.getTenCoins(), day.getDiboCoins(),
                day.getCashOnBox(), day.getNonCash());

        model.addAttribute("success", "Смена успешно закрыта");

        return "dashboard";
    }

    @PostMapping("/addMalfunction")
    public String addMalfunction(@ModelAttribute Malfunctions malfunctions, BindingResult result, Model model){
        model.addAttribute("malfunctions", malfunctions);

        if (result.hasErrors()){
            model.addAttribute("error", "Неверные параметры");
            return "dashboard";
        }

        malfunctionsService.addMalfunction(malfunctions);

        return "redirect:/dashboard";
    }

    @PostMapping("/closeMalfunction")
    public String closeMalfunction(@RequestParam(value = "id") Long id) {
        malfunctionsService.closeMalfunction(malfunctionsService.findById(id));

        return "redirect:/dashboard";
    }
}
