package carwash.dibo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormsController {

    @GetMapping("/forms")
    public String getForms(){
        return "forms";
    }
}
