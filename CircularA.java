
public class CircularA {
	private CircularB circularB;
	int a;
	public CircularA()
	{
		a = 0;
		circularB = null;
	}
	public CircularA(int para)
	{	
		a = para;
	}
	
	public void SetCircle(CircularB circB)
	{
		this.circularB = circB;
	}
}
