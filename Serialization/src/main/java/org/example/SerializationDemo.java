package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.jetbrains.annotations.Nullable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class SerializationDemo {

    // Сериализация в XML
    public static void serializeToXML(Student student, String fileName) {
        try {
            JAXBContext context = JAXBContext.newInstance(Student.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(student, new File(fileName));
            System.out.println("Объект успешно сериализован в XML файл.");
        } catch (Exception e) {
            System.err.println("Ошибка сериализации в XML: " + e.getMessage());
        }
    }

    // Десериализация из XML
    public static @Nullable Student deserializeFromXML(String fileName) {
        try {
            JAXBContext context = JAXBContext.newInstance(Student.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Student) unmarshaller.unmarshal(new File(fileName));
        } catch (Exception e) {
            System.err.println("Ошибка десериализации из XML: " + e.getMessage());
            return null;
        }
    }

    // Сериализация в JSON
    public static void serializeToJSON(Student student, String fileName) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File(fileName), student);
            System.out.println("Объект успешно сериализован в JSON файл.");
        } catch (Exception e) {
            System.err.println("Ошибка сериализации в JSON: " + e.getMessage());
        }
    }

    // Десериализация из JSON
    public static @Nullable Student deserializeFromJSON(String fileName) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new File(fileName), Student.class);
        } catch (Exception e) {
            System.err.println("Ошибка десериализации из JSON: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        Student student = new Student("John", 20, 3.5);

        // Сериализация в XML
        serializeToXML(student, "student.xml");

        // Десериализация из XML
        Student deserializedStudentXML = deserializeFromXML("student.xml");
        if (deserializedStudentXML != null) {
            System.out.println("Десериализация из XML:");
            System.out.println("Имя: " + deserializedStudentXML.getName());
            System.out.println("Возраст: " + deserializedStudentXML.getAge());
            System.out.println("Средний балл: " + deserializedStudentXML.getGPA());
        }

        // Сериализация в JSON
        serializeToJSON(student, "student.json");

        // Десериализация из JSON
        Student deserializedStudentJSON = deserializeFromJSON("student.json");
        if (deserializedStudentJSON != null) {
            System.out.println("\nДесериализация из JSON:");
            System.out.println("Имя: " + deserializedStudentJSON.getName());
            System.out.println("Возраст: " + deserializedStudentJSON.getAge());
            System.out.println("Средний балл: " + deserializedStudentJSON.getGPA());
        }
    }
}
