package edu.nick.aws.photoapp.users;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostHandlerTest {
  @Mock
  APIGatewayProxyRequestEvent input;
  @Mock
  Context context;

  @InjectMocks
  PostHandler postHandler;

  @Test
  public void successfulResponse() {
    String userid =  UUID.randomUUID().toString();
    JsonObject userDetails = new JsonObject();
    userDetails.addProperty("firstName", "Nikhil");
    userDetails.addProperty("lastName", "Gargatte");
    userDetails.addProperty("userid", userid);
    String userDetailsJsonString = new Gson().toJson(userDetails, JsonObject.class);
    when(input.getBody()).thenReturn(userDetailsJsonString);

    Map<String, String> requestHeaders = new HashMap<>();
    requestHeaders.put("Content-Type", "application/json");
    when(input.getHeaders()).thenReturn(requestHeaders);

    APIGatewayProxyResponseEvent result = postHandler.handleRequest(input, context);
    String content = result.getBody();
    assertEquals(200, result.getStatusCode());

    JsonObject contentJson = new JsonParser().parse(content).getAsJsonObject();
    assertTrue(contentJson.get("firstName").getAsString().equals("Nikhil"));
    assertTrue(contentJson.get("lastName").getAsString().equals("Gargatte"));
    assertTrue(contentJson.get("userid").getAsString().equals(userid));
  }
}
