package main.java;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {

    static volatile List<Integer> output = new ArrayList<>();
    public static void main(String[] args) throws IOException
    {

        ServerSocket serverSocket = new ServerSocket(8090);

        Socket socket;

        while (true)
        {
            socket = serverSocket.accept();

            System.out.println("New client request received : " + socket);

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            System.out.println("Creating a new handler for this client...");

            ClientHandler clientHandler = new ClientHandler(dataInputStream);

            Thread t = new Thread(clientHandler);

            t.start();
        }

    }
}

class ClientHandler implements Runnable
{
    private final DataInputStream dataInputStream;

    public ClientHandler(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    @Override
    public void run() {

        String received;
        try
        {
            received = dataInputStream.readUTF();
            Scanner scanner = new Scanner(received);
            while (scanner.hasNextInt())
                Server.output.add(scanner.nextInt());
            Collections.sort(Server.output);
            BufferedWriter writer = new BufferedWriter(new FileWriter("Output.txt"));
            writer.write(Arrays.toString(Server.output.toArray()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
