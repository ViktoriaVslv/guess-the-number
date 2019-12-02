package ChatUDP;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Bot {
    private static boolean isNum(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static void main(String args[]) throws Exception {
        Scanner scan = new Scanner(System.in);
        System.out.println("Port: ");
        String p= scan.nextLine();

        DatagramSocket serverSocket = new DatagramSocket(Integer.parseInt(p));
        byte[] receiveData = new byte[1024];

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket);

        InetAddress ipAddress = receivePacket.getAddress();
        int port = receivePacket.getPort();
        String str="Welcome! Enter limit:\n @name -- change name \n @quit -- go out\n";
        System.out.println("Bot: "+str);
        DatagramPacket sendPacket = new DatagramPacket(str.getBytes(),str.getBytes().length, ipAddress, port);
        serverSocket.send(sendPacket);
        boolean flag=false;
        int begin=0;
        int end=0;
        int num=0;
        try{
            String name = "Gamer";

            while (true) {

                byte[] receiveData1 = new byte[1024];
                DatagramPacket receivePacket1 = new DatagramPacket(receiveData1, receiveData1.length);
                serverSocket.receive(receivePacket1);

                int length;
                for(length = 0; receiveData1[length] != 0; length++);

                byte[] correctReceive = new byte[length];
                for(int j = 0; j < length; j++)
                {
                    correctReceive[j] = receiveData1[j];
                }

                String sentence = new String(correctReceive);
                if((isNum(sentence))&&(!flag)){
                    if(Integer.parseInt(sentence)<=0){}
                    else {
                        end = Integer.parseInt(sentence);
                        flag = true;
                        num = end / 2;
                        System.out.println(name + ": limit: 0- " + sentence);
                        str = "Chislo bolshe - " + num + "? (yes/no)";
                        System.out.println("Bot: "+str);
                        sendPacket = new DatagramPacket(str.getBytes(), str.getBytes().length, ipAddress, port);
                        serverSocket.send(sendPacket);
                        continue;
                    }
                }
                if(sentence.equals("@name")||sentence.equals("yes")||sentence.equals("no")) {
                    if (sentence.equals("@name")) {
                        byte[] receiveDataName = new byte[1024];
                        DatagramPacket receivePacketName = new DatagramPacket(receiveDataName, receiveDataName.length);
                        serverSocket.receive(receivePacketName);
                        int len;
                        for (len = 0; receiveDataName[len] != 0; len++) ;
                        byte[] correctReceive1 = new byte[len];
                        for (int j = 0; j < len; j++) {
                            correctReceive1[j] = receiveDataName[j];
                        }
                        String sentence1 = new String(correctReceive1);
                        name = sentence1;
                        //sentence = "";
                        System.out.println("Bot: "+str);
                        sendPacket = new DatagramPacket(str.getBytes(), str.getBytes().length, ipAddress, port);
                        serverSocket.send(sendPacket);

                    }
                    if (sentence.equals("yes")) {
                        System.out.println(name + ": " + sentence);
                        num++;
                        begin = num;
                        num = num + (end - begin) / 2;
                        if((end- begin)==1){num=begin;}
                        str = "Chislo bolshe - " + num + "? (yes/no)";

                        if ((end == begin)) {
                            str = "Chislo:" + end+". Dialog over.";
                            System.out.println("Bot: "+str);
                            sendPacket = new DatagramPacket(str.getBytes(), str.getBytes().length, ipAddress, port);
                            serverSocket.send(sendPacket);
                            System.exit(0);
                            serverSocket.close();
                        }
                        System.out.println("Bot: "+str);
                        sendPacket = new DatagramPacket(str.getBytes(), str.getBytes().length, ipAddress, port);
                        serverSocket.send(sendPacket);
                    }
                    if (sentence.equals("no")) {
                        System.out.println(name + ": " + sentence);
                        end = num;
                        num = num - (end - begin) / 2;
                        if (((end- begin)==1)) {
                            num=begin;
                        }
                        str = "Chislo bolshe - " + num + "? (yes/no)";

                        if ((end == begin)) {
                            str = "Chislo:" + begin+". Dialog over.";
                            System.out.println("Bot: "+str);
                            sendPacket = new DatagramPacket(str.getBytes(), str.getBytes().length, ipAddress, port);
                            serverSocket.send(sendPacket);
                            System.exit(0);
                            serverSocket.close();
                        }
                        System.out.println("Bot: "+str);
                        sendPacket = new DatagramPacket(str.getBytes(), str.getBytes().length, ipAddress, port);
                        serverSocket.send(sendPacket);
                    }
                }
                else {
                    System.out.println(name + ": " + sentence);
                    System.out.println("Bot: "+str);
                    sendPacket = new DatagramPacket(str.getBytes(), str.getBytes().length, ipAddress, port);
                    serverSocket.send(sendPacket);
                }
            }
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}