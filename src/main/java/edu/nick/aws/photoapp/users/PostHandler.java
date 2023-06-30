package edu.nick.aws.photoapp.users;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Handler for requests to Lambda function.
 */
public class PostHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    String userid = UUID.randomUUID().toString();

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {

        String requestBody = input.getBody();

        Map<String, String> requestHeaders = input.getHeaders();

        final String USERID = "userid";

        Gson gson = new Gson();
        Map<String, String> userDetails = gson.fromJson(requestBody, Map.class);
        userDetails.put(USERID, userid);

        //TODO: Process user details

        Map<String, String> returnValue = new HashMap<>();
        returnValue.put("firstName", userDetails.get("firstName"));
        returnValue.put("lastName", userDetails.get("lastName"));
        returnValue.put(USERID, userDetails.get(USERID));

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response
                .withStatusCode(200)
                .withBody(gson.toJson(returnValue, Map.class));

        Map<String, String> responseHeaders = new HashMap<>();
        responseHeaders.put("Content-Type", "application/json");
        responseHeaders.put("Access-Control-Allow-Origin", "*");
        response.withHeaders(responseHeaders);

        return response;
    }

}
