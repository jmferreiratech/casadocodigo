package br.com.casadocodigo.loja.daos;

import br.com.casadocodigo.loja.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAO implements UserDetailsService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        String jpql = "select u from User u where u.login = :login";
        List<User> users = em.createQuery(jpql, User.class).setParameter("login", userName).getResultList();
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("O usuario " + userName + " não existe");
        }
        return users.get(0);
    }
}
