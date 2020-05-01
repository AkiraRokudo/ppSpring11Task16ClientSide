package ppSpring11Task16.restcontroller;

import com.sun.deploy.nativesandbox.comm.Response;
import org.springframework.http.ResponseEntity;
import ppSpring11Task16.dto.RoleDto;
import ppSpring11Task16.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author AkiraRokudo on 26.04.2020 in one of sun day
 */
@RestController
public class RoleRestController {

    private RoleService roleService;

    @Autowired
    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    //содержит один метод для получения всех ролей
    @GetMapping("/admin/roles")
    public ResponseEntity<?> getAllRoles() {
        return ResponseEntity.ok(roleService.getAll().stream().map(roleDto -> roleDto.getName()).collect(Collectors.toList()));
    }
}
