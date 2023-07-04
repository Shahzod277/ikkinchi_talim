package uz.raqamli_markaz.ikkinchi_talim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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

	public static void main(String[] args) {
		SpringApplication.run(IkkinchiTalimApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		utils.saveEduForm();
//		generateKeys();
	}

	public void generateKeys() throws NoSuchAlgorithmException {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(1024);
		KeyPair pair = generator.generateKeyPair();
		PublicKey publicKey = pair.getPublic();
		PrivateKey privateKey = pair.getPrivate();
		String s = Base64.getEncoder().encodeToString(privateKey.getEncoded());
		String p = Base64.getEncoder().encodeToString(publicKey.getEncoded());
		System.out.println(s);
		System.out.println(p);

		try {
			FileOutputStream publicFos = new FileOutputStream("public.key");
			publicFos.write(publicKey.getEncoded());
			publicFos.close();

			FileOutputStream privateFos = new FileOutputStream("private.key");
			privateFos.write(privateKey.getEncoded());
			publicFos.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	String secretMessage = "My secret message";
	byte[] encryptedMessageBytes = null;

	public void encode() throws InvalidKeySpecException, IOException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		File publicKeyFile = new File("public.key");
		byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
		System.out.println(Arrays.toString(Base64.getDecoder().decode(publicKeyBytes)));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
		String s = Base64.getEncoder().encodeToString(publicKey.getEncoded());
		System.out.println(s);

		Cipher encryptCipher = Cipher.getInstance("RSA");
		encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
		System.out.println(secretMessageBytes.toString());
		encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
		String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);
		System.out.println(encodedMessage);
	}

	public void decode() throws InvalidKeySpecException, IOException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		File privateKeyFile = new File("private.key");
		byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

		Cipher decryptCipher = Cipher.getInstance("RSA");
		decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);

		byte[] decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes);
		String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
		System.out.println(decryptedMessage);
	}
}
