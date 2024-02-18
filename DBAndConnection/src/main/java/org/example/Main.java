package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {

    public static void main(String[] args) {
        // Создание фабрики сессий
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();

        // Создание сессии
        Session session = sessionFactory.getCurrentSession();

        try {
            // Начало транзакции
            session.beginTransaction();

            // Создание объекта
            Course course = new Course();
            course.setTitle("Java Programming");
            course.setDuration(30);

            session.save(course);
            System.out.println("Object course save successfully");

            // Чтение данных
            Course retrievedCourse = session.get(Course.class, course.getId());
            System.out.println("Прочитанный курс: " + retrievedCourse.getTitle());

            // Обновление данных
            retrievedCourse.setTitle("Advanced Java Programming");
            session.update(retrievedCourse);

            // Удаление данных
            //session.delete(retrievedCourse);

            // Коммит транзакции
            session.getTransaction().commit();
        }
        finally {
            // Закрытие фабрики сессий
            sessionFactory.close();
        }
    }
}

