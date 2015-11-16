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
	
	public CircularA CreateCircular()
	{
		System.out.println("Enter two integers separated by a space");
		System.out.println("int int");
		String value = in.nextLine();
		String[] parsedValue = value.split(" ");
		int a = Integer.parseInt(parsedValue[0]);
		int b = Integer.parseInt(parsedValue[1]);
		
		CircularA circA = new CircularA(a);
		CircularB circB = new CircularB(b);
		circA.SetCircle(circB);
		circB.SetCricle(circA);
		
		System.out.printf("Successfully created CircualrA object with field value %d and field reference to CircularB\n", a);
		System.out.printf("Successfully created CircualrB object with field value %d and field reference to CircularA\n", b);
		return circA;
	}
}
