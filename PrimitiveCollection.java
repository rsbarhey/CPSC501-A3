import java.util.ArrayList;
import java.util.Arrays;

public class PrimitiveCollection {
	private ArrayList<Primitive> primitiveCollection;
	
	public PrimitiveCollection()
	{
		primitiveCollection = new ArrayList<>();
	}
	public PrimitiveCollection(Primitive[] para)
	{
		primitiveCollection = new ArrayList(Arrays.asList(para));
	}
}
