package com.neurogine.revenumonster.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.messaging.Message;

import com.neurogine.revenumonster.messages.AuthRequest;
import com.neurogine.revenumonster.messages.AuthResponse;
import com.neurogine.revenumonster.services.RevenumosterService;

@Configuration
@EnableIntegration
public class RevenumonsterServiceConfiguration {

    @Autowired
    RevenumosterService revenumosterService;

    @MessagingGateway
    public interface ApiGateway {
        
        @Gateway(requestChannel = "authChannel")
        AuthResponse authenticate(AuthRequest request);

        @Gateway(requestChannel = "topupChannel")
        Message<String> topup(Message<String> message);
    }
    
    @Bean
    public HttpRequestExecutingMessageHandler authenticate() {
        return Http.outboundGateway(RevenumosterService.AUTH_URL)
                .httpMethod(HttpMethod.POST)
                .expectedResponseType(AuthResponse.class)
                .get();
    }
    
    @Bean
    public IntegrationFlow authenticateOutboundFlow() {
        return IntegrationFlows.from("authChannel")
            .enrichHeaders(header -> header
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + revenumosterService.getAuthToken()))
            .handle(authenticate())
            .channel("authenticateOutput")
            .get();
    }
    
    @ServiceActivator(inputChannel = "authenticateOutput")
    public AuthResponse authenticateOutput(AuthResponse response) {
        RevenumosterService.setAccessToken(response.getAccessToken());
        RevenumosterService.setRefreshToken(response.getRefreshToken());
        return response;
    }
    
    public HttpRequestExecutingMessageHandler topup() {
        return Http.outboundGateway(RevenumosterService.SERVICE_URL)
                .httpMethod(HttpMethod.POST)
                .mappedRequestHeaders("Content-Type", "Authorization", "X-Nonce-Str", "X-Signature", "X-Timestamp")
                .expectedResponseType(String.class)
                .get();
    }

    @Bean
    public IntegrationFlow topupOutboundFlow() {

        return IntegrationFlows.from("topupChannel")
                .handle("authService", "authenticateNewRequest")
                .enrichHeaders(headerEnricherSpec -> headerEnricherSpec
                     .header("Content-Type", "application/json")
                     .headerFunction("Authorization", message -> "Bearer " + RevenumosterService.getAccessToken()) 
                     .headerFunction("X-Signature", message -> "sha256 " + RevenumosterService.getRequestSignature())
                     .headerFunction("X-Nonce-Str", message -> RevenumosterService.getNonce())
                     .headerFunction("X-Timestamp", message -> RevenumosterService.getTimestamp().toString())
                )
                .handle(topup())
                .get();
    }
}
