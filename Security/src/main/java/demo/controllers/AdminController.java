package demo.controllers;


import demo.model.User;
import demo.service.RoleService;
import demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    AdminController(UserService userService,RoleService roleService,PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.roleService=roleService;
        this.passwordEncoder=passwordEncoder;
    }

    @GetMapping("/{id}")//МЕТОД ЮЗЕРА, А ОСТАЛЬНЫЕ ПЕРЕНЕСЕМ В AdminController
    public String getUser(Model model,@PathVariable("id") long id){ //Principal principal){//@PathVariable("id") long id){
        model.addAttribute("user", userService.findById(id));
        // model.addAttribute("user",userService.findById(id));
        return "show1";
    }

    @GetMapping()
    public String getUsers(Model model){
        model.addAttribute("users",userService.findAll());
        model.addAttribute("thisUser", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("allRoles", roleService.findAll());
        model.addAttribute("newUser", new User());
        return "all1";
    }

    @GetMapping("/new")
    public String createUsersForm(@ModelAttribute("user") User user,Model model) {
        model.addAttribute("allRoles", roleService.findAll());
        return "new1";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("newUser") User user,
                             @RequestParam(value = "index", required = false) Long[] ids){
        if (ids != null) {
            for (Long roleId : ids) {
                user.addRole(roleService.findById(roleId));
            }
        } else {
            user.addRole(roleService.findByName("ROLE_USER"));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user.getUsername());
        System.out.println(user.getLastName());
        System.out.println(user.getPassword());
        System.out.println(user.getRoles());
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String userDelete(@PathVariable("id") long id){
        userService.findById(id).setRoles(Collections.emptyList());
        userService.removeUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/update/{id}")
    public String updateUserForm(@PathVariable("id") long id,Model model){
        model.addAttribute("user",userService.findById(id));
        model.addAttribute("roles",roleService.findAll());
        return "add";//"update";
    }

    @PostMapping("/{id}")
    public String updateUser(/*@Validated @RequestBody User user){//*/@ModelAttribute("user") User user,
                                                                      @RequestParam(value = "index", required = false) Long[] ids){
        System.out.println(user.getUsername());
        System.out.println(user.getLastName());
        System.out.println(user.getAge());
        System.out.println(user.getRoles());
        if (ids != null) {
            for (Long roleId : ids) {
                user.addRole(roleService.findById(roleId));
            }
        } else {
            user.addRole(roleService.findByName("ROLE_USER"));
        }
        user.setPassword(userService.findById(user.getId()).getPassword());
        System.out.println(user.getRoles());
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        userService.update(user);
        System.out.println();
        return "redirect:/admin";
    }

    @GetMapping("/add/{id}")
    public String addForm(@PathVariable("id") long id, Model model){
        model.addAttribute("user",userService.findById(id));
        return "add";
    }

  //  @PostMapping("/add/{id}/{name}")
  //  public String addRole(@PathVariable("id") long id,@PathVariable("name") String name){
  //      userService.findById(id).getRoles().add(repository.findByName(name));
  //      return "redirect:/admin";
  //  }
}
