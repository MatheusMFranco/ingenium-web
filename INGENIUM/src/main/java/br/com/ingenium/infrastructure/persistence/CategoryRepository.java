package br.com.ingenium.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import br.com.ingenium.domain.model.Category;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class CategoryRepository {

    @PersistenceContext(unitName = "ingeniumPU")
    private EntityManager entityManager;

    public List<Category> findAllByOwnerId(Long ownerId) {
        return entityManager.createQuery(
                "SELECT c FROM Category c WHERE c.owner.id = :ownerId ORDER BY c.name",
                Category.class)
            .setParameter("ownerId", ownerId)
            .getResultList();
    }

    public Optional<Category> findByIdAndOwnerId(Long categoryId, Long ownerId) {
        TypedQuery<Category> query = entityManager.createQuery(
                "SELECT c FROM Category c WHERE c.id = :categoryId AND c.owner.id = :ownerId",
                Category.class);
        query.setParameter("categoryId", categoryId);
        query.setParameter("ownerId", ownerId);
        return query.getResultStream().findFirst();
    }

    public Category save(Category category) {
        entityManager.persist(category);
        return category;
    }

    public Category update(Category category) {
        return entityManager.merge(category);
    }

    public void delete(Category category) {
        Category managedCategory = entityManager.contains(category)
                ? category
                : entityManager.merge(category);
        entityManager.remove(managedCategory);
    }
}
