package org.example;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        Animal[] animals = {
                new Dog("Buddy", 3, "Labrador"),
                new Cat("Whiskers", 5, true)
        };

        for (Animal animal : animals) {
            System.out.println("Name: " + animal.getName());
            System.out.println("Age: " + animal.getAge());

            // Вызов метода makeSound(), если такой метод присутствует
            try {
                Method makeSoundMethod = animal.getClass().getMethod("makeSound");
                makeSoundMethod.invoke(animal);
            } catch (Exception e) {
                // Метод makeSound() не найден
            }
            System.out.println();
        }
    }
}