package br.com.ingenium.application.service;

import java.util.Optional;

import br.com.ingenium.domain.model.User;
import br.com.ingenium.infrastructure.persistence.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthenticationService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private PasswordHasher passwordHasher;

    public Optional<User> authenticate(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return Optional.empty();
        }
        
        String normalizedUsername = username.trim().toLowerCase();
        Optional<User> maybeUser = userRepository.findByUsername(normalizedUsername);
        
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            if (passwordHasher.verify(password, user.getPassword())) {
                return Optional.of(user);
            }
        }
        
        return Optional.empty();
    }
}
