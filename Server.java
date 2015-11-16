import java.io.IOException;
import java.net.*;
import java.util.Scanner;
public class Server {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(2225);
			System.out.printf("Server is listening on port %d\n", 2225);
			System.out.println("Type in Quit to stop the server");
			Scanner in = new Scanner(System.in);
			MainThread thread = new MainThread(serverSocket);
			thread.start();
			while(true)
			{
				
				if(in.nextLine().equalsIgnoreCase("quit"))
				{
					thread.terminate();
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("Failed to initialize the server");
			System.out.println("Quiting...");
			e.printStackTrace();
		}

	}

}
