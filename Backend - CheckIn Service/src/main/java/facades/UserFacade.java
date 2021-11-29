package facades;

import entities.Role;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import errorhandling.AuthenticationException;
import errorhandling.ExceptionDTO;
import javax.persistence.TypedQuery;
import utils.EMF_Creator;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.userName=:userName", User.class);
            try {
                user = query.setParameter("userName", username).getSingleResult();
            } catch (Exception e) {
                throw new AuthenticationException("Invalid user name or password");
            }

            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public User createUser(String username, String password, String role) throws ExceptionDTO {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Role userRole = new Role(role);
        TypedQuery<Role> query1 = em.createQuery("SELECT r FROM Role r WHERE r.roleName=:role", Role.class);
        if (query1.setParameter("role", role).getResultList().isEmpty()) {
            em.persist(userRole);
        }

        User user = new User(username, password);
        user.addRole(userRole);

        TypedQuery<User> query2 = em.createQuery("SELECT u FROM User u WHERE u.userName=:userName", User.class);
        if (query2.setParameter("userName", username).getResultList().isEmpty()) {
            em.persist(user);
        } else {
            throw new ExceptionDTO(1061, "Username already exists in database");
        }

        em.getTransaction().commit();

        return user;
    }

}
