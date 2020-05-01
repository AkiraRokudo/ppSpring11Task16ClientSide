package ppSpring11Task16.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author AkiraRokudo on 12.04.2020 in one of sun day
 */
@Controller
@RequestMapping("/")
public class LoginController {

    @GetMapping
    public String getLoginForm() {
        return "login";
    }

}
