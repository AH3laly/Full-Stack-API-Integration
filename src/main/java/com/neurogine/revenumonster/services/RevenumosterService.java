package com.neurogine.revenumonster.services;

import java.util.Base64;
import java.util.Random;

import com.neurogine.revenumonster.utils.RSASignatureUtil;

public class RevenumosterService {

	public static final String AUTH_URL = "https://sb-oauth.revenuemonster.my/v1/token";
	public static final String SERVICE_URL = "https://sb-open.revenuemonster.my/v3/wallet/topup";
	public static final String CLIENT_ID = "1715948204793029959";
	public static final String CLIENT_SECRET = "VpXJDNSAXhCMjvtYKigGjCDCHYXbRNUT";
	
	private static String accessToken;
	private static String refreshToken;
	private static String nonce;
	private static String requestSignature;
	private static Long timestamp;

	public static String getAuthToken() {
		String compactToken = CLIENT_ID + " : " + CLIENT_SECRET;
		return Base64.getEncoder().encode(compactToken.getBytes()).toString();
	}
	
	public static String getAccessToken() {
		return accessToken;
	}

	public static void setAccessToken(String accessToken) {
		RevenumosterService.accessToken = accessToken;
	}

	public static String getRefreshToken() {
		return refreshToken;
	}
	public static void setRefreshToken(String refreshToken) {
		RevenumosterService.refreshToken = refreshToken;
	}
	
	public static long generateTimestamp() {
		timestamp = System.currentTimeMillis() / 1000L;
		return timestamp;
	}
	
	public static Long getTimestamp() {
		return timestamp;
	}
	
	public static String generateNewNonce() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 32) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        nonce = salt.toString();
        return nonce;
	}
	
	public static String getNonce() {
        return nonce;
	}
	
	private static String preparePayloadForSignature(String request) {
		return Base64.getEncoder().encodeToString(request.getBytes());
	}
	
	public static String generateRequestSignature(String messagePaload) throws Exception {
		
		StringBuilder messageBytes = new StringBuilder();
		messageBytes.append("data=").append(preparePayloadForSignature(messagePaload))
			.append("&method=post")
			.append("&nonceStr=").append(nonce)
			.append("&requestUrl=").append(RevenumosterService.SERVICE_URL)
			.append("&signType=sha256")
			.append("&timestamp=").append(getTimestamp());
		
		requestSignature = RSASignatureUtil.SignMessage(messageBytes.toString());

		return requestSignature;
	}

	public static String getRequestSignature() {
		return requestSignature;
	}
}
