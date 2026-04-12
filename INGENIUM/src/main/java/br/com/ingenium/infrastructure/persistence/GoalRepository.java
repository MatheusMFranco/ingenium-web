package br.com.ingenium.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import br.com.ingenium.domain.model.Goal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class GoalRepository {

    private static final String OWNER_ID_PARAM = "ownerId";

    @PersistenceContext(unitName = "ingeniumPU")
    private EntityManager entityManager;

    public List<Goal> findAllByOwnerId(Long ownerId) {
        return entityManager.createQuery(
                "SELECT g FROM Goal g JOIN FETCH g.category WHERE g.category.owner.id = :ownerId ORDER BY g.startDate DESC",
                Goal.class)
            .setParameter(OWNER_ID_PARAM, ownerId)
            .getResultList();
    }

    public List<Goal> findPaginatedByOwnerId(Long ownerId, int first, int pageSize) {
        return entityManager.createQuery(
                "SELECT g FROM Goal g JOIN FETCH g.category WHERE g.category.owner.id = :ownerId ORDER BY g.startDate DESC",
                Goal.class)
            .setParameter(OWNER_ID_PARAM, ownerId)
            .setFirstResult(first)
            .setMaxResults(pageSize)
            .getResultList();
    }

    public long countByOwnerId(Long ownerId) {
        return entityManager.createQuery(
                "SELECT COUNT(g.id) FROM Goal g WHERE g.category.owner.id = :ownerId",
                Long.class)
            .setParameter(OWNER_ID_PARAM, ownerId)
            .getSingleResult();
    }

    public Optional<Goal> findByIdAndOwnerId(Long goalId, Long ownerId) {
        TypedQuery<Goal> query = entityManager.createQuery(
                "SELECT g FROM Goal g JOIN FETCH g.category WHERE g.id = :goalId AND g.category.owner.id = :ownerId",
                Goal.class);
        query.setParameter("goalId", goalId);
        query.setParameter(OWNER_ID_PARAM, ownerId);
        return query.getResultStream().findFirst();
    }

    public Goal save(Goal goal) {
        entityManager.persist(goal);
        return goal;
    }

    public Goal update(Goal goal) {
        return entityManager.merge(goal);
    }

    public void delete(Goal goal) {
        Goal managedGoal = entityManager.contains(goal) ? goal : entityManager.merge(goal);
        entityManager.remove(managedGoal);
    }
}
