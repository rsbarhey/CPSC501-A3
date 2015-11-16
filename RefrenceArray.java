
public class RefrenceArray {
	private Primitive[] refArray;
	public RefrenceArray()
	{
		refArray = null;
	}
	public RefrenceArray(Primitive[] refArray)
	{
		this.refArray = new Primitive[refArray.length];
		
		for(int i = 0; i<refArray.length; i++)
		{
			this.refArray[i] = refArray[i];
		}
	}
}
