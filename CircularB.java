
public class CircularB {
	private CircularA cricularA;
	private int b;
	
	public CircularB()
	{
		b = 0;
		cricularA = null;
	}
	public CircularB(int para)
	{
		b = para;
	}
	
	public void SetCricle(CircularA circA)
	{
		this.cricularA = circA;
	}
}
