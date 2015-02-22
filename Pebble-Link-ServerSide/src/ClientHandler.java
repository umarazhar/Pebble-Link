import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;


public class ClientHandler extends Thread {
	
	private ArrayList<Client> clients;
	private Queue<Client> commQueue;
	
	private boolean running;
	
	public ClientHandler() {
		clients = new ArrayList<Client>();
		commQueue = new PriorityQueue<Client>();
		running = true;
	}
	
	public void checkData() {
		for (Client client : clients) {
			if (client.readyToSend()) {
				commQueue.add(client);
			}
		}
	}
	
	public void transferData() {
		Client client1 = commQueue.remove();
		
		Client client2 = null;
		
		while (client2 == null) {
			client2 = commQueue.poll();
		}
		
		while (true) {
			try {
				client2.sendData(client1.getSendData());
				break;
			} catch (IOException e) {
				
			}
		}
			
		delay(20);
		
		while (true) {
			try {
				client1.sendData(client2.getSendData());
				break;
			} catch (IOException e) {
				
			}
		}
		
		System.out.println("All data successfully transferred!");
	}
	
	public void addClient(Socket socket) {
		Client newClient = new Client(socket, clients);
		clients.add(newClient);
		System.out.println("Client added to handler!");
	}
	
	public void run() {
		
		while (running) {
//			System.out.println(clients.size());
			
			checkData();
			if (!commQueue.isEmpty()) {
				System.out.println("Starting data transfer!");
				transferData();
			}
		}
		
	}
	
	public void delay(long milliseconds) {
		Thread current = Thread.currentThread();
		
		try {
			current.sleep(milliseconds);
		} catch (Exception e) {
			
		}
	}

}
