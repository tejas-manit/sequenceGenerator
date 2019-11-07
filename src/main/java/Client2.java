package main.java;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client2 {

    private final static int SERVER_PORT = 8090;

    public static void main(String[] args) throws IOException
    {
        InetAddress ip = InetAddress.getByName("localhost");
        Socket socket = new Socket(ip, SERVER_PORT);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        Thread sendMessage = new Thread(() -> {
            BufferedReader reader;
            StringBuilder message = new StringBuilder();
            try {
                reader = new BufferedReader(new FileReader(
                        "File2.txt"));
                String line = reader.readLine();

                while (line != null) {
                    message.append(line).append("\n");
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                dataOutputStream.writeUTF(message.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        sendMessage.start();

    }
}

