package shopping.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

public class BadWordFilter {

    private static final String PURGOMALUM_API_URL = "https://www.purgomalum.com/service/containsprofanity?text=";

    public static boolean containsBadWords(String text) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setAccept(MediaType.parseMediaTypes("text/plain"));

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(PURGOMALUM_API_URL + text, String.class);
            return Boolean.parseBoolean(response.getBody());
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
                return false; // 기본적으로 false로 처리, 필요시 로깅 추가 가능
            }
            throw e;
        }
    }
}