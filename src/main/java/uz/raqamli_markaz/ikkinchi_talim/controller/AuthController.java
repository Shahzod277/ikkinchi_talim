package uz.raqamli_markaz.ikkinchi_talim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.raqamli_markaz.ikkinchi_talim.model.request.LoginRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.response.JwtResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.Result;
import uz.raqamli_markaz.ikkinchi_talim.security.JwtTokenProvider;
import uz.raqamli_markaz.ikkinchi_talim.security.UserDetailsImpl;
import uz.raqamli_markaz.ikkinchi_talim.service.AuthService;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;
    @PostMapping("signIn")
    public ResponseEntity<?> signIn(@RequestBody LoginRequest loginRequest){
        try {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));
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

    @GetMapping("oneId")
    public ResponseEntity<?> getOneId() {
        URI uri = authService.redirectOneIdUrl();
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(uri)
                .build();
    }

    @PostMapping("signInOneId")
    public ResponseEntity<?> signInOneId(@RequestParam(value = "code") String code) {
        Result result = authService.oneIdSignIn(code);
        return ResponseEntity.status(result.isSuccess() ? 200 : 400).body(result);
    }

}
