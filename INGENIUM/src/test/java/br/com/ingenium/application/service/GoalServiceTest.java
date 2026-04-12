package br.com.ingenium.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import br.com.ingenium.application.exception.BusinessException;
import br.com.ingenium.domain.model.Category;
import br.com.ingenium.domain.model.DurationType;
import br.com.ingenium.domain.model.Goal;
import br.com.ingenium.infrastructure.persistence.CategoryRepository;
import br.com.ingenium.infrastructure.persistence.GoalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GoalServiceTest {

    @Mock
    private GoalRepository goalRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private GoalService goalService;

    private Goal goal;
    private Category category;
    private Long userId = 1L;
    private Long categoryId = 10L;
    private Long goalId = 100L;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(categoryId);
        category.setName("Saúde");

        goal = new Goal();
        goal.setId(goalId);
        goal.setName("Correr 5km");
        goal.setDurationType(DurationType.MONTHLY);
        goal.setStartDate(LocalDate.now());
        goal.setEndDate(LocalDate.now().plusMonths(3));
    }

    @Test
    void shouldCreateGoalSuccessfully() {
        when(categoryRepository.findByIdAndOwnerId(categoryId, userId)).thenReturn(Optional.of(category));
        when(goalRepository.save(any(Goal.class))).thenAnswer(i -> i.getArguments()[0]);

        Goal created = goalService.create(userId, goal, categoryId);

        assertNotNull(created);
        assertEquals("Correr 5km", created.getName());
        assertEquals(category, created.getCategory());
        verify(goalRepository).save(goal);
    }

    @Test
    void shouldUpdateGoalSuccessfully() {
        when(categoryRepository.findByIdAndOwnerId(categoryId, userId)).thenReturn(Optional.of(category));
        when(goalRepository.findByIdAndOwnerId(goalId, userId)).thenReturn(Optional.of(goal));
        when(goalRepository.update(any(Goal.class))).thenAnswer(i -> i.getArguments()[0]);

        Goal updates = new Goal();
        updates.setName("Correr 10km");
        updates.setDurationType(DurationType.YEARLY);
        updates.setStartDate(LocalDate.now());
        updates.setEndDate(LocalDate.now().plusYears(1));
        updates.setDescription("Nova Descrição");

        Goal updated = goalService.update(userId, goalId, updates, categoryId);

        assertEquals("Correr 10km", updated.getName());
        assertEquals(DurationType.YEARLY, updated.getDurationType());
        verify(goalRepository).update(any(Goal.class));
    }

    @Test
    void shouldDeleteGoalSuccessfully() {
        when(goalRepository.findByIdAndOwnerId(goalId, userId)).thenReturn(Optional.of(goal));
        goalService.delete(userId, goalId);
        verify(goalRepository).delete(goal);
    }

    @Test
    void shouldFindById() {
        when(goalRepository.findByIdAndOwnerId(goalId, userId)).thenReturn(Optional.of(goal));
        Optional<Goal> found = goalService.findById(userId, goalId);
        assertTrue(found.isPresent());
        assertEquals(goalId, found.get().getId());
    }

    @Test
    void shouldReturnEmptyWhenIdsAreNullInFindById() {
        assertTrue(goalService.findById(null, goalId).isEmpty());
        assertTrue(goalService.findById(userId, null).isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenGoalIsNullOnCreate() {
        assertThrows(BusinessException.class, () -> goalService.create(userId, null, categoryId));
    }

    @Test
    void shouldThrowExceptionWhenGoalNameIsEmpty() {
        goal.setName("  ");
        assertThrows(BusinessException.class, () -> goalService.create(userId, goal, categoryId));
    }

    @Test
    void shouldThrowExceptionWhenGoalNameIsNull() {
        goal.setName(null);
        assertThrows(BusinessException.class, () -> goalService.create(userId, goal, categoryId));
    }

    @Test
    void shouldThrowExceptionWhenDurationTypeIsNull() {
        goal.setDurationType(null);
        assertThrows(BusinessException.class, () -> goalService.create(userId, goal, categoryId));
    }

    @Test
    void shouldThrowExceptionWhenStartDateIsNull() {
        goal.setStartDate(null);
        assertThrows(BusinessException.class, () -> goalService.create(userId, goal, categoryId));
    }

    @Test
    void shouldThrowExceptionWhenEndDateIsNull() {
        goal.setEndDate(null);
        assertThrows(BusinessException.class, () -> goalService.create(userId, goal, categoryId));
    }

    @Test
    void shouldThrowExceptionWhenStartDateIsAfterEndDate() {
        goal.setStartDate(LocalDate.now().plusDays(1));
        goal.setEndDate(LocalDate.now());
        assertThrows(BusinessException.class, () -> goalService.create(userId, goal, categoryId));
    }

    @Test
    void shouldThrowExceptionWhenCategoryIdIsNullOnCreate() {
        assertThrows(BusinessException.class, () -> goalService.create(userId, goal, null));
    }

    @Test
    void shouldThrowExceptionWhenUpdateGoalNotFound() {
        when(goalRepository.findByIdAndOwnerId(goalId, userId)).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> goalService.update(userId, goalId, goal, categoryId));
    }

    @Test
    void shouldThrowExceptionWhenUpdateCategoryNotFound() {
        when(goalRepository.findByIdAndOwnerId(goalId, userId)).thenReturn(Optional.of(goal));
        when(categoryRepository.findByIdAndOwnerId(categoryId, userId)).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> goalService.update(userId, goalId, goal, categoryId));
    }

    @Test
    void shouldListByOwner() {
        when(goalRepository.findAllByOwnerId(userId)).thenReturn(List.of(goal));
        assertEquals(1, goalService.listByOwner(userId).size());
        assertTrue(goalService.listByOwner(null).isEmpty());
    }

    @Test
    void shouldCountByOwner() {
        when(goalRepository.countByOwnerId(userId)).thenReturn(10L);
        assertEquals(10L, goalService.countByOwner(userId));
        assertEquals(0L, goalService.countByOwner(null));
    }

    @Test
    void shouldListByOwnerPaginated() {
        when(goalRepository.findPaginatedByOwnerId(userId, 0, 5)).thenReturn(List.of(goal));
        assertEquals(1, goalService.listByOwnerPaginated(userId, 0, 5).size());
        assertTrue(goalService.listByOwnerPaginated(null, 0, 5).isEmpty());
    }
}
