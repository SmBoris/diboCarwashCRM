package carwash.dibo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Month;

@Controller
public class ChartsController {
    
    @GetMapping("/charts")
    public String getCharts(){
        return "charts";
    }
}
