package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.dao.UserDao;
import web.model.User;

/**
 * 04.12.2020
 *
 * @author MescheRGen
 */

@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserDao userDao;

    @GetMapping()
    public String index(ModelMap model){
        model.addAttribute("userList", userDao.getAsList());
        return "users/list";
    }

    @GetMapping("/{id}")
    public String userInfo(@PathVariable("id") int id, ModelMap model){
        model.addAttribute("user", userDao.getById((long) id));
        return "users/info";
    }

    @GetMapping("/addNew")
    public String addUser(ModelMap model) {
        model.addAttribute("user", new User());
        return "users/addNew";
    }

    @PostMapping()
    public String insertIntoDatabase(@ModelAttribute("user") User user) {
        userDao.add(user);
        //return "users/successfulAddition";
        return "redirect:/users";
    }


}
