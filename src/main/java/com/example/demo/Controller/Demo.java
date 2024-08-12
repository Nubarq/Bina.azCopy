package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Demo {

    //@ResponseBody ---> for sending strings

    @GetMapping("/main/demo")
    public String demo() {
        System.out.println("Connected!");

        return "redirect:/login.html";
    }

    @RequestMapping("/data")
    public String showRegistrationForm() {
        return "redirect:/register.html"; // return the name of the HTML file (without .html)
    }

    @PostMapping("/data")
    public ModelAndView registerUser(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        // Process the form data (e.g., save to database)
        // For demonstration, just return the user data in a view
        ModelAndView mav = new ModelAndView("registrationSuccess");
        System.out.println(username + "  " + email + "  " + password);
        //üstteki kod çalışıyor ve yukarıdan datayı alıyor ama  yönlendirmede hata var ve dbye aha atılmıyor
        mav.addObject("username", username);
        return mav;
    }
}
