package carwash.dibo.controller;

import carwash.dibo.bills.UtilityMeter;
import carwash.dibo.model.AutoChemistry;
import carwash.dibo.model.UtilityBills;
import carwash.dibo.service.AutoChemistryService;
import carwash.dibo.service.UtilityBillsService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Month;
import java.util.List;

@Controller
@AllArgsConstructor
public class TablesController {
    private final AutoChemistryService autoChemistryService;
    private final UtilityBillsService utilityBillsService;

    @GetMapping("/tables")
    public String getTables(){
        return "tables";
    }

    @Cacheable(value = "chemistryList")
    @ModelAttribute("autoChemistryList")
    public List<AutoChemistry> getAutoChemical() {
        return autoChemistryService.getLast4Rows();
    }

    @Cacheable(value = "billsList")
    @ModelAttribute("utilityBillsList")
    public List<UtilityBills> getUtilityBills() {
        return utilityBillsService.getLast4Rows();
    }

    @PostMapping("/refueled")
    public String refueled(@ModelAttribute AutoChemistry autoChemistry, RedirectAttributes redirectAttributes,
                           BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("refueledMessage", "Не выбраны параметры");
            return "redirect:/tables";
        }

        redirectAttributes.addFlashAttribute("autoChemistry", autoChemistry);

        return "redirect:refueled/success";
    }

    @GetMapping("/refueled/success")
    public String redirectedRefueled(@ModelAttribute AutoChemistry autoChemistry, Model model) {

        String message = autoChemistryService
                .refueled(autoChemistry.getName(), autoChemistry.getQuantityToChange(), autoChemistry.getStatus());

        model.addAttribute("refueledMessage", message);

        return "redirect:/tables";
    }

    @PostMapping("/utility-bills")
    public String getUtilityMeterValues(@RequestParam UtilityMeter meter, RedirectAttributes redirectAttributes,
                                      BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("error", "неверный формат числа");
            return "redirect:/tables";
        }

        redirectAttributes.addFlashAttribute("meter", meter);

        return "redirect:/utility-bills/success";
    }

    @GetMapping("/utility-bills/success")
    public String setUtilityBill(@RequestParam UtilityMeter meter, Month month, Model model){

        utilityBillsService.save(meter.getType(), month, meter.getValue(), meter.getCost());

        return "tables";
    }
}
