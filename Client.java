
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.util.Scanner;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
public class Client {
	
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 2225);
			Scanner in = new Scanner(System.in);
			ObjectCreator objCreator = new ObjectCreator(in);
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
					Object obj = ProcessChoice(choice, objCreator);
					if(obj != null)
					{
						System.out.println("Serializing...");
						try {
							Document doc = Serializer.serialize(obj);
							XMLOutputter xmlOut = new XMLOutputter();
							xmlOut.setFormat(Format.getPrettyFormat());
							
							xmlOut.output(doc, System.out);
							xmlOut.output(doc, new FileWriter("newXmlFile.xml"));
							System.out.println("Sending");
							File fileToSend = new File("newXmlFile.xml");
							if(fileToSend.exists())
							{
								FileInputStream fin = new FileInputStream(fileToSend);
								byte[] fileBytes = new byte[(int) fileToSend.length()];
								fin.read(fileBytes);
								OutputStream out = socket.getOutputStream();
								out.write(fileBytes);
								out.flush();
								out.close();
								socket.close();
							}
							break;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
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
		System.out.println("2- Circular referenced object");
		System.out.println("3- PrimitiveArry object");
		System.out.println("4- ReferenceArray object");
		System.out.println("5- Collction of Primitive Objects");
		
		System.out.println("*****************************************************");
		System.out.println("Type in Quit to quit the program");
	}
	public static Object ProcessChoice(String choice, ObjectCreator objCreator)
	{
		switch(choice)
		{
		case "1":
			System.out.println("You have chosen Primitive");
			return objCreator.CreatePrimitive();
		case "2":
			System.out.println("You have chosen Circular object");
			return objCreator.CreateCircular();
		case "3":
			System.out.println("You have chosen PrimitiveArray");
			return objCreator.CreatePrimitiveArray();
		case "4":
			System.out.println("You have chosen Reference Array");
			return objCreator.CreateRefrenceArray();
		case "5":
			System.out.println("You have chosen PrimitiveObj Collection");
			return objCreator.CreatePrimitiveCol();
		default:
			System.out.println("Unknown choice, please follow the instruction");
			PrintInstruction();
		}
		return null;
	}
}
