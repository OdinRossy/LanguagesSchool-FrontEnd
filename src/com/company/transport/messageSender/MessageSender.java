package com.company.transport.messageSender;

import com.company.transport.configuration.ServerConfiguration;
import com.company.transport.request.Request;
import com.company.transport.response.Response;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class MessageSender implements ServerConfiguration {

    public Response sendRequestToServer(Request request) {
        try (Socket socket = new Socket(InetAddress.getByName(TCP_SERVER_ADDRESS), TCP_SERVER_PORT);
             OutputStream outputStream = socket.getOutputStream();
             InputStream inputStream = socket.getInputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {

            objectOutputStream.writeObject(request);
            return (Response) objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static MessageSender instance;

    public static MessageSender getInstance() {
        if (instance == null) {
            instance = new MessageSender();
        }
        return instance;
    }

    private MessageSender() {}
}
