package carwash.dibo.controller;

import carwash.dibo.bills.UtilityMeter;
import carwash.dibo.model.Expense;
import carwash.dibo.model.AutoChemistry;
import carwash.dibo.model.UtilityBills;
import carwash.dibo.service.ExpenseService;
import carwash.dibo.service.AutoChemistryService;
import carwash.dibo.service.UtilityBillsService;
import carwash.dibo.utils.DateConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
public class TablesController {
    private final AutoChemistryService autoChemistryService;
    private final UtilityBillsService utilityBillsService;
    private final ExpenseService additionalExpenseService;

    @GetMapping("/tables")
    public String getTables(){
        return "tables";
    }

    @ModelAttribute("autoChemistryList")
    public List<AutoChemistry> getAutoChemical4Rows() {
        return autoChemistryService.getLast4Rows();
    }

    @ModelAttribute("utilityBillsList")
    public List<UtilityBills> getUtilityBills() {
        return utilityBillsService.getLast7Rows();
    }

    @ModelAttribute("rusMonth")
    public List<String> getRusMonth() { return DateConverter.getRussianMonth();
    }

    @ModelAttribute("expenses")
    public List<Expense> getAdditionalExpenses() {
        return additionalExpenseService.getLast5Rows();

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
    public String getUtilityMeterValues(@ModelAttribute UtilityMeter meter,
                                        @RequestParam(value = "month") String month,
                                        RedirectAttributes redirectAttributes,
                                        BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("error", "неверный формат числа");
            return "redirect:/tables";
        }

        redirectAttributes.addFlashAttribute("meter", meter);
        redirectAttributes.addFlashAttribute("month", month);

        return "redirect:/utility-bills/success";
    }

    @GetMapping("/utility-bills/success")
    public String setUtilityBill(Model model){

        UtilityMeter meter = (UtilityMeter) model.getAttribute("meter");
        String month = (String) model.getAttribute("month");

        assert meter != null;
        assert month != null;

        utilityBillsService.save(
                meter.getType(),
                DateConverter.getMonthIndex(month),
                meter.getValue(),
                meter.getCost());

        return "redirect:/tables";
    }

    @PostMapping("/additional-expense")
    public String addExpense(@ModelAttribute Expense expense){
        expense.setDate(new Date());

        return "redirect:/tables";
    }
}
