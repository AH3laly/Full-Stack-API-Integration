package com.neurogine.revenumonster.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.neurogine.revenumonster.messages.AuthRequest;
import com.neurogine.revenumonster.messages.AuthResponse;
import com.neurogine.revenumonster.services.RevenumosterService;

@Service
public class AuthService {

	@Autowired
	RevenumonsterServiceConfiguration.ApiGateway revenumonsterApiService;
	@Autowired
	RevenumosterService revenumosterService;
    public Message<String> authenticateNewRequest(Message<String> message) throws Exception {
    	
    	if(RevenumosterService.getAccessToken() == null) {
    		AuthResponse authResponse = revenumonsterApiService.authenticate(new AuthRequest("client_credentials"));
    		System.out.println("Authenticating .......");
    		System.out.println("Authenticated: Access Token: " + authResponse.getAccessToken());
    		revenumonsterApiService.authenticate(new AuthRequest("client_credentials"));
    	} else {
    		System.out.println("Already Have Access Token: " + RevenumosterService.getAccessToken());
    	}
    	
    	revenumosterService.generateNewNonce();
        revenumosterService.generateTimestamp();
        revenumosterService.generateRequestSignature(message.getPayload().toString());
        return message;
    }
}