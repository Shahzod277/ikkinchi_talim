package uz.raqamli_markaz.ikkinchi_talim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import uz.raqamli_markaz.ikkinchi_talim.api.iib_api.IIBServiceApi;
import uz.raqamli_markaz.ikkinchi_talim.api.my_edu.MyEduApiService;
import uz.raqamli_markaz.ikkinchi_talim.api.my_edu.user_response.Passport;
import uz.raqamli_markaz.ikkinchi_talim.api.my_edu.user_response.UserResponseMyEdu;
import uz.raqamli_markaz.ikkinchi_talim.domain.User;
import uz.raqamli_markaz.ikkinchi_talim.model.request.PinflRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.response.PinflResponse1;
import uz.raqamli_markaz.ikkinchi_talim.model.response.ResponseMessage;
import uz.raqamli_markaz.ikkinchi_talim.model.response.Result;
import uz.raqamli_markaz.ikkinchi_talim.repository.UserRepository;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final MyEduApiService myEduApiService;
    private final IIBServiceApi iibServiceApi;
    String myEduPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCe6PeC7/ufFdTfIr00axHB+vGAJxlH8X0HG6pMixXQgDL95AcBZO/e6wFkfZ/oh0J9WD7am1v1ASUtocx3XeGLelxZfAaiOInQ+Qn/EcjSlKqO+uckxcFKac6iBcoahrymFWQVvcbN6p5xdcOBdj6nO1onRvsWkk2sxcRAlzrUHwIDAQAB";
    String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAISfguHXlZp2BzVrUaT+8blLPepjhxjozOCk8BJu6TteMBynTOT7EAC5H00h5MZwT7TgRkImwbk2FcFeAeGQjvSDsVTUbZlakC/wB1AA4UqcUm3OJlC77o6//pTb9wBH+lKlsAvY+PrAiAKwAMTDHFs6Q/ACY74ZbIFLY3O4MFNfAgMBAAECgYAQ8aQyIG3/pvayz3xF3UCa0M8fRAn9l7idNtVpNXxc1mLFNmavlpfrz7r9CsiExdKZJFI1n2f+trc+1jjdTa/FxHshzVB2q29GfeHR/Iu8tjw6ypWcXT4kJdj6wNRYpgMsYRV/f6Sk7Ngp+M9+NeXOj+xX1+EH7UiyDpNSXRwAmQJBALrnl0q/Z2wJiK4XN+8aHTEgroJmhb+NpC0mVDu5DldJWswdK4s/Kvh4C4SHhduEDeC541hjA3CXaUo1AFs9hBsCQQC1psj9VeV/qicN1FYf2coVSChTKp8lhC/yifakPoLp0UX9iHaKzypzTqYWTUdsSZ63d4dKnUa9+Iy6yZZbj7oNAkBEmAIaWKyoJceXvMW2ZqsYAJqLGP01E9KRD2QSlxQATNeZ2YrFi+VFUylG9kXWDlzZgN9C7POyOp9VsKX01lrJAkEAgoNN328K0HoBS1dndcT2A+pvRqnV5I+gH4PumL1tM++veOTGPx9voZ89h8KIcY5HogwYQYzU2gMtobra8/hFNQJAa+NCJazCnUhwL7Nt+jS/wzHToLTWV1ZtLUo3iiMA6R5IegdnxfW6R8LAHNcx/ZhXEbv9ZCV08kU/dtMgSfzqoA==";
    String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEn4Lh15Wadgc1a1Gk/vG5Sz3qY4cY6MzgpPASbuk7XjAcp0zk+xAAuR9NIeTGcE+04EZCJsG5NhXBXgHhkI70g7FU1G2ZWpAv8AdQAOFKnFJtziZQu+6Ov/6U2/cAR/pSpbAL2Pj6wIgCsADEwxxbOkPwAmO+GWyBS2NzuDBTXwIDAQAB";


    @Transactional
    public Result checkUser(String token) {
        try {
            String decode = decode(token);
            if (decode != null) {
                String pinfl = decode.substring(0, decode.indexOf("|"));
                String expireteTime = decode.substring(decode.indexOf("|") + 1);
                long now = System.currentTimeMillis();
                long aLong = Long.parseLong(expireteTime);
                if (now > aLong * 1000) {
                    return new Result("Expirete Token", false);
                }
                Optional<User> user = userRepository.findUserByPinfl(pinfl);
                if (user.isEmpty()) {
                    String encode = encode(pinfl);
                    UserResponseMyEdu userMyEdu = myEduApiService.getUserByToken(encode);
                    User userNew = new User();
                    userNew.setCitizenship(userMyEdu.getCitizenship().getNameUz());
                    userNew.setGender(userMyEdu.getGender());
                    userNew.setFullName(userMyEdu.getFirstName() + " " + userMyEdu.getLastName() + " " + userMyEdu.getMiddleName());
                    userNew.setDateOfBirth(userMyEdu.getBirthDate());
                    userNew.setPermanentAddress(userMyEdu.getAddress());
                    userNew.setMyEduId(userMyEdu.getId());
                    userNew.setPhoneNumber(userMyEdu.getPhoneNumber());
                    userNew.setFotoUrl(userMyEdu.getPhoto().getFile());
                    userNew.setPinfl(userMyEdu.getPinfl());
                    userRepository.save(userNew);
                    return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true, userNew.getId());
                }
                return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true, user.get().getId());

            }
            return new Result("token is null", false);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR.getMessage(), false);
        }
    }

    public String decode(String token) throws Exception {
        byte[] decode = Base64.getDecoder().decode(token); //Test string text

        byte[] decodePrivateKey = Base64.getDecoder().decode(privateKey);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(decodePrivateKey);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] decryptedMessageBytes = decryptCipher.doFinal(decode);
        String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
        System.out.println(decryptedMessage);
        return decryptedMessage;
    }

    public String encode(String pinfl) throws Exception {
        byte[] decodePublicKey = Base64.getDecoder().decode(myEduPublicKey);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(decodePublicKey);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] secretMessageBytes = pinfl.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
        String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);
        System.out.println(encodedMessage);
        return encodedMessage;
    }


    public void test() {
        List<String> all = userRepository.findAllByRoleIsNull();
        PinflRequest request = new PinflRequest();
        request.setPinfls(all);
        List<PinflResponse1> response = iibServiceApi.getPasportSerialAndNumber(request);

        response.forEach(pinflResponse1 -> {
            Thread thread = new Thread(() -> {
                User user = userRepository.findUserByPinfl(pinflResponse1.getPinfl()).get();
                if (pinflResponse1.getPassportSerial().isEmpty()) {
                    user.setPassportSerial(pinflResponse1.getPassportSerial());
                    user.setPassportSerial(pinflResponse1.getPassportNumber());
                    user.setModifiedDate(LocalDateTime.now());
                    userRepository.save(user);
                    System.out.println(pinflResponse1.getPassportNumber()+pinflResponse1.getPassportSerial());
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
