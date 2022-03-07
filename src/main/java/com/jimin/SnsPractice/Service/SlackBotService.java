package com.jimin.SnsPractice.Service;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class SlackBotService {

    @Value("${slackBotToken}")
    private String slackToken;

    public void sendPhotoToUser(String intraId) {
        String url = "https://slack.com/api/files.upload";
        String img = "https://profile.intra.42.fr/users/jimkwon/photo";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + slackToken);
        headers.add("Content-type", "application/json; charset=utf-8");

        String slackId = getSlackIdByEmail(intraId);
        String body = "{\"channel\": \"" + slackId + "\", " +
                "\"attachments\": [{\"text\": \"글과함께 보낸다\", \"image_url\": " + img + "}]}";

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);

    }

    public void sendMessageToUser(String intraId) {
        String url = "https://slack.com/api/chat.postMessage";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + slackToken);
        headers.add("Content-type", "application/json; charset=utf-8");

        String slackId = getSlackIdByEmail(intraId);
        String body = "{\"channel\": \"" + slackId + "\", \"text\" : \"" + intraId + " 님, 반갑습니다! \"}";

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);

    }

    public String getSlackIdByEmail(String intraId) {
        String url = "https://slack.com/api/users.lookupByEmail";
        String email = intraId + "@student.42seoul.kr";
        url += "?email=" + email;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + slackToken);
        headers.add("Content-type", "application/x-www-form-urlencoded");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                String.class
        );
        JSONObject jsonObject;
        jsonObject = new JSONObject(responseEntity.getBody());
        //System.out.println(jsonObject);
        String id = null;
        JSONObject profile = jsonObject.getJSONObject("user");
        id = (String)profile.get("id");
        return id;
    }
}
