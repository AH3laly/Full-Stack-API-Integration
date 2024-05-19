package com.neurogine.revenumonster.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neurogine.revenumonster.configurations.RevenumonsterServiceConfiguration;
import com.neurogine.revenumonster.messages.ApiResponse;
import com.neurogine.revenumonster.messages.TopupRequest;

@RestController
@RequestMapping("/api")
public class RevenumonsterController {

    @Autowired
    RevenumonsterServiceConfiguration.ApiGateway revenumonsterApiService;

    @GetMapping("/topup")
    public ResponseEntity<?> topup(HttpServletRequest request,
            @RequestParam("amount") Integer amount) {
        try {

            TopupRequest topupRequest = new TopupRequest(50);

            Message<String> requestMessage = MessageBuilder.withPayload(topupRequest.toString()).build();

            Message<String> responseMessage = revenumonsterApiService.topup(requestMessage);

            return new ResponseEntity<>(responseMessage.getPayload().toString(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<String>(ApiResponse.STATUS.ERROR, e.getMessage(), ""), HttpStatus.BAD_REQUEST);
        }
    }
}
