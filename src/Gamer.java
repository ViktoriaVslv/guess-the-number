package ChatUDP;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Gamer {
    public static void main(String args[]) throws Exception {
        Scanner scan = new Scanner(System.in);
        System.out.println("Address: ");
        String address= scan.nextLine();

        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress ip = InetAddress.getByName(address);
        System.out.println("Port: ");
        String p =scan.nextLine();
        int port = Integer.parseInt(p);
        byte[] sendData = new byte[1024];
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, port);
        clientSocket.send(sendPacket);

        try{
            String name = "Gamer";
            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);

                int length;
                for(length = 0; receiveData[length] != 0; length++);
                byte[] correctReceive = new byte[length];
                for(int j = 0; j < length; j++)
                {
                    correctReceive[j] = receiveData[j];
                }
                String sentence = new String(correctReceive);
                System.out.println("Bot: " +sentence);
                System.out.println(name+": ");
                String answer =scan.nextLine();
                if (answer.equals("@name")) {
                    System.out.println("input your name: ");
                    name = scan.nextLine();
                    sendPacket = new DatagramPacket("@name".getBytes(),"@name".getBytes().length, ip, port);
                    clientSocket.send(sendPacket);
                    sendPacket = new DatagramPacket(name.getBytes(),name.getBytes().length, ip, port);
                    clientSocket.send(sendPacket);
                    continue;
                }
                if (answer.equals("@quit")) {
                    String end = "Dialog over";
                    sendData = end.getBytes();
                    DatagramPacket res = new DatagramPacket(sendData, sendData.length, ip, port);
                    clientSocket.send(res);
                    System.exit(0);
                    clientSocket.close();
                }
                sendData=answer.getBytes();
                DatagramPacket sendPacket1 = new DatagramPacket(sendData, sendData.length, ip, port);
                clientSocket.send(sendPacket1);
            }
        }
        catch (IOException ex){
        System.out.println(ex.getMessage());
        }

    }
}
