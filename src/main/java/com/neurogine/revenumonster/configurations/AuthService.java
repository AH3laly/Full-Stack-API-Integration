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
        
    	// This to make sure that this Thread would hit the Authentication End-point once,
    	// So, it would go to authenticate only if RevenumosterService.getAccessToken() returns null.
    	// However, this is not perfect for production, so, the Refresh token also should be implemented.
    	// But for a Testing project, it's fine.
        if(RevenumosterService.getAccessToken() == null) {
            revenumonsterApiService.authenticate(new AuthRequest("client_credentials"));
            System.out.println("Authenticated.");
        } else {
            System.out.println("Already Authenticated.");
        }
        
        // Nonce is a random String must be unique per each request,
        // So, we generate it once here by calling revenumosterService.generateNewNonce(),
        // And read anywhere else during the request using revenumosterService.getNonce().
        revenumosterService.generateNewNonce();
        
        // This is a UnixTimestamp, also should be unique per request,
        // So we generate it once here, and read it anywhere else using revenumosterService.getTimestamp()
        revenumosterService.generateTimestamp();
        revenumosterService.generateRequestSignature(message.getPayload().toString());
        return message;
    }
}