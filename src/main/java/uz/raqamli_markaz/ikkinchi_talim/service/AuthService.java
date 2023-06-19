package uz.raqamli_markaz.ikkinchi_talim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uz.raqamli_markaz.ikkinchi_talim.api.iib_api.IIBServiceApi;
import uz.raqamli_markaz.ikkinchi_talim.api.one_id.OneIdServiceApi;
import uz.raqamli_markaz.ikkinchi_talim.api.sms_api.SmsServiceApi;
import uz.raqamli_markaz.ikkinchi_talim.model.request.LoginRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.response.JwtResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.Result;
import uz.raqamli_markaz.ikkinchi_talim.repository.AdminEntityRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.RoleRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.UserRepository;
import uz.raqamli_markaz.ikkinchi_talim.security.JwtTokenProvider;
import uz.raqamli_markaz.ikkinchi_talim.security.UserDetailsImpl;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("signIn")
    public ResponseEntity<?> signIn(@RequestBody LoginRequest loginRequest){
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getPhoneNumber(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();
            String jwtToken = jwtTokenProvider.generateJWTToken(userDetails);
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            return ResponseEntity.ok(new JwtResponse(
                    userDetails.getId(),
                    userDetails.getUsername(),
                    jwtToken,
                    roles));
        } catch (Exception ex) {
            return ResponseEntity.status(400).body(new Result("Telefon raqam yoki parol hato kiritilgan, " +
                    "iltimos tekshirib qayta urinib ko'ring", false));
        }
    }

}
