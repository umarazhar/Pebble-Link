import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;


public class Client extends Thread implements Comparable{
	
	private Socket socket;
	private ArrayList<Client> clients;
	
	private String toSend = null;
	
	private boolean running;
	
	public Client(Socket socket, ArrayList<Client> clients) {
		this.socket = socket;
		this.clients = clients;
		
		running = true;
	}
	
	public void update() throws IOException {
		String comm = null;
		
		while (comm == null) {
			comm = DataHandler.readLine(socket.getInputStream());
		}
		
		System.out.println("Received Information: " + comm);
		
		toSend = comm;
		
	}
	
	public void sendData(String data) throws IOException {
		DataHandler.writeLine(socket.getOutputStream(), data);
		socket.getOutputStream().flush();
	}
	
	public String getSendData() {
		String toReturn = toSend;
		toSend = null;
		
		return toReturn;
	}
	
	public boolean readyToSend() {
		return (toSend != null);
	}
	
	public void run() {
		
		while (running) {
			try {
				if (!readyToSend()) {
					update();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public int compareTo(Object arg0) {
		Client tmp = (Client)arg0;
		if (tmp == this)
			return 0;
		
		return -1;
	}

}
