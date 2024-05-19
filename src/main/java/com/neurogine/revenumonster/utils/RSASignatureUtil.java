package com.neurogine.revenumonster.utils;

import java.io.FileReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.util.Base64;

import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.util.io.pem.PemReader;

public class RSASignatureUtil {

public static final String PRIVE_KEY_PATH = "";
	
	private static String sign(String data, PrivateKey privateKey) throws Exception {
        Signature rsaSignature = Signature.getInstance("SHA256withRSA");
        rsaSignature.initSign(privateKey);
        rsaSignature.update(data.getBytes());
        byte[] signature = rsaSignature.sign();
        return Base64.getEncoder().encodeToString(signature);
    }

	public static PrivateKey loadPKCS1PrivateKey(String filePath) throws Exception {
        PemReader pemReader = new PemReader(new FileReader(filePath));
        byte[] pemContent = pemReader.readPemObject().getContent();
        pemReader.close();

        ASN1Primitive asn1Object = ASN1Primitive.fromByteArray(pemContent);
        RSAPrivateKey rsaPrivateKey = RSAPrivateKey.getInstance(asn1Object);

        RSAPrivateCrtKeySpec keySpec = new RSAPrivateCrtKeySpec(
                rsaPrivateKey.getModulus(),
                rsaPrivateKey.getPublicExponent(),
                rsaPrivateKey.getPrivateExponent(),
                rsaPrivateKey.getPrime1(),
                rsaPrivateKey.getPrime2(),
                rsaPrivateKey.getExponent1(),
                rsaPrivateKey.getExponent2(),
                rsaPrivateKey.getCoefficient());

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }
	
	public static String SignMessage(String message) throws Exception {
            PrivateKey privateKey = loadPKCS1PrivateKey(PRIVE_KEY_PATH);
            String signature = RSASignatureUtil.sign(message, privateKey);
            return signature;
    }
}
