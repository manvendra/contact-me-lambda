package com.mps;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;


public class ContactMeHandler implements RequestHandler<APIGatewayProxyRequestEvent, ApiGatewayResponse> {

    public static final String COMMA = ",";
    public static final String EMPTY_STRING = "";

    @Override
    public ApiGatewayResponse handleRequest(APIGatewayProxyRequestEvent input, Context context) {


        printInput(input, context.getLogger());

        Response responseBody = new Response("Thanks for contacting", null);

        return ApiGatewayResponse
                .builder()
                .setStatusCode(202)
                .setObjectBody(responseBody)
                .setHeaders(Collections.singletonMap("X-Powered-By", "MPS Kaleidoscope service"))
                .build();
    }

    private void printInput(APIGatewayProxyRequestEvent input, LambdaLogger lambdaLogger) {

        lambdaLogger.log("Received the request " + input);

        lambdaLogger.log("Request Body " + input.getBody());

        lambdaLogger.log("\n---------------------------");
        lambdaLogger.log("  Headers are as follows");
        lambdaLogger.log("---------------------------");
        Map<String, String> headers = input.getHeaders();
        headers.entrySet()
                .forEach(e -> lambdaLogger.log("\t " + e.getKey() + " : " + e.getValue() + " "));

        lambdaLogger.log("\n---------------------------");
        lambdaLogger.log("  multiValueHeaders are as follows");
        lambdaLogger.log("---------------------------");
        Map<String, List<String>> multiValueHeaders = input.getMultiValueHeaders();
        multiValueHeaders
                .entrySet()
                .forEach(e -> lambdaLogger.log("\t " + e.getKey() + " : [ " + getAsString(e.getValue()) + " ] "));


    }

    private Object getAsString(List<String> value) {
        if (value != null && value.size() != 0)
            return value.stream().collect(Collectors.joining(COMMA));
        else
            return EMPTY_STRING;
    }
}
