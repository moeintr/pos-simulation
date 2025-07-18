package com.simulation.psp.usecase.auth_usecase;

import com.simulation.psp.core.entity.User;
import com.simulation.psp.core.entity.UserRole;
import com.simulation.psp.core.repository.UserRepository;
import com.simulation.psp.core.service.JwtService;
import com.simulation.psp.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUseCase implements UseCase<AuthUseCaseRequest, AuthUseCaseResponse> {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthUseCaseResponse execute(AuthUseCaseRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("کاربر یافت نشد"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UsernameNotFoundException("رمز عبور احراز هویت اشتباه است");
        }

        String token = jwtService.generateToken(user);
        return new AuthUseCaseResponse(token);
    }

    public AuthUseCaseResponse signupUser(AuthUseCaseRequest request) {
        User user = new User()
                .setUsername(request.getUsername())
                .setPassword(passwordEncoder.encode(request.getPassword()))
                .setRole(UserRole.ADMIN);
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthUseCaseResponse(token);
    }
}
