import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
	
	public static final int PORT = 4732;
	
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(PORT);
		ClientHandler clientHandler = new ClientHandler();
		clientHandler.start();
		boolean running = true;
		
		Socket client;
		
		while (running) {
			client = serverSocket.accept();
			System.out.println("Found client connection!");
			
			clientHandler.addClient(client);
			
		}
	}

}
