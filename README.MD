# Notes

## Before use:

1) Make sure to set the variables **revenumonster.clientId**, **revenumonster.clientSecret**, and **revenumonster.rsaPrivateKeyFilePath** in the **application.properties** file.

```
revenumonster.clientId=[Set Your Client ID]
revenumonster.clientSecret=[SET Your Client Secret]
revenumonster.rsaPrivateKeyFilePath=/path/private_key_rsa.pem
```

2) Make sure the private key format is **RSA PKCS1**, otherwise the signature process would fail.

3) Upload your public key to Client portal, so the API can verify your signed request.

4) Service SandBox URLs are statically defined, should be moved toproperties file later.

```java
public static final String AUTH_URL = "https://sb-oauth.revenuemonster.my/v1/token";
public static final String SERVICE_URL = "https://sb-open.revenuemonster.my/v3/wallet/topup";
```

5) The authentication endpoint is called once in the first request, any subsequent requests would be performed without re-authentication.

6) Methods **RevenumosterService.generateNewNonce()**, **RevenumosterService.generateTimestamp()**, and **RevenumosterService.generateRequestSignature()** are only called once throughout the request to generate the unique values.
To get the generated values **RevenumosterService.getNonce()**, **RevenumosterService.getNonce()**, and **RevenumosterService.getRequestSignature()** could be used.

7) To test the App, Start the project and hit _http://localhost:8080/api/topup?amount=50_

Note: The _amount_ parameter is required.
