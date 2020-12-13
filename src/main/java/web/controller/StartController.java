package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import web.dao.UserDao;
import web.model.User;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * 03.12.2020
 *
 * @author MescheRGen
 */

@Controller
public class StartController {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/")
    public String printWelcome(ModelMap model, Principal principal) {
        List<String> messages = new ArrayList<>();
        if(principal != null) {
            User user = userDao.getByUsername(principal.getName());
            messages.add(String.format("Hello, %s!",user.getFirstName()));
            model.addAttribute("userId", user.getId());
        } else {
            messages.add("Hello, anonymous!");
        }

        messages.add("Im F I N A L L Y ok, and SECURE!");
        messages.add("CongratZ!");
        model.addAttribute("messages", messages);
        return "index";
    }
}
