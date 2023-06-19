package uz.raqamli_markaz.ikkinchi_talim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.raqamli_markaz.ikkinchi_talim.api.iib_api.IIBServiceApi;
import uz.raqamli_markaz.ikkinchi_talim.api.one_id.OneIdServiceApi;
import uz.raqamli_markaz.ikkinchi_talim.api.sms_api.SmsServiceApi;
import uz.raqamli_markaz.ikkinchi_talim.repository.AdminEntityRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.RoleRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.UserRepository;
import uz.raqamli_markaz.ikkinchi_talim.security.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IIBServiceApi iibServiceApi;
    private final SmsServiceApi smsServiceApi;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OneIdServiceApi oneIdServiceApi;
    private final JwtTokenProvider jwtTokenProvider;
    private final AdminEntityRepository adminEntityRepository;

}
