package com.akadatsky.dao;

import com.akadatsky.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

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

    public void insert(Student student) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
        }
    }

    public void update(Student student) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(student);
            transaction.commit();
        }
    }

    public void delete(Student student) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(student);
            transaction.commit();
        }
    }

    public List<Student> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Student", Student.class).list();
        }
    }

    public Student getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("FROM Student WHERE name = :name ", Student.class)
                    .setParameter("name", name)
                    .getSingleResult();
        }
    }

    public List<Student> getByAge(int from, int to) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("FROM Student WHERE age BETWEEN :from AND :to ORDER BY age", Student.class)
                    .setParameter("from", from)
                    .setParameter("to", to)
                    .list();
        }
    }
}

