
public class PrimitiveArray {
	private int[] intArray;
	
	public PrimitiveArray()
	{
		int[] intArray = new int[0];
	}
	public PrimitiveArray(int[] intPara)
	{
		intArray = new int[intPara.length];
		for(int i = 0; i<intArray.length; i++)
		{
			intArray[i] = intPara[i];
		}
	}
}
