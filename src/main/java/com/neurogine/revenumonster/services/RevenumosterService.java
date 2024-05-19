package com.neurogine.revenumonster.services;

import java.util.Base64;

public class RevenumosterService {

	public static final String AUTH_URL = "https://sb-oauth.revenuemonster.my/v1/token";
	public static final String SERVICE_URL = "https://sb-open.revenuemonster.my/v3/wallet/topup";
	public static final String CLIENT_ID = "";
	public static final String CLIENT_SECRET = "";
	
	private static String accessToken;
	private static String refreshToken;
	
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
}
