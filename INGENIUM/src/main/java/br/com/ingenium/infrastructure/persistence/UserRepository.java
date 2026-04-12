package br.com.ingenium.infrastructure.persistence;

import java.util.Optional;

import br.com.ingenium.domain.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class UserRepository {

    @PersistenceContext(unitName = "ingeniumPU")
    private EntityManager entityManager;

    public Optional<User> findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username = :username",
                User.class);
        query.setParameter("username", username);
        return query.getResultStream().findFirst();
    }

    public boolean existsByUsername(String username) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(u.id) FROM User u WHERE u.username = :username",
                Long.class)
            .setParameter("username", username)
            .getSingleResult();
        return count > 0;
    }

    public boolean existsByEmail(String email) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(u.id) FROM User u WHERE u.email = :email",
                Long.class)
            .setParameter("email", email)
            .getSingleResult();
        return count > 0;
    }

    public boolean existsByEmailForAnotherUser(Long userId, String email) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(u.id) FROM User u WHERE u.email = :email AND u.id <> :userId",
                Long.class)
            .setParameter("email", email)
            .setParameter("userId", userId)
            .getSingleResult();
        return count > 0;
    }

    public Optional<User> findById(Long userId) {
        return Optional.ofNullable(entityManager.find(User.class, userId));
    }

    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    public User update(User user) {
        return entityManager.merge(user);
    }

    public void delete(User user) {
        User managedUser = entityManager.contains(user) ? user : entityManager.merge(user);
        entityManager.remove(managedUser);
    }
}
