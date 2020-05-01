package ppSpring11Task16.controller;

import ppSpring11Task16.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

/**
 * @author AkiraRokudo on 14.03.2020 in one of sun day
 */
@Controller
@RequestMapping("/admin")
public class AdminController {


    //TODO: переписать весь пакет в один бин для шорткатных отдач вьюх

    @GetMapping
    public String getUsers(Principal userP, ModelMap model) {
        return "admin";
    }

}
