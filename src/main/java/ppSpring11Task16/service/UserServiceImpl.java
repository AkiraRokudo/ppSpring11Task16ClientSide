package ppSpring11Task16.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ppSpring11Task16.dto.UserDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * @author AkiraRokudo on 29.02.2020 in one of sun day
 */
@Service
public class UserServiceImpl implements UserService {
//
//                        _oo0oo_
//                       o8888888o
//                       88" . "88
//                       (| -_- |)
//                       0\  =  /0
//                     ___/`---'\___
//                   .' \\|     |// '.
//                  / \\|||  :  |||// \
//                 / _||||| -:- |||||- \
//                |   | \\\  -  /// |   |
//                | \_|  ''\---/''  |_/ |
//                \  .-\__  '-'  ___/-. /
//              ___'. .'  /--.--\  `. .'___
//          ."" '<  `.___\_<|>_/___.' >' "".
//         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//         \  \ `_.   \_ __\ /__ _/   .-` /  /
//     =====`-.____`.___ \_____/___.-`___.-'=====
//                       `=---='
//
//
//     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//
//              God Bless         No Bugs
//

    @Override
    public UserDto addObject(UserDto userDto) {
        try {

            // create headers
            HttpHeaders headers = new HttpHeaders();
            // create auth credentials
            String authStr = "ADMIN:ADMIN";
            String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
            headers.add("Authorization", "Basic " + base64Creds);
            //если отдаем объект - говорим об этом в хеддере

            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            // create request
            HttpEntity<String> request = new HttpEntity(userDto,headers);

            //определили куда шлем
            // request url
            String domain = "http://localhost";
            String port = ":8081";
            String loginPart = "/admin/create" ;
            StringBuilder sb = new StringBuilder();
            String url = sb.append(domain).append(port).append(loginPart).toString();

            //создали шаблон
            RestTemplate userTemplate = new RestTemplate();

            //послали и получили ответ
            ResponseEntity<?> response = userTemplate.postForEntity(url, request,Object.class);
            //userTemplate.postForEntity(url,request,UserDto.class);
            //Преобразовали ответ в нужный нам формат
            // get JSON response. Это на случай если конвертер сам не справится
            //String json = response.getBody();
            HttpStatus status = response.getStatusCode();
            if(HttpStatus.OK != status) {
                throw new IllegalStateException("Exception on serverside");
            }
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return null; //а потому что не задумывалось
    }

    @Override
    public void editObject(UserDto userDto) {
        try {

            // create headers
            HttpHeaders headers = new HttpHeaders();
            // create auth credentials
            String authStr = "ADMIN:ADMIN";
            String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
            headers.add("Authorization", "Basic " + base64Creds);
            //если отдаем объект - говорим об этом в хеддере

            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            // create request
            HttpEntity<String> request = new HttpEntity(userDto,headers);

            //определили куда шлем
            // request url
            String domain = "http://localhost";
            String port = ":8081";
            String loginPart = "/admin/edit" ;
            StringBuilder sb = new StringBuilder();
            String url = sb.append(domain).append(port).append(loginPart).toString();

            //создали шаблон
            RestTemplate userTemplate = new RestTemplate();

            //послали и получили ответ
            ResponseEntity<?> response = userTemplate.postForEntity(url, request,Object.class);
            //userTemplate.postForEntity(url,request,UserDto.class);
            //Преобразовали ответ в нужный нам формат
            // get JSON response. Это на случай если конвертер сам не справится
            //String json = response.getBody();
            HttpStatus status = response.getStatusCode();
            if(HttpStatus.OK != status) {
                throw new IllegalStateException("Exception on serverside");
            }
        } catch (RestClientException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param id искомого юзера
     * @return null если дали null или несуществующий айдишник
     */
    @Override
    public UserDto getObject(Long id) {
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
            HttpEntity<String> request = new HttpEntity(null,headers);

            //определили куда шлем
            // request url
            String domain = "http://localhost";
            String port = ":8081";
            String loginPart = "/admin/edituser/" + id;
            StringBuilder sb = new StringBuilder();
            String url = sb.append(domain).append(port).append(loginPart).toString();

            //создали шаблон
            RestTemplate userTemplate = new RestTemplate();

            //послали и получили ответ
            ResponseEntity<UserDto> response = userTemplate.exchange(url, HttpMethod.GET, request,UserDto.class);

            //Преобразовали ответ в нужный нам формат
            // get JSON response. Это на случай если конвертер сам не справится
            //String json = response.getBody();
            userDto = response.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return userDto;
    }

    /**
     * @param login искомого юзера
     * @return null если дали null или несуществующий айдишник
     */
    @Override
    public UserDto getUserByLogin(String login) {
        return getObjectByParameters("login", login);
    }

    /**
     * @param id если null - Ничего не удалит
     */
    @Override
    public void removeObject(Long id) {
        UserDto deleteUser = getObject(id);
        if (deleteUser != null) {
            removeObject(deleteUser);
        }
    }

    @Override
    public void removeObject(UserDto userDto) {
        try {

            // create headers
            HttpHeaders headers = new HttpHeaders();
            // create auth credentials
            String authStr = "ADMIN:ADMIN";
            String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
            headers.add("Authorization", "Basic " + base64Creds);
            //если отдаем объект - говорим об этом в хеддере

            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            // create request
            HttpEntity<String> request = new HttpEntity(userDto,headers);

            //определили куда шлем
            // request url
            String domain = "http://localhost";
            String port = ":8081";
            String loginPart = "/admin/delete" ;
            StringBuilder sb = new StringBuilder();
            String url = sb.append(domain).append(port).append(loginPart).toString();

            //создали шаблон
            RestTemplate userTemplate = new RestTemplate();

            //послали и получили ответ
            ResponseEntity<?> response = userTemplate.postForEntity(url, request,Object.class);
            //userTemplate.postForEntity(url,request,UserDto.class);
            //Преобразовали ответ в нужный нам формат
            // get JSON response. Это на случай если конвертер сам не справится
            //String json = response.getBody();
            HttpStatus status = response.getStatusCode();
            if(HttpStatus.OK != status) {
                throw new IllegalStateException("Exception on serverside");
            }
        } catch (RestClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserDto getObjectByParameters(String paramName, String paramValue) {
        UserDto userDto = new UserDto();

        //по хорошему надо слать реквест с 2 параметрами, и их читать. но мы схитрим и ифом отсечем неподдерживаемые
        if ("login".equals(paramName)) {
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
                HttpEntity<String> request = new HttpEntity(paramValue,headers);

                //определили куда шлем
                // request url
                String domain = "http://localhost";
                String port = ":8081";
                String loginPart = "/userInfo";
                StringBuilder sb = new StringBuilder();
                String url = sb.append(domain).append(port).append(loginPart).toString();

                //создали шаблон
                RestTemplate userTemplate = new RestTemplate();

                //послали и получили ответ
                ResponseEntity<UserDto[]> response = userTemplate.exchange(url, HttpMethod.POST, request,UserDto[].class);

                //Преобразовали ответ в нужный нам формат
                // get JSON response. Это на случай если конвертер сам не справится
                //String json = response.getBody();
                userDto = response.getBody()[0];
            } catch (RestClientException e) {
                e.printStackTrace();
            }

        } else {
            throw new IllegalArgumentException("можно искать только по логину");
        }

        return userDto;
    }

    @Override
    public List<UserDto> getAll() {
        List<UserDto> users = new ArrayList<>();
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
                String loginPart = "/admin/allusers";
                StringBuilder sb = new StringBuilder();
                String url = sb.append(domain).append(port).append(loginPart).toString();

                //создали шаблон
                RestTemplate userTemplate = new RestTemplate();

                //послали и получили ответ
                ResponseEntity<UserDto[]> response = userTemplate.exchange(url, HttpMethod.GET, request,UserDto[].class);

                //Преобразовали ответ в нужный нам формат
                // get JSON response. Это на случай если конвертер сам не справится
                //String json = response.getBody();
                users = Arrays.asList(response.getBody());
            } catch (RestClientException e) {
                e.printStackTrace();
            }

        return users;
    }

    @Override
    public Boolean isAdmin(UserDto user) {
        return user.getRoles().contains("Admin");
    }
}
