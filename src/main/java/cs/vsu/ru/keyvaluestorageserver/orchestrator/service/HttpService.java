package cs.vsu.ru.keyvaluestorageserver.orchestrator.service;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.Set;

@Component
public class HttpService {
    public HttpResponse createGetHttpResponse(String url) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet post = new HttpGet(url);
        HttpResponse response;

        try {
            response = client.execute(post);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return response;
    }

    private HttpResponse createBaseHttpResponse(String json, String url) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        if (Objects.nonNull(json)) {
            post.setHeader("Content-type", "application/json");

            StringEntity entity;

            try {
                entity = new StringEntity(json);
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex);
            }

            post.setEntity(entity);
        }
        HttpResponse response;

        try {
            response = client.execute(post);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return response;
    }

    private int createSimpleHttpRequest(String json, String url) {
        HttpResponse response = createBaseHttpResponse(json, url);

        return response.getStatusLine().getStatusCode();
    }

    public void makeRepIsMaster(String url) {
        createBaseHttpResponse(null, url);
    }

    public String getRepState(String url) {
        var response = createGetHttpResponse(url);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
            return br.readLine();
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }

    public void sendActualName(Set<String> data, String url) {
        Gson gson = new Gson();
        String json = gson.toJson(data);

        createSimpleHttpRequest(json, url);
    }

    public void deleteServiceFromEureka(String serviceId, String eurekaServerUrl) {
        String url = eurekaServerUrl + "/eureka/apps/" + serviceId;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                entity,
                String.class);


        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            throw new RuntimeException("Failed to delete service from Eureka");
        }
    }

}
