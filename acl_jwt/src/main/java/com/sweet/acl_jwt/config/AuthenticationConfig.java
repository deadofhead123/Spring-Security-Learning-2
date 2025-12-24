package com.sweet.acl_jwt.config;

import com.sweet.acl_jwt.component.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig {
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider); // Quyết định chọn DaoAuthenticationProvider làm Authentication Provider
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(); // Mã hóa mật khẩu bằng BCrypt

//        return new SCryptPasswordEncoder( // Mã hóa mật khẩu bằng SCrypt
//                16384, // cpu Cost
//                8,     // memory Cost
//                1,     // parallelization
//                32,    // key length
//                32     // salt length
//        );

//        return new Argon2PasswordEncoder( // Mã hóa mật khẩu bằng Argon2
//                16,     // salt length
//                32,     // hash length
//                1,      // parallelism
//                65536,  // memory (64MB)
//                3       // iterations
//        );

        String defaultPasswordEncoder = "argon2";

        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder( 16384, 8, 1, 32, 32));
        encoders.put("argon2", new Argon2PasswordEncoder( 16, 32,1, 65536,3));

        DelegatingPasswordEncoder encoder = new DelegatingPasswordEncoder(defaultPasswordEncoder, encoders);
        encoder.setDefaultPasswordEncoderForMatches(new BCryptPasswordEncoder()); // Đặt encoder mặc định khi không có prefix trong password để nhận biết encoder
        return encoder;
    }
}
