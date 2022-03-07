package com.jimin.SnsPractice.Service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
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
        String url = "https://slack.com/api/chat.postMessage";
        String img = "https://profile.intra.42.fr/users/jimkwon/photo";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + slackToken);
        headers.add("Content-type", "application/json; charset=utf-8");

        String slackId = getSlackIdByEmail(intraId);
        JSONObject jsonObject = new JSONObject();
        JSONArray arr =new JSONArray();
        jsonObject.put("channel", slackId);
        JSONObject attachments = new JSONObject();
        attachments.put("image_url", "https://is5-ssl.mzstatic.com/image/thumb/Purple3/v4/d3/72/5c/d3725c8f-c642-5d69-1904-aa36e4297885/source/256x256bb.jpg");
        attachments.put("text", "HELLO");
        attachments.put("pre-text", "HELLO");
        arr.put(attachments);
        jsonObject.put("attachments", arr);

        String body = jsonObject.toString();
        System.out.println(body);
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
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("channel", slackId);
        jsonObject.put("text", intraId + " 님, 반갑습니다!");
        String body = jsonObject.toString();

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
        JSONObject profile = jsonObject.getJSONObject("user");
        String id = (String)profile.get("id");
        return id;
    }
}
