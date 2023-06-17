package edu.nick.aws.photoapp.users;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

/**
 * Handler for requests to Lambda function.
 */
public class PostHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        String requestBody = input.getBody();

        Map<String, String> requestHeaders = input.getHeaders();

        Gson gson = new Gson();
        Map<String, String> userDetails = gson.fromJson(requestBody, HashMap.class);
        userDetails.put("userid", UUID.randomUUID().toString());

        //TODO: Process user details

        Map<String, String> returnValue = new HashMap<>();
        returnValue.put("firstName", userDetails.get("firstName"));
        returnValue.put("lastName", userDetails.get("lastName"));
        returnValue.put("userid", userDetails.get("userid"));

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response
                .withStatusCode(200)
                .withBody(gson.toJson(returnValue, Map.class))
                .withBody(gson.toJson(requestHeaders.get("Origin"), String.class));

        Map<String, String> responseHeaders = new HashMap<>();
        responseHeaders.put("Content-Type", "application/json");
        responseHeaders.put("Access-Control-Allow-Origin", "*");
        response.withHeaders(responseHeaders);

        return response;
    }

}
