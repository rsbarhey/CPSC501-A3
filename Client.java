import java.io.IOException;
import java.net.*;
import java.util.Scanner;
public class Client {
	
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 2225);
			Scanner in = new Scanner(System.in);
			PrintInstruction();
			while(true)
			{
				String choice = in.nextLine();
				if(choice.equalsIgnoreCase("quit"))
				{
					System.out.println("Quitting...");
					break;
				}
				else
				{
					ProcessChoice(choice);
				}
			}
			
		} catch (UnknownHostException e) {
			System.out.println("Failed to connect to server...");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Failed to connect to server...");
			e.printStackTrace();
		}
		
	}
	
	public static void PrintInstruction()
	{
		System.out.println("------------Object creator interface-----------------");
		System.out.println("Please enter the number of the object you wish to create");
		System.out.println("1- Primitive object");
		
		System.out.println("*****************************************************");
		System.out.println("Type in Quit to quit the program");
	}
	public static void ProcessChoice(String choice)
	{
		switch(choice)
		{
		case "1":
			System.out.println("You have chosen Primitive");
			break;
		default:
			System.out.println("Unknown choice, please follow the instruction");
			PrintInstruction();
		}
	}
}
