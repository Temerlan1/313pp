package demo.controllers;

import demo.model.User;
import demo.service.RoleService;
import demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.security.Principal;

@Controller
//@RequestMapping()
public class UserController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UserController(UserService userService,RoleService roleService) {
        this.userService = userService;
        this.roleService=roleService;
    }
    @GetMapping("/login")
    public String login() {
        return "login1";
    }

  //  @PostMapping("/login")
  //  public String loginP(){
  //      return "redirect:/login";
  //  }

    @GetMapping("/logout1")
    public String getlogoutForm(Model model,Principal principal){
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "logout";
    }

    @GetMapping("/user")//МЕТОД ЮЗЕРА, А ОСТАЛЬНЫЕ ПЕРЕНЕСЕМ В AdminController
    public String getUser(Model model,Principal principal){//@PathVariable("id") long id){
        User user=userService.findByUsername(principal.getName());
        model.addAttribute("user",user);
        //if (user.getRoles().contains(roleService.findByName("ROLE_ADMIN"))){
            //return "show1";
        //}
       // model.addAttribute("user",userService.findById(id));
        return "/userPanel";//"2";
    }

}
