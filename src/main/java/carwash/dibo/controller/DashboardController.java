package carwash.dibo.controller;

import carwash.dibo.integration.NatureliaOrderService;
import carwash.dibo.integration.WeatherService;
import carwash.dibo.validator.DashboardValidator;
import carwash.dibo.common.AutoChemistryGoods;
import carwash.dibo.model.Malfunctions;
import carwash.dibo.model.WorkingDay;
import carwash.dibo.service.*;
import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
public class DashboardController {
    private final StoreQuantityService storeQuantityService;
    private final WorkingDayService workingDayService;
    private final DashboardValidator dashboardValidator;
    private final MalfunctionsService malfunctionsService;
    private final WeatherService weatherService;
    private final NatureliaOrderService natureliaOrderService;

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
        return storeQuantityService.getCurrentQuantityByName(AutoChemistryGoods.ACTIVE_FOAM.getName());
    }

    @ModelAttribute("malfunctionsList")
    public List<Malfunctions> getAllByOpenByDate(){
        return malfunctionsService.findAllByOpenByDateDesc();
    }

    @ModelAttribute("storeQuantityWax")
    public int getCurrentQuantityWax(){
        return storeQuantityService.getCurrentQuantityByName(AutoChemistryGoods.WAX.getName());
    }


    @GetMapping("/openDay")
    public String openDay(Model model) {

        if (!workingDayService.firstTimeOpenToday()){
            model.addAttribute("errorOpenDay", "Сегодня смена уже открыта");
            return "dashboard";
        }
        if (workingDayService.findByOpenTrue().size() > 0){
            model.addAttribute("errorOpenDoubleDay", "Закройте предыдущую смену");
            return "dashboard";
        }

        workingDayService.openWorkingDay();

        return "redirect:/dashboard";
    }

    @PostMapping("/closeDay")
    public String closeDay(@RequestParam(value = "tenCoins", required = false) String tenCoins,
                           @RequestParam (value = "diboCoins", required = false) String diboCoins,
                           @RequestParam (value = "cashOnBox", required = false) String cashOnBox,
                           @RequestParam (value = "cashBill", required = false) String cashBillAcceptor,
                           Model model){

        String errorMessage = dashboardValidator.getFieldsValidate(tenCoins, diboCoins, cashOnBox);
        if (!errorMessage.isEmpty()) {
            model.addAttribute("error", errorMessage);
            return "dashboard";
        }

        DashboardValidator validated = dashboardValidator.fieldsTypeValidate(tenCoins, diboCoins, cashOnBox, cashBillAcceptor);
        if (!validated.getErrorMessage().isEmpty()){
            model.addAttribute("error", validated.getErrorMessage());
            return "dashboard";
        }

        List<WorkingDay> openDayList = workingDayService.findByOpenTrue();
        if (openDayList.isEmpty()) {
            model.addAttribute("error", "Смена еще не открыта");
            return "dashboard";
        }
        if (openDayList.size() > 1) {
            throw new RuntimeException("Can't be open more the one working day");
        }

        WorkingDay openDay = openDayList.get(0);
        workingDayService.closeWorkingDay(openDay, validated.getTenCoins(), validated.getDiboCoins(),
                validated.getCashOnBox(), validated.getCashBillAcceptor());

        model.addAttribute("dayClosed", "Смена успешно закрыта");

        return "dashboard";
    }

    @PostMapping("/addMalfunction")
    public String addMalfunction(@ModelAttribute Malfunctions malfunctions, BindingResult result, Model model){
        model.addAttribute("malfunctions", malfunctions);

        if (result.hasErrors()){
            model.addAttribute("errorField", "Неверные параметры");
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
