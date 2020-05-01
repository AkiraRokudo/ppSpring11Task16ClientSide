package ppSpring11Task16.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ppSpring11Task16.dto.UserDto;
import ppSpring11Task16.model.RoleData;
import ppSpring11Task16.model.UserData;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

/**
 * @author AkiraRokudo on 09.04.2020 in one of sun day
 */
@Service
@Primary
public class UserLoginService implements UserDetailsService {

    //тут рестшаблоны для доступа
    //у клиента нет своих данных. но ему их нужно откуда-то брать
    //он может получить только данные в виде дто.

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = null;
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
            HttpEntity<String> request = new HttpEntity(username,headers);

            //определили куда шлем
            // request url
            String domain = "http://localhost";
            String port = ":8081";
            String loginPart = "/user/principalInfo";
            StringBuilder sb = new StringBuilder();
            String url = sb.append(domain).append(port).append(loginPart).toString();

            //создали шаблон
            RestTemplate loginTemplate = new RestTemplate();

            //послали и получили ответ
            ResponseEntity<UserDto> response = loginTemplate.exchange(url, HttpMethod.POST, request,UserDto.class);

            //Преобразовали ответ в нужный нам формат
            // get JSON response. Это на случай если конвертер сам не справится
            //String json = response.getBody();
            userDto = response.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        if (userDto == null) {
            throw new UsernameNotFoundException("User not found");
        }

        //начинается самое интересное
        //полученные данные мы разбиваем на части - юзера и роли
        //после чего упаковав роли в множество, совместно с остальными
        //нужными полями укладываем в нашу "модельку" юзера
        Set<RoleData> roles = new HashSet<>();
        userDto.getRoles().stream().forEach(rs -> roles.add(new RoleData(rs)));

        UserData user = new UserData(userDto.getLogin(), userDto.getPassword(), roles);
        return user;
    }

}
