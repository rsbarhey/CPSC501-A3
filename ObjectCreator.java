import java.util.Scanner;

public class ObjectCreator {
	private Scanner in;
	public ObjectCreator(Scanner in)
	{
		this.in = in;
	}
	
	public Primitive CreatePrimitve()
	{
		System.out.println("Enter the fields values seprated by a single");
		System.out.println("int float string");
		System.out.println("Note that the string can have space inside it");
		String value = in.nextLine();
		String[] parsedValue = value.split(" ", 3);
		
		int intVal = Integer.parseInt(parsedValue[0]);
		float floatVal = Float.parseFloat(parsedValue[1]);
		String stringVal = parsedValue[2];
		
		System.out.println("Successfully created Primitive with values " + value);
		return new Primitive(intVal, floatVal, stringVal);
	}
}
