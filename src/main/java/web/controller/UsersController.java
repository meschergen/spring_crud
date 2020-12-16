package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.dao.UserDao;
import web.model.User;

import javax.validation.Valid;
import java.security.Principal;

/**
 * 04.12.2020
 *
 * @author MescheRGen
 */

@Controller
@RequestMapping("/users")
public class UsersController {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping()
    public String index(ModelMap model){
        model.addAttribute("userList", userDao.getAsList());
        return "users/list";
    }

    @GetMapping("/{id}")
    public String userInfo(@PathVariable("id") long id, ModelMap model){
        model.addAttribute("user", userDao.getById(id));
        return "users/info";
    }

    @GetMapping("/new")
    public String addUser(@ModelAttribute("user") User user) {
        return "users/new";
    }

    @GetMapping("/{id}/edit")
    public String editUserInfo(@PathVariable("id") long id, ModelMap model){
        model.addAttribute("user", userDao.getById(id));
        return "users/edit";
    }

    @GetMapping("/registration")
    public String userRegistration(@ModelAttribute("user") User user) {
        return "users/registration";
    }

    @PostMapping("/**")
    public String insertIntoDatabase(@ModelAttribute("user") @Valid User user,
                                     BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) {
            if (principal != null) {
                return "users/new";
            } else {
                return "users/registration";
            }
        }

        userDao.add(user);
        if (principal != null) {
            return "redirect:/users";
        }
        return "redirect:/";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult,
                             @PathVariable("id") long id) {

        if (bindingResult.hasErrors()) {
            return "users/edit";
        }
        userDao.update(id, user); //убрать id?
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id){
        userDao.remove(id);
        return "redirect:/users";
    }


}
