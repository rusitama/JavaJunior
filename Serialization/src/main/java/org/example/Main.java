package org.example;

import java.io.*;

public class Main {
    public static void main(String[] args) {

        // Создание объекта Student и инициализация данными
        Student student = new Student("John", 20, 3.5);

        // Сериализация объекта в файл
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("student.ser"))) {
            outputStream.writeObject(student);
            System.out.println("Объект успешно сериализован в файл.");
        } catch (IOException e) {
            System.err.println("Ошибка сериализации: " + e.getMessage());
        }

        // Десериализация объекта из файла
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("student.ser"))) {
            Student deserializedStudent = (Student) inputStream.readObject();
            System.out.println("Объект успешно десериализован из файла.");

            // Вывод всех полей объекта, включая GPA
            System.out.println("Имя: " + deserializedStudent.getName());
            System.out.println("Возраст: " + deserializedStudent.getAge());
            System.out.println("Средний балл: " + deserializedStudent.getGPA());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка десериализации: " + e.getMessage());
        }
    }
}