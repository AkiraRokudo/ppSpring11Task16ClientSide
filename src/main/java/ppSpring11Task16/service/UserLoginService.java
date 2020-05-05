package ppSpring11Task16.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
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
import ppSpring11Task16.components.ServerUrl;
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

    private ServerUrl serverUrl;
    private HttpHeaders basicAuthHeaders;

    @Autowired
    public UserLoginService(HttpHeaders basicAuthHeaders, ServerUrl serverUrl) {
        this.basicAuthHeaders = basicAuthHeaders;
        this.serverUrl = serverUrl;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = null;
        try {
            HttpEntity<String> request = new HttpEntity(username,basicAuthHeaders);
            String url = serverUrl.getServerUrl() + "/user/principalInfo";
            RestTemplate loginTemplate = new RestTemplate();
            //послали и получили ответ
            ResponseEntity<UserDto> response = loginTemplate.exchange(url, HttpMethod.POST, request,UserDto.class);

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
