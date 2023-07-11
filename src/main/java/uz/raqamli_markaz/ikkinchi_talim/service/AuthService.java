package uz.raqamli_markaz.ikkinchi_talim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import uz.raqamli_markaz.ikkinchi_talim.api.one_id.OneIdResponseToken;
import uz.raqamli_markaz.ikkinchi_talim.api.one_id.OneIdResponseUserInfo;
import uz.raqamli_markaz.ikkinchi_talim.api.one_id.OneIdServiceApi;
import uz.raqamli_markaz.ikkinchi_talim.domain.User;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Role;
import uz.raqamli_markaz.ikkinchi_talim.model.request.DefaultRole;
import uz.raqamli_markaz.ikkinchi_talim.model.response.JwtResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.ResponseMessage;
import uz.raqamli_markaz.ikkinchi_talim.model.response.Result;
import uz.raqamli_markaz.ikkinchi_talim.repository.RoleRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.UserRepository;
import uz.raqamli_markaz.ikkinchi_talim.security.JwtTokenProvider;
import uz.raqamli_markaz.ikkinchi_talim.security.UserDetailsImpl;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final OneIdServiceApi oneIdServiceApi;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public URI redirectOneIdUrl() {
        return oneIdServiceApi.redirectOneIdUrl();
    }

    @Transactional
    public Result oneIdSignIn(String code) {

        try {
            OneIdResponseToken oneIdToken = oneIdServiceApi.getAccessAndRefreshToken(code);
            if (oneIdToken == null) {
                return new Result("Token" + ResponseMessage.NOT_FOUND.getMessage(), false);
            }
            OneIdResponseUserInfo oneIdUserInfo = oneIdServiceApi.getUserInfo(oneIdToken.getAccess_token());
            Optional<User> user = userRepository.findUserByPinfl(oneIdUserInfo.getPin());
            if (user.isPresent()) {
                if (user.get().getPassword() == null) {
                    user.get().setPassword(passwordEncoder.encode(user.get().getPinfl()));
                    user.get().setModifiedDate(LocalDateTime.now());
                    userRepository.save(user.get());
                }
                JwtResponse jwtResponse = getJwtResponse(user.get().getPinfl(), user.get().getPinfl());
                return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true, jwtResponse);
            }
            return new Result("Sizga bu tizimga kirishga ruxsat yo'q", false);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.NOT_FOUND.getMessage(), false);
        }
    }

    private JwtResponse getJwtResponse(String username, String password) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();
        String jwtToken = jwtTokenProvider.generateJWTToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return new JwtResponse(
                userDetails.getId(),
                userDetails.getUsername(),
                jwtToken,
                roles);
    }

}
