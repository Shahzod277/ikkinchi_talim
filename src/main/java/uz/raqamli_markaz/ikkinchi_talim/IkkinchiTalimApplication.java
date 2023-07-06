package uz.raqamli_markaz.ikkinchi_talim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import uz.raqamli_markaz.ikkinchi_talim.service.DiplomaService;
import uz.raqamli_markaz.ikkinchi_talim.service.Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

@SpringBootApplication
@EnableJpaRepositories
public class IkkinchiTalimApplication implements CommandLineRunner {

	@Autowired
	private Utils utils;
	@Autowired

		private DiplomaService diplomaService;

	public static void main(String[] args) {
		SpringApplication.run(IkkinchiTalimApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//utils.saveInstitution();
//utils.saveOldInstitution();
//		utils.saveSpecialities();
//		utils.saveDiplomaSerial();
		utils.saveDiplomaSerial();
	}

//	public void generateKeys() throws NoSuchAlgorithmException {
//		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
//		generator.initialize(1024);
//		KeyPair pair = generator.generateKeyPair();
//		PublicKey publicKey = pair.getPublic();
//		PrivateKey privateKey = pair.getPrivate();
//		String s = Base64.getEncoder().encodeToString(privateKey.getEncoded());
//		String p = Base64.getEncoder().encodeToString(publicKey.getEncoded());
//		System.out.println(s);
//		System.out.println(p);
//		try {
//			FileOutputStream publicFos = new FileOutputStream("public.key");
//			publicFos.write(publicKey.getEncoded());
//			publicFos.close();
//
//			FileOutputStream privateFos = new FileOutputStream("private.key");
//			privateFos.write(privateKey.getEncoded());
//			publicFos.close();
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//	}

	String secretMessage = "My secret message";
	String encodeMessage = "gTlr0/ZHN9fmQoUDAqlcwVAMdbiJiQp70rwJQV4S67raUrVTEq3geXoT3tgcaCTdmdT3g2tPZxyD69xtU9MVrqg6zWmfUMM3Ik4afFKLZ2LxtxT8yvS77FoSAbYealuw0hYGgIH5G/89vhOUkLc3fPOPDZUbFisH/t1JMpkzlOA=";
	String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEn4Lh15Wadgc1a1Gk/vG5Sz3qY4cY6MzgpPASbuk7XjAcp0zk+xAAuR9NIeTGcE+04EZCJsG5NhXBXgHhkI70g7FU1G2ZWpAv8AdQAOFKnFJtziZQu+6Ov/6U2/cAR/pSpbAL2Pj6wIgCsADEwxxbOkPwAmO+GWyBS2NzuDBTXwIDAQAB";
	String publicKeyTest = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCe6PeC7/ufFdTfIr00axHB+vGAJxlH8X0HG6pMixXQgDL95AcBZO/e6wFkfZ/oh0J9WD7am1v1ASUtocx3XeGLelxZfAaiOInQ+Qn/EcjSlKqO+uckxcFKac6iBcoahrymFWQVvcbN6p5xdcOBdj6nO1onRvsWkk2sxcRAlzrUHwIDAQAB";
	String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAISfguHXlZp2BzVrUaT+8blLPepjhxjozOCk8BJu6TteMBynTOT7EAC5H00h5MZwT7TgRkImwbk2FcFeAeGQjvSDsVTUbZlakC/wB1AA4UqcUm3OJlC77o6//pTb9wBH+lKlsAvY+PrAiAKwAMTDHFs6Q/ACY74ZbIFLY3O4MFNfAgMBAAECgYAQ8aQyIG3/pvayz3xF3UCa0M8fRAn9l7idNtVpNXxc1mLFNmavlpfrz7r9CsiExdKZJFI1n2f+trc+1jjdTa/FxHshzVB2q29GfeHR/Iu8tjw6ypWcXT4kJdj6wNRYpgMsYRV/f6Sk7Ngp+M9+NeXOj+xX1+EH7UiyDpNSXRwAmQJBALrnl0q/Z2wJiK4XN+8aHTEgroJmhb+NpC0mVDu5DldJWswdK4s/Kvh4C4SHhduEDeC541hjA3CXaUo1AFs9hBsCQQC1psj9VeV/qicN1FYf2coVSChTKp8lhC/yifakPoLp0UX9iHaKzypzTqYWTUdsSZ63d4dKnUa9+Iy6yZZbj7oNAkBEmAIaWKyoJceXvMW2ZqsYAJqLGP01E9KRD2QSlxQATNeZ2YrFi+VFUylG9kXWDlzZgN9C7POyOp9VsKX01lrJAkEAgoNN328K0HoBS1dndcT2A+pvRqnV5I+gH4PumL1tM++veOTGPx9voZ89h8KIcY5HogwYQYzU2gMtobra8/hFNQJAa+NCJazCnUhwL7Nt+jS/wzHToLTWV1ZtLUo3iiMA6R5IegdnxfW6R8LAHNcx/ZhXEbv9ZCV08kU/dtMgSfzqoA==";
	byte[] encryptedMessageBytes = null;

//	public void encode() throws InvalidKeySpecException, IOException, NoSuchAlgorithmException,
//			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
////		File publicKeyFile = new File("public.key");
////		byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
//		byte[] decodePublicKey = Base64.getDecoder().decode(publicKeyTest);
//
//		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(decodePublicKey);
//		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
//
//		Cipher encryptCipher = Cipher.getInstance("RSA");
//		encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
//		byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
//		encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
//		String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);
//		System.out.println(encodedMessage);
//	}

//	public void decode() throws InvalidKeySpecException, IOException, NoSuchAlgorithmException,
//			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
//		byte[] decode = Base64.getDecoder().decode(encodeMessage); //Test string text
//		byte[] decodePrivateKey = Base64.getDecoder().decode(privateKey);
//
///*		File privateKeyFile = new File("private.key");
//		byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());*/
//
//		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//
//		EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(decodePrivateKey);
//		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
//
//		Cipher decryptCipher = Cipher.getInstance("RSA");
//		decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
//
//		byte[] decryptedMessageBytes = decryptCipher.doFinal(decode);
//		String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
//		System.out.println(decryptedMessage);
//	}
}
