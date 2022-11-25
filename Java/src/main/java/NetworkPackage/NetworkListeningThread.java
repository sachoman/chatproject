package NetworkPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkListeningThread {
	public NetworkListeningThread(int port, MyTCPthread th) throws ClassNotFoundException {
   	 try (ServerSocket serverSocket = new ServerSocket(port)) {
   		 
            System.out.println("Server is listening on port " + port);
 
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                th.setSocket(socket);
                th.run(socket);
            }
 
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
   }
}
