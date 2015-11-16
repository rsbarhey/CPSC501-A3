import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Serializer {
	private HashMap <Integer, Object>objects;
	private HashMap <Integer, Object>arrays;
	private HashMap <Integer, Object> alreadySerialized;
	final Class<?>[] PRIMITIVE_WRAPPERS = {Boolean.class, Byte.class, Short.class,
											Integer.class, Long.class, Float.class,
											 Double.class, Character.class, String.class};
	public Serializer()
	{
		objects = new HashMap<>();
		arrays = new HashMap<>();
		alreadySerialized = new HashMap<>();
	}
	public Document serialize(Object obj)
	{
		objects.put(obj.hashCode(), obj);
		Element rootElement = new Element("serialized");
		Document doc = new Document();
		doc.setRootElement(rootElement);
		Element object = new Element("object");
		object.setAttribute(new Attribute("class", obj.getClass().getName()));
		object.setAttribute(new Attribute("id", Integer.toString(obj.hashCode())));
		
		//Imidiate fields
		serializeFields(object ,obj, obj.getClass());
		//Inherited fields
		serializeInheritedFields(object, obj, obj.getClass().getSuperclass());
		// serialize parent class
		doc.getRootElement().addContent(object);
		
		//serialize arrays
		for(Map.Entry<Integer, Object> entry : arrays.entrySet())
		{
			serializeArray(doc.getRootElement(), entry.getValue());
		}
		
		//serialize objects
		for(Map.Entry<Integer, Object> entry : objects.entrySet())
		{
			if(!alreadySerialized.containsKey(entry.getKey()))
			{
				Element objRef = new Element("object");
				objRef.setAttribute(new Attribute("class", obj.getClass().getName()));
				objRef.setAttribute(new Attribute("id", Integer.toString(obj.hashCode())));
				serializeFields(objRef, entry.getValue(), entry.getValue().getClass());
				serializeInheritedFields(object, entry.getValue(), entry.getValue().getClass().getSuperclass());
				doc.getRootElement().addContent(objRef);
			}
		}
		
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
	private void serializeInheritedFields(Element object, Object obj, Class superClass) 
	{
		if(!superClass.equals(Object.class))
		{
			try {
				serializeFields(object, obj, superClass);
				serializeInheritedFields(object, obj, superClass.getSuperclass());
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void serializeFields(Element object,Object obj, Class classObj) 
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
					Element field = new Element("field");
					field.setAttribute(new Attribute("name", fields[i].getName()));
					field.setAttribute(new Attribute("declaringclass", fields[i].getDeclaringClass().getName()));
					Element reference = new Element("reference");
					reference.setText(Integer.toString(val.hashCode()));
					field.setContent(reference);
					object.addContent(field);
					arrays.put(val.hashCode(), val);
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
		alreadySerialized.put(obj.hashCode(), obj);
	}
	public void serializeArray(Element object, Object obj)
	{
		Element arrayElement = new Element("object");
		arrayElement.setAttribute(new Attribute("class", obj.getClass().getName()));
		arrayElement.setAttribute(new Attribute("id", Integer.toString(obj.hashCode())));
		arrayElement.setAttribute(new Attribute("length", Integer.toString(Array.getLength(obj))));
		
		if(obj.getClass().getComponentType().isPrimitive() || Arrays.asList(PRIMITIVE_WRAPPERS).contains(obj.getClass().getComponentType()))
		{
			for(int i = 0; i < Array.getLength(obj); i++)
			{
				Element value = new Element("value");
				value.setText(Array.get(obj, i).toString());
				arrayElement.addContent(value);
			}
			object.addContent(arrayElement);
		}
		else if(obj.getClass().getComponentType().isArray())
		{
			//handle array
		}
		else
		{
			for(int i = 0; i < Array.getLength(obj); i++)
			{
				Element reference = new Element("reference");
				reference.setText(Integer.toString(Array.get(obj, i).hashCode()));
				arrayElement.addContent(reference);
				objects.put(Array.get(obj, i).hashCode(), Array.get(obj, i));
			}
			object.addContent(arrayElement);
		}
	}
}
