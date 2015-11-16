
import java.util.Scanner;

public class ObjectCreator {
	private Scanner in;
	public ObjectCreator(Scanner in)
	{
		this.in = in;
	}
	
	public Primitive CreatePrimitive()
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
	
	public PrimitiveArray CreatePrimitiveArray()
	{
		System.out.println("Enter integers separated by a single space");
		System.out.println("int int int int int int ...");
		String value = in.nextLine();
		String[] parsedValue = value.split(" ");
		int[] array = new int[parsedValue.length];
		for(int i = 0; i<array.length; i++)
		{
			array[i] = Integer.parseInt(parsedValue[i]);
		}
		
		PrimitiveArray primitiveArray = new PrimitiveArray(array);
		
		System.out.println("Successfully created an object with array: "+ value);
		return primitiveArray;
	}
	
	public RefrenceArray CreateRefrenceArray()
	{
		System.out.println("Enter how many Primitive you wish to create");
		String value = in.nextLine();
		int count = Integer.parseInt(value);
		Primitive[] refArray = new Primitive[count];
		for(int i = 0; i<count; i++)
		{
			refArray[i] = CreatePrimitive();
		}
		
		System.out.println("Successfully created an object with array of references");
		return new RefrenceArray(refArray);
	}
	
	public PrimitiveCollection CreatePrimitiveCol()
	{
		System.out.println("Enter how many Primitive you wish to create");
		String value = in.nextLine();
		int count = Integer.parseInt(value);
		Primitive[] refArray = new Primitive[count];
		for(int i = 0; i<count; i++)
		{
			refArray[i] = CreatePrimitive();
		}
		
		System.out.println("Successfully created an object with array of references");
		return new PrimitiveCollection(refArray);
	}
}
