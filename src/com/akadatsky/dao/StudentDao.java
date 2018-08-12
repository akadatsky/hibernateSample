package com.akadatsky.dao;

import com.akadatsky.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class StudentDao {

    private SessionFactory sessionFactory;

    public StudentDao() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void close() {
        sessionFactory.close();
    }

    public int clear() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String hql = String.format("DELETE FROM %s", Student.class.getSimpleName());
            Query query = session.createQuery(hql);
            int count = query.executeUpdate();
            transaction.commit();
            return count;
        }
    }

    public void insertStudent(Student student) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
        }
    }

    public List<Student> getAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Student> criteriaQuery = builder.createQuery(Student.class);

            Root<Student> root = criteriaQuery.from(Student.class);
            criteriaQuery.select(root);

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    public List<Student> getAllSql() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Student", Student.class).list();
        }
    }

    public Student getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Student> criteriaQuery = builder.createQuery(Student.class);

            Root<Student> root = criteriaQuery.from(Student.class);
            criteriaQuery.where(builder.equal(root.get("name"), name));

            return session.createQuery(criteriaQuery).getSingleResult();
        }
    }

    public List<Student> getByAge(int from, int to) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Student> criteriaQuery = builder.createQuery(Student.class);

            Root<Student> root = criteriaQuery.from(Student.class);
            criteriaQuery.where(builder.between(root.get("age"), from, to));
            criteriaQuery.orderBy(builder.asc(root.get("age")));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    public void update(Student student) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(student);
            transaction.commit();
        }
    }

    public void deleteByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Student> criteriaQuery = builder.createQuery(Student.class);

            Root<Student> root = criteriaQuery.from(Student.class);
            criteriaQuery.where(builder.equal(root.get("name"), name));

            Student student = session.createQuery(criteriaQuery).getSingleResult();

            session.delete(student);

            transaction.commit();
        }
    }

}

