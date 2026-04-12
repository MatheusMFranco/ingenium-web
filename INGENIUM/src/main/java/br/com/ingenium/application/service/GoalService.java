package br.com.ingenium.application.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import br.com.ingenium.application.exception.BusinessException;
import br.com.ingenium.domain.model.Category;
import br.com.ingenium.domain.model.Goal;
import br.com.ingenium.infrastructure.persistence.CategoryRepository;
import br.com.ingenium.infrastructure.persistence.GoalRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class GoalService {

    @Inject
    private GoalRepository goalRepository;

    @Inject
    private CategoryRepository categoryRepository;

    public List<Goal> listByOwner(Long ownerId) {
        if (ownerId == null) {
            return Collections.emptyList();
        }
        return goalRepository.findAllByOwnerId(ownerId);
    }

    public List<Goal> listByOwnerPaginated(Long ownerId, int first, int pageSize) {
        if (ownerId == null) {
            return Collections.emptyList();
        }
        return goalRepository.findPaginatedByOwnerId(ownerId, first, pageSize);
    }

    public long countByOwner(Long ownerId) {
        if (ownerId == null) {
            return 0;
        }
        return goalRepository.countByOwnerId(ownerId);
    }

    public Optional<Goal> findById(Long ownerId, Long goalId) {
        if (ownerId == null || goalId == null) {
            return Optional.empty();
        }
        return goalRepository.findByIdAndOwnerId(goalId, ownerId);
    }

    @Transactional
    public Goal create(Long ownerId, Goal goal, Long categoryId) {
        validateGoal(goal);
        if (categoryId == null) {
            throw new BusinessException("Categoria é obrigatória.");
        }
        Category category = categoryRepository.findByIdAndOwnerId(categoryId, ownerId)
            .orElseThrow(() -> new BusinessException("Categoria selecionada não encontrada."));

        goal.setCategory(category);
        return goalRepository.save(goal);
    }

    @Transactional
    public Goal update(Long ownerId, Long goalId, Goal updates, Long categoryId) {
        validateGoal(updates);
        if (categoryId == null) {
            throw new BusinessException("Categoria é obrigatória.");
        }
        Goal managedGoal = goalRepository.findByIdAndOwnerId(goalId, ownerId)
            .orElseThrow(() -> new BusinessException("Objetivo não encontrado."));

        Category category = categoryRepository.findByIdAndOwnerId(categoryId, ownerId)
            .orElseThrow(() -> new BusinessException("Categoria selecionada não encontrada."));

        managedGoal.setName(updates.getName().trim());
        managedGoal.setDescription(updates.getDescription() == null ? null : updates.getDescription().trim());
        managedGoal.setDurationType(updates.getDurationType());
        managedGoal.setStartDate(updates.getStartDate());
        managedGoal.setEndDate(updates.getEndDate());
        managedGoal.setCategory(category);

        return goalRepository.update(managedGoal);
    }

    @Transactional
    public void delete(Long ownerId, Long goalId) {
        Goal managedGoal = goalRepository.findByIdAndOwnerId(goalId, ownerId)
            .orElseThrow(() -> new BusinessException("Objetivo não encontrado."));
        goalRepository.delete(managedGoal);
    }

    private void validateGoal(Goal goal) {
        if (goal == null || goal.getName() == null || goal.getName().trim().isBlank()) {
            throw new BusinessException("Nome do objetivo é obrigatório.");
        }
        if (goal.getDurationType() == null) {
            throw new BusinessException("Tipo de duração é obrigatório.");
        }
        if (goal.getStartDate() == null || goal.getEndDate() == null) {
            throw new BusinessException("Datas inicial e final são obrigatórias.");
        }

        LocalDate startDate = goal.getStartDate();
        LocalDate endDate = goal.getEndDate();
        if (startDate.isAfter(endDate)) {
            throw new BusinessException("Data inicial não pode ser maior que a data final.");
        }

        goal.setName(goal.getName().trim());
    }
}
