package br.com.ingenium.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import br.com.ingenium.application.exception.BusinessException;
import br.com.ingenium.domain.model.User;
import br.com.ingenium.infrastructure.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordHasher passwordHasher;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setFullName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password123");
    }

    @Test
    void shouldFindById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Optional<User> found = userService.findById(1L);
        assertTrue(found.isPresent());
        assertEquals("testuser", found.get().getUsername());
    }

    @Test
    void shouldRegisterUserWithHashedPassword() {
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordHasher.hash("password123")).thenReturn("hashed_password");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User registered = userService.register(user);

        assertEquals("hashed_password", registered.getPassword());
        assertEquals("testuser", registered.getUsername()); // Verificando normalização (lowercase)
        verify(passwordHasher).hash("password123");
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowExceptionWhenRegisteringNullUser() {
        BusinessException ex = assertThrows(BusinessException.class, () -> userService.register(null));
        assertEquals("Dados do usuário são obrigatórios.", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUsernameTooShort() {
        user.setUsername("ab");
        assertThrows(BusinessException.class, () -> userService.register(user));
    }

    @Test
    void shouldThrowExceptionWhenFullNameTooShort() {
        user.setFullName("ab");
        assertThrows(BusinessException.class, () -> userService.register(user));
    }

    @Test
    void shouldThrowExceptionWhenFullNameIsNull() {
        user.setFullName(null);
        assertThrows(BusinessException.class, () -> userService.register(user));
    }

    @Test
    void shouldThrowExceptionWhenEmailIsEmpty() {
        user.setEmail("");
        assertThrows(BusinessException.class, () -> userService.register(user));
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsTooLong() {
        user.setPassword("1234567890123456"); // 16 chars
        assertThrows(BusinessException.class, () -> userService.register(user));
    }

    @Test
    void shouldThrowExceptionWhenUsernameInUse() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);
        BusinessException exception = assertThrows(BusinessException.class, () -> userService.register(user));
        assertEquals("Nome de usuário já está em uso.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenEmailInUse() {
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);
        assertThrows(BusinessException.class, () -> userService.register(user));
    }

    @Test
    void shouldUpdateProfileSuccessfully() {
        User updates = new User();
        updates.setFullName("Updated Name");
        updates.setEmail("updated@example.com");
        updates.setPassword("newpassword");
        updates.setDescription("New Description");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmailForAnotherUser(eq(1L), any(String.class))).thenReturn(false);
        when(passwordHasher.hash("newpassword")).thenReturn("new_hashed_password");
        when(userRepository.update(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User updated = userService.updateProfile(1L, updates);

        assertEquals("Updated Name", updated.getFullName());
        assertEquals("updated@example.com", updated.getEmail());
        assertEquals("new_hashed_password", updated.getPassword());
        assertEquals("New Description", updated.getDescription());
    }

    @Test
    void shouldUpdateProfileWithoutChangingPassword() {
        User updates = new User();
        updates.setFullName("Updated Name");
        updates.setEmail("updated@example.com");
        updates.setPassword(""); // Senha vazia não deve ser alterada

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmailForAnotherUser(1L, "updated@example.com")).thenReturn(false);
        when(userRepository.update(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User updated = userService.updateProfile(1L, updates);

        assertEquals("password123", updated.getPassword()); // Mantém original
        verify(passwordHasher, never()).hash(any());
    }

    @Test
    void shouldThrowExceptionWhenUpdateNameIsNull() {
        User updates = new User();
        updates.setFullName(null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        assertThrows(BusinessException.class, () -> userService.updateProfile(1L, updates));
    }

    @Test
    void shouldThrowExceptionWhenUpdateEmailIsNull() {
        User updates = new User();
        updates.setFullName("Nome");
        updates.setEmail(null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        assertThrows(BusinessException.class, () -> userService.updateProfile(1L, updates));
    }

    @Test
    void shouldDeleteAccountSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.deleteAccount(1L);
        verify(userRepository).delete(user);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> userService.deleteAccount(99L));
    }
}
