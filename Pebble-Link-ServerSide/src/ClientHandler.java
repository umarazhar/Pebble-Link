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
		for (int i = 0; i < clients.size(); i++) {
			if (commQueue.contains(clients.get(i)))
				continue;
			if (clients.get(i).readyToSend()) {
				System.out.println("Adding client to queue...");
				commQueue.add(clients.get(i));
			}
		}
	}
	
	public void transferData() {
		System.out.println("Size before removing first client from Queue: " + commQueue.size());
		Client client1 = commQueue.remove();
		
		System.out.println("Size after removing first client from Queue: " + commQueue.size());
		
		Client client2 = null;
		
		while (client2 == null) {
			client2 = commQueue.poll();
		}
		
		System.out.println("Size after removing second client from Queue: " + commQueue.size());
		
		while (true) {
			try {
				System.out.println("Client 1 before getSendData() readyToSend(): " + client1.readyToSend());;
				client2.sendData(client1.getSendData());
				System.out.println("Client 1 after getSendData() readyToSend(): " + client1.readyToSend());
				break;
			} catch (IOException e) {
				
			}
		}
		
		System.out.println("Client 2 data sent!");
		
		delay(20);
		
		while (true) {
			try {
				System.out.println("Client 2 before getSendData() readyToSend(): " + client1.readyToSend());
				client1.sendData(client2.getSendData());
				System.out.println("Client 2 after getSendData() readyToSend(): " + client1.readyToSend());
				break;
			} catch (IOException e) {
				
			}
		}
		
		System.out.println("All data successfully transferred!");
	}
	
	public void addClient(Socket socket) {
		Client newClient = new Client(socket, clients);
		newClient.start();
		clients.add(newClient);
		System.out.println("Client added to handler!");
	}
	
	public void run() {
		
		while (running) {
//			System.out.println(clients.size());
			checkData();
			if (commQueue.size() > 1) {
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
