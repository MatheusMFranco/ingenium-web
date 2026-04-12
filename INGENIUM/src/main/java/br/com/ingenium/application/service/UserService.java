package br.com.ingenium.application.service;

import java.util.Optional;

import br.com.ingenium.application.exception.BusinessException;
import br.com.ingenium.domain.model.User;
import br.com.ingenium.infrastructure.persistence.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private PasswordHasher passwordHasher;

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public User register(User user) {
        validateForRegistration(user);
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new BusinessException("Nome de usuário já está em uso.");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BusinessException("E-mail já está em uso.");
        }
        
        user.setPassword(passwordHasher.hash(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public User updateProfile(Long userId, User updates) {
        User managedUser = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException("Usuário não encontrado."));

        if (updates.getFullName() == null || updates.getFullName().isBlank()) {
            throw new BusinessException("Nome é obrigatório.");
        }
        if (updates.getEmail() == null || updates.getEmail().isBlank()) {
            throw new BusinessException("E-mail é obrigatório.");
        }

        String normalizedEmail = updates.getEmail().trim().toLowerCase();
        if (userRepository.existsByEmailForAnotherUser(userId, normalizedEmail)) {
            throw new BusinessException("E-mail já está em uso.");
        }

        managedUser.setFullName(updates.getFullName().trim());
        managedUser.setDescription(updates.getDescription() == null ? null : updates.getDescription().trim());
        managedUser.setEmail(normalizedEmail);

        if (updates.getPassword() != null && !updates.getPassword().isBlank()) {
            if (updates.getPassword().length() < 6 || updates.getPassword().length() > 15) {
                throw new BusinessException("Senha deve ter entre 6 e 15 caracteres.");
            }
            managedUser.setPassword(passwordHasher.hash(updates.getPassword()));
        }

        return userRepository.update(managedUser);
    }

    @Transactional
    public void deleteAccount(Long userId) {
        User managedUser = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException("Usuário não encontrado."));
        userRepository.delete(managedUser);
    }

    private void validateForRegistration(User user) {
        if (user == null) {
            throw new BusinessException("Dados do usuário são obrigatórios.");
        }
        if (user.getUsername() == null || user.getUsername().trim().length() < 3) {
            throw new BusinessException("Usuário deve ter ao menos 3 caracteres.");
        }
        if (user.getFullName() == null || user.getFullName().trim().length() < 3) {
            throw new BusinessException("Nome deve ter ao menos 3 caracteres.");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new BusinessException("E-mail é obrigatório.");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6 || user.getPassword().length() > 15) {
            throw new BusinessException("Senha deve ter entre 6 e 15 caracteres.");
        }

        user.setUsername(user.getUsername().trim().toLowerCase());
        user.setFullName(user.getFullName().trim());
        user.setEmail(user.getEmail().trim().toLowerCase());
    }
}
