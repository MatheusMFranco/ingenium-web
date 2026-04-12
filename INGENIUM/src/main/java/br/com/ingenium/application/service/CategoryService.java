package br.com.ingenium.application.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import br.com.ingenium.application.exception.BusinessException;
import br.com.ingenium.domain.model.Category;
import br.com.ingenium.domain.model.User;
import br.com.ingenium.infrastructure.persistence.CategoryRepository;
import br.com.ingenium.infrastructure.persistence.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CategoryService {

    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    private UserRepository userRepository;

    public List<Category> listByOwner(Long ownerId) {
        if (ownerId == null) {
            return Collections.emptyList();
        }
        return categoryRepository.findAllByOwnerId(ownerId);
    }

    public Optional<Category> findById(Long ownerId, Long categoryId) {
        if (ownerId == null || categoryId == null) {
            return Optional.empty();
        }
        return categoryRepository.findByIdAndOwnerId(categoryId, ownerId);
    }

    @Transactional
    public Category create(Long ownerId, Category category) {
        if (ownerId == null) {
            throw new BusinessException("Usuário autenticado não encontrado.");
        }
        validateCategory(category);
        User owner = userRepository.findById(ownerId)
            .orElseThrow(() -> new BusinessException("Usuário autenticado não encontrado."));
        category.setOwner(owner);
        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(Long ownerId, Long categoryId, Category updates) {
        validateCategory(updates);
        Category managedCategory = categoryRepository.findByIdAndOwnerId(categoryId, ownerId)
            .orElseThrow(() -> new BusinessException("Categoria não encontrada."));

        managedCategory.setName(updates.getName().trim());
        managedCategory.setDescription(updates.getDescription() == null ? null : updates.getDescription().trim());
        return categoryRepository.update(managedCategory);
    }

    @Transactional
    public void delete(Long ownerId, Long categoryId) {
        Category managedCategory = categoryRepository.findByIdAndOwnerId(categoryId, ownerId)
            .orElseThrow(() -> new BusinessException("Categoria não encontrada."));
        categoryRepository.delete(managedCategory);
    }

    private void validateCategory(Category category) {
        if (category == null || category.getName() == null || category.getName().trim().isBlank()) {
            throw new BusinessException("Nome da categoria é obrigatório.");
        }
        category.setName(category.getName().trim());
    }
}
