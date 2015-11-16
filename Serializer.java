import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.IdentityHashMap;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Serializer {
	private IdentityHashMap objects;
	final Class<?>[] PRIMITIVE_WRAPPERS = {Boolean.class, Byte.class, Short.class,
											Integer.class, Long.class, Float.class,
											 Double.class, Character.class, String.class};
	public Serializer()
	{
		objects = new IdentityHashMap<>();
	}
	public Document serialize(Object obj)
	{
		Element rootElement = new Element("serialized");
		Document doc = new Document();
		doc.setRootElement(rootElement);
		Element object = new Element("object");
		object.setAttribute(new Attribute("class", obj.getClass().getName()));
		object.setAttribute(new Attribute("id", Integer.toString(obj.hashCode())));
		
		//Imidiate fields
		serializeFields(doc, object ,obj, obj.getClass());
		//Inherited fields
		serializeInheritedFields(doc, object, obj, obj.getClass().getSuperclass());
		// serialize parent class
		doc.getRootElement().addContent(object);
		
		XMLOutputter xmlOut = new XMLOutputter();
		xmlOut.setFormat(Format.getPrettyFormat());
		try {
			xmlOut.output(doc, System.out);
			xmlOut.output(doc, new FileWriter("newXmlFile.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return doc;
	}
	private void serializeInheritedFields(Document doc, Element object, Object obj, Class superClass) 
	{
		if(!superClass.equals(Object.class))
		{
			try {
				serializeFields(doc, object, obj, obj.getClass().getSuperclass());
				serializeInheritedFields(doc, object, obj, superClass.getSuperclass());
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void serializeFields(Document doc, Element object,Object obj, Class classObj) 
	{
		Field[] fields = classObj.getDeclaredFields();
		for(int i = 0; i<fields.length; i++)
		{
			if(!fields[i].isAccessible())
			{
				fields[i].setAccessible(true);
			}
			Object val;
			try {
				val = fields[i].get(obj);
				if((Arrays.asList(PRIMITIVE_WRAPPERS).contains(val.getClass())|| val.getClass().isPrimitive()))
				{
					Element field = new Element("field");
					field.setAttribute(new Attribute("name", fields[i].getName()));
					field.setAttribute(new Attribute("declaringclass", fields[i].getDeclaringClass().getName()));
					Element value = new Element("value");
					value.setText(val.toString());
					field.setContent(value);
					object.addContent(field);
				}
				else if (val.getClass().isArray())
				{
					// handle array
				}
				else
				{
					Element field = new Element("field");
					field.setAttribute(new Attribute("name", fields[i].getName()));
					field.setAttribute(new Attribute("declaringclass", fields[i].getDeclaringClass().getName()));
					Element reference = new Element("reference");
					reference.setText(Integer.toString(val.hashCode()));
					field.setContent(reference);
					object.addContent(field);
					objects.put(val.hashCode(), val);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
