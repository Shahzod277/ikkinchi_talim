package uz.raqamli_markaz.ikkinchi_talim.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import uz.raqamli_markaz.ikkinchi_talim.api.iib_api.IIBServiceApi;
import uz.raqamli_markaz.ikkinchi_talim.api.iib_api.docrest.CheckUserRequest;
import uz.raqamli_markaz.ikkinchi_talim.api.iib_api.docrest.IIBResponseNew;
import uz.raqamli_markaz.ikkinchi_talim.api.my_edu.MyEduApiService;
import uz.raqamli_markaz.ikkinchi_talim.api.my_edu.user_response.UserResponseMyEdu;
import uz.raqamli_markaz.ikkinchi_talim.domain.User;
import uz.raqamli_markaz.ikkinchi_talim.model.request.PinflRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.response.*;
import uz.raqamli_markaz.ikkinchi_talim.repository.UserRepository;


import javax.crypto.Cipher;
import java.io.IOException;
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
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
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
        //        System.out.println(decryptedMessage);
        return new String(decryptedMessageBytes, StandardCharsets.UTF_8);
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
        //        System.out.println(encodedMessage);
        return Base64.getEncoder().encodeToString(encryptedMessageBytes);
    }

//    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void test() {
        List<User> all = userRepository.findAllByRoleIsNull();
        all.forEach(s -> {
            System.out.println(s.getPinfl());
            String pinfl = s.getPinfl();
            String day = pinfl.substring(1, 3);
            String month = pinfl.substring(3, 5);
            String year = pinfl.substring(5, 7);
            if (Integer.parseInt(year) > 20) {
                year = 19 + year;
            } else {
                year = 20 + year;
            }

            CheckUserRequest request = new CheckUserRequest();

            request.setPinfl(pinfl);
            request.setBirthDate(year + "-" + month + "-" + day);
            System.out.println(year + "-" + month + "-" + day);
            IIBResponseNew iibUser = iibServiceApi.getIibUser(request);
            if (iibUser.getData() != null) {
                String currentDocument = iibUser.getData().get(0).getCurrentDocument();
                if (currentDocument != null) {
                    s.setPassportSerial(currentDocument.substring(0, 2));
                    s.setPassportNumber(currentDocument.substring(2));
                    userRepository.save(s);
                    System.out.println(pinfl);
                }
            }


        });
        System.out.println("the end");

    }
}
