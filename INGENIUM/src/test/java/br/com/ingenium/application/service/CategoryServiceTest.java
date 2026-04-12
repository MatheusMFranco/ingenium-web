package br.com.ingenium.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import br.com.ingenium.application.exception.BusinessException;
import br.com.ingenium.domain.model.Category;
import br.com.ingenium.domain.model.User;
import br.com.ingenium.infrastructure.persistence.CategoryRepository;
import br.com.ingenium.infrastructure.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private User owner;
    private Long userId = 1L;
    private Long categoryId = 10L;

    @BeforeEach
    void setUp() {
        owner = new User();
        owner.setId(userId);

        category = new Category();
        category.setId(categoryId);
        category.setName("Estudos");
    }

    @Test
    void shouldCreateCategorySuccessfully() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(owner));
        when(categoryRepository.save(any(Category.class))).thenAnswer(i -> i.getArguments()[0]);

        Category created = categoryService.create(userId, category);

        assertEquals("Estudos", created.getName());
        assertEquals(owner, created.getOwner());
        verify(categoryRepository).save(category);
    }

    @Test
    void shouldUpdateCategorySuccessfully() {
        when(categoryRepository.findByIdAndOwnerId(categoryId, userId)).thenReturn(Optional.of(category));
        when(categoryRepository.update(any(Category.class))).thenAnswer(i -> i.getArguments()[0]);

        Category updates = new Category();
        updates.setName("Estudos Avançados");
        updates.setDescription("Nova Descrição");

        Category updated = categoryService.update(userId, categoryId, updates);

        assertEquals("Estudos Avançados", updated.getName());
        assertEquals("Nova Descrição", updated.getDescription());
        verify(categoryRepository).update(category);
    }

    @Test
    void shouldDeleteCategorySuccessfully() {
        when(categoryRepository.findByIdAndOwnerId(categoryId, userId)).thenReturn(Optional.of(category));
        categoryService.delete(userId, categoryId);
        verify(categoryRepository).delete(category);
    }

    @Test
    void shouldListByOwner() {
        when(categoryRepository.findAllByOwnerId(userId)).thenReturn(List.of(category));
        List<Category> list = categoryService.listByOwner(userId);
        assertEquals(1, list.size());
        assertTrue(categoryService.listByOwner(null).isEmpty());
    }

    @Test
    void shouldFindById() {
        when(categoryRepository.findByIdAndOwnerId(categoryId, userId)).thenReturn(Optional.of(category));
        Optional<Category> found = categoryService.findById(userId, categoryId);
        assertTrue(found.isPresent());
        assertTrue(categoryService.findById(null, categoryId).isEmpty());
        assertTrue(categoryService.findById(userId, null).isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenCreatingNullCategory() {
        assertThrows(BusinessException.class, () -> categoryService.create(userId, null));
    }

    @Test
    void shouldThrowExceptionWhenCreatingCategoryWithNullUser() {
        assertThrows(BusinessException.class, () -> categoryService.create(null, category));
    }

    @Test
    void shouldThrowExceptionWhenOwnerNotFoundOnCreate() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> categoryService.create(userId, category));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentCategory() {
        when(categoryRepository.findByIdAndOwnerId(categoryId, userId)).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> categoryService.update(userId, categoryId, category));
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentCategory() {
        when(categoryRepository.findByIdAndOwnerId(categoryId, userId)).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> categoryService.delete(userId, categoryId));
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        category.setName("");
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            categoryService.create(userId, category);
        });
        assertEquals("Nome da categoria é obrigatório.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        category.setName(null);
        assertThrows(BusinessException.class, () -> categoryService.create(userId, category));
    }
}
