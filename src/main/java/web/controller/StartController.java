package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 03.12.2020
 *
 * @author MescheRGen
 */

@Controller
public class StartController {

    @GetMapping("/")
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello");
        messages.add("Im F I N A L L Y ok, and SECURE!");
        messages.add("CongratZ!");
        model.addAttribute("messages", messages);
        return "index";
    }
}
