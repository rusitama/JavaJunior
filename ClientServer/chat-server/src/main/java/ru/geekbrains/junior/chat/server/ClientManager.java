package ru.geekbrains.junior.chat.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class ClientManager implements Runnable {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String name;
    public static ArrayList<ClientManager> clients = new ArrayList<>();

    public ClientManager(Socket socket) {
        try {
            this.socket = socket;
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clients.add(this);
            //TODO: ...
            name = bufferedReader.readLine();
            System.out.println(name + " подключился к чату.");
            broadcastMessage("Server: " + name + " подключился к чату.");
        }
        catch (Exception e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        // Удаление клиента из коллекции
        removeClient();
        try {
            // Завершаем работу буфера на чтение данных
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            // Завершаем работу буфера для записи данных
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            // Закрытие соединения с клиентским сокетом
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаление клиента из коллекции
     */
    private void removeClient() {
        clients.remove(this);
        System.out.println(name + " покинул чат.");
        broadcastMessage("Server: " + name + " покинул чат.");
    }

    /**
     * Отправка сообщения всем слушателям
     *
     * @param message сообщение
     */
    private void broadcastMessage(String message) {
        if (message.startsWith("@")) {
            // Разбиваем сообщение на части по первому символу ":"
            String[] parts = message.split(" ", 2);
            if (parts.length >= 2) {
                String recipientName = parts[0].substring(1).trim(); // Получаем имя адресата
                String privateMessage = parts[1].trim(); // Получаем само приватное сообщение

                // Проверяем наличие пользователя в чате
                boolean userFound = false;
                for (ClientManager client : clients) {
                    if (client.name.equalsIgnoreCase(recipientName)) {
                        try {
                            client.bufferedWriter.write("[Приватное сообщение] " + this.name + ": " + privateMessage);
                            client.bufferedWriter.newLine();
                            client.bufferedWriter.flush();
                            userFound = true;
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (!userFound) {
                    try {
                        // Сообщение, если пользователь не найден
                        this.bufferedWriter.write("Server: Пользователь " + recipientName + " не найден.");
                        this.bufferedWriter.newLine();
                        this.bufferedWriter.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            // Отправка общего сообщения всем клиентам
            for (ClientManager client : clients) {
                try {
                    if (!client.equals(this) && message != null) {
                        client.bufferedWriter.write(this.name + ": " + message);
                        client.bufferedWriter.newLine();
                        client.bufferedWriter.flush();
                    }
                } catch (Exception e) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                }
            }
        }
    }

    @Override
    public void run() {
        String massageFromClient;
        while (!socket.isClosed()) {
            try {
                // Чтение данных
                massageFromClient = bufferedReader.readLine();
                // Отправка данных всем слушателям
                broadcastMessage(massageFromClient);
            }
            catch (Exception e){
                closeEverything(socket, bufferedReader, bufferedWriter);
                //break;
            }
        }
    }
}