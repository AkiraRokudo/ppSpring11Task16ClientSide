package ppSpring11Task16.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ppSpring11Task16.dto.RoleDto;
import ppSpring11Task16.dto.UserDto;

import java.sql.SQLException;
import java.util.*;

/**
 * @author AkiraRokudo on 06.04.2020 in one of sun day
 */
@Service
public class RoleServiceImpl implements RoleService {

    //Шаблон для получения определенной роли по имени

    @Override
    public RoleDto addObject(RoleDto role) {
        throw new UnsupportedOperationException("Низя. Идите лесом, или к разработчику");
    }

    @Override
    public void editObject(RoleDto role) {
        throw new UnsupportedOperationException("Низя. Идите лесом, или к разработчику");
    }

    @Override
    public RoleDto getObject(Long id) {
        throw new UnsupportedOperationException("Низя. Идите лесом, или к разработчику");
    }

    @Override
    public RoleDto getRoleByName(String name) {
        return getObjectByParameters("name" , name);
    }

    @Override
    public void removeObject(Long id) {
        throw new UnsupportedOperationException("Низя. Идите лесом, или к разработчику");
    }

    @Override
    public void removeObject(RoleDto role) {
        throw new UnsupportedOperationException("Низя. Идите лесом, или к разработчику");
    }

    @Override
    public RoleDto getObjectByParameters(String paramName, String paramValue) {
        throw new UnsupportedOperationException("Низя. Идите лесом, или к разработчику");
    }

    @Override
    public List<RoleDto> getAll() {
        List<RoleDto> roles = new ArrayList<>();
        try {

            // create headers
            HttpHeaders headers = new HttpHeaders();
            // create auth credentials
            String authStr = "ADMIN:ADMIN";
            String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
            headers.add("Authorization", "Basic " + base64Creds);
            //если отдаем объект - говорим об этом в хеддере

//            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//            headers.setContentType(MediaType.TEXT_PLAIN);
            // create request
            HttpEntity<String> request = new HttpEntity(null,headers);

            //определили куда шлем
            // request url
            String domain = "http://localhost";
            String port = ":8081";
            String loginPart = "/admin/roles";
            StringBuilder sb = new StringBuilder();
            String url = sb.append(domain).append(port).append(loginPart).toString();

            //создали шаблон
            RestTemplate userTemplate = new RestTemplate();

            //послали и получили ответ
            ResponseEntity<RoleDto[]> response = userTemplate.exchange(url, HttpMethod.GET, request,RoleDto[].class);

            //Преобразовали ответ в нужный нам формат
            // get JSON response. Это на случай если конвертер сам не справится
            //String json = response.getBody();
            roles = Arrays.asList(response.getBody());
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return roles;
    }
}
