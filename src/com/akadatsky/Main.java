package com.akadatsky;

import com.akadatsky.dao.StudentDao;
import com.akadatsky.model.Student;

public class Main {

    public static void main(String[] args) {
        StudentDao studentDao = new StudentDao();


        int count = studentDao.clear();
        System.out.println("Deleted items count: " + count);

        System.out.println("=============================================================");

        studentDao.insert(new Student("Ivan", 25));
        studentDao.insert(new Student("Alex", 30));
        studentDao.insert(new Student("Sofia", 28));
        studentDao.insert(new Student("Igor", 35));


        System.out.println("Students: " + studentDao.getAll());

        System.out.println("=============================================================");

        Student student = studentDao.getByName("Sofia");
        System.out.println("Student by name: " + student);

        student.setAge(32);
        studentDao.update(student);

        student = studentDao.getByName("Sofia");
        System.out.println("Updated student: " + student);

        System.out.println("=============================================================");

        System.out.println("Students by age: " + studentDao.getByAge(30, 50));

        System.out.println("=============================================================");


        student = studentDao.getByName("Ivan");
        studentDao.delete(student);
        student = studentDao.getByName("Alex");
        studentDao.delete(student);
        System.out.println("Students: " + studentDao.getAll());

        System.out.println("=============================================================");


        studentDao.close();
    }

}
