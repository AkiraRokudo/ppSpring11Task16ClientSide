package ppSpring11Task16.controller;

import ppSpring11Task16.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

/**
 * @author AkiraRokudo on 08.04.2020 in one of sun day
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public String getUserInfo() {
        return "user";
    }

}
