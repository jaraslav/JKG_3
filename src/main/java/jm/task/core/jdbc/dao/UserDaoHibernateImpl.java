package jm.task.core.jdbc.dao;

import jakarta.persistence.criteria.CriteriaQuery;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        request("""
                        CREATE TABLE IF NOT EXISTS `schema_digital_trash`.`users` (
                          `id` INT NOT NULL AUTO_INCREMENT,
                          `name` VARCHAR(45) NOT NULL,
                          `lastName` VARCHAR(45) NOT NULL,
                          `age` INT NOT NULL,
                          PRIMARY KEY (`id`));""");
    }

    @Override
    public void dropUsersTable() {
        request("DROP TABLE IF EXISTS users;");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        request("INSERT INTO users"
                + " (name, lastName, age) VALUES ('" + name
                + "', '" + lastName + "', " + age
                + ");");
    }

    @Override
    public void removeUserById(long id) {
        request("DELETE FROM users WHERE id=" + id);
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getHibernateSessionFactory().openSession();
        CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
        criteriaQuery.from(User.class);
        Transaction transaction = session.beginTransaction();
        List<User> userList = session.createQuery(criteriaQuery).getResultList();
        try {
            transaction.commit();
            return userList;
        } catch (HibernateException e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        request("TRUNCATE TABLE users;");
    }

    private void request(String s) {
        try (Session session = Util.getHibernateSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(s).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
