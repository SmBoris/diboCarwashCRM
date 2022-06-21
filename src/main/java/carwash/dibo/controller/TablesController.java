package carwash.dibo.controller;

import carwash.dibo.common.AutoChemistryStatuses;
import carwash.dibo.exception.CantBeNegativeException;
import carwash.dibo.model.AutoChemistry;
import carwash.dibo.model.UtilityBills;
import carwash.dibo.service.AutoChemistryService;
import carwash.dibo.service.UtilityBillsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @ModelAttribute("autoChemistryList")
    public List<AutoChemistry> getAutoChemical() {
        return autoChemistryService.getLast4Rows();
    }

    @ModelAttribute("utilityBillsList")
    public List<UtilityBills> getUtilityBills() {
        return utilityBillsService.getLast4Rows();
    }

    @PostMapping("/refueled")
    public String refueled(@ModelAttribute AutoChemistry autoChemistry, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("error", "Неверно введены параметры");
            return "tables";
        }

        redirectAttributes.addFlashAttribute("autoChemistry", autoChemistry);

        return "redirect:refueled/success";
    }

    @GetMapping("/refueled/success")
    public String redirectedRefueled(@ModelAttribute AutoChemistry autoChemistry, BindingResult bindingResult, Model model) throws CantBeNegativeException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorData", "Неверно введены параметры");
            return "tables";
        }

        if (autoChemistry.getStatus().equals(AutoChemistryStatuses.PURCHASE)) {
            autoChemistryService.addPurchase(autoChemistry.getName(), autoChemistry.getQuantityToChange());
            model.addAttribute("successPurchase", autoChemistry.getName() + " успешно добавлена");
            return "redirect:/tables";
        }

        if (autoChemistry.getStatus().equals(AutoChemistryStatuses.REFUELED)) {
            try {
                autoChemistryService.refueled(autoChemistry.getName(), autoChemistry.getQuantityToChange());
            }
            catch (CantBeNegativeException e){
                model.addAttribute("errorRefueled", "Невозможно заправить больше, чем наличие на складе");
                return "tables";
            }

            model.addAttribute("successRefueled", autoChemistry.getName() + " успешно списана");

            return "redirect:/tables";
        }

        return "redirect:/tables";
    }
}
