package shopping.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import org.springframework.web.reactive.function.client.ClientResponse;

public class BadWordFilter {

    private static final String PURGOMALUM_API_URL = "https://www.purgomalum.com/service/containsprofanity?text=";
    private final WebClient webClient;

    public BadWordFilter() {
        HttpClient httpClient = HttpClient.create();
        this.webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(PURGOMALUM_API_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
                .build();
    }

    public boolean containsBadWords(String text) {
        ClientResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder.path(text).build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .block();

        if (response != null && response.statusCode() == HttpStatus.OK) {
            return Boolean.parseBoolean(response.bodyToMono(String.class).block());
        } else if (response != null && response.statusCode() == HttpStatus.NOT_ACCEPTABLE) {
            return false;
        } else {
            throw new RuntimeException("Error occurred while checking bad words");
        }
    }
}