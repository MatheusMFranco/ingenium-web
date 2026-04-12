package br.com.ingenium.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import br.com.ingenium.domain.model.User;
import br.com.ingenium.infrastructure.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordHasher passwordHasher;

    @InjectMocks
    private AuthenticationService authenticationService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setPassword("hashed_password");
    }

    @Test
    void shouldAuthenticateSuccessfully() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordHasher.verify("password123", "hashed_password")).thenReturn(true);

        Optional<User> authenticated = authenticationService.authenticate("testuser", "password123");

        assertTrue(authenticated.isPresent());
        assertEquals("testuser", authenticated.get().getUsername());
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());
        Optional<User> authenticated = authenticationService.authenticate("unknown", "pass");
        assertTrue(authenticated.isEmpty());
    }

    @Test
    void shouldReturnEmptyWhenPasswordInvalid() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordHasher.verify("wrong", "hashed_password")).thenReturn(false);

        Optional<User> authenticated = authenticationService.authenticate("testuser", "wrong");

        assertTrue(authenticated.isEmpty());
    }

    @Test
    void shouldReturnEmptyWhenUsernameIsNull() {
        assertTrue(authenticationService.authenticate(null, "pass").isEmpty());
    }

    @Test
    void shouldReturnEmptyWhenUsernameIsBlank() {
        assertTrue(authenticationService.authenticate("  ", "pass").isEmpty());
    }

    @Test
    void shouldReturnEmptyWhenPasswordIsNull() {
        assertTrue(authenticationService.authenticate("user", null).isEmpty());
    }

    @Test
    void shouldReturnEmptyWhenPasswordIsBlank() {
        assertTrue(authenticationService.authenticate("user", "").isEmpty());
    }
}
