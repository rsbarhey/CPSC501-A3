


import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;

public class Serializer{
	public static Document serialize(Object source) throws Exception {
		return serializeHelper(source, new Document(new Element("serialized")),
				new IdentityHashMap());
	}

	private static Document serializeHelper(Object source, Document target,
			Map table) throws Exception {
		String id = Integer.toString(table.size()); //#1
		table.put(source, id); //#1
		Class sourceclass = source.getClass(); //#1

		Element oElt = new Element("object"); //#2
		oElt.setAttribute("class", sourceclass.getName()); //#2
		oElt.setAttribute("id", id); //#2
		target.getRootElement().addContent(oElt); //#2

		if (!sourceclass.isArray()) { //#3
			Field[] fields = Mopex.getInstanceVariables(sourceclass); //#4
			for (int i = 0; i < fields.length; i++) { //#4

				if (!Modifier.isPublic(fields[i].getModifiers())) //#5
					fields[i].setAccessible(true); //#5

				Element fElt = new Element("field"); //#6
				fElt.setAttribute("name", fields[i].getName()); //#6
				Class declClass = fields[i].getDeclaringClass(); //#6
				fElt.setAttribute("declaringclass", //#6
						declClass.getName()); //#6
				fElt.setAttribute("type", //#6
						fields[i].getType().getName()); //#6
				//#6
				Class fieldtype = fields[i].getType(); //#6
				Object child = fields[i].get(source); //#6
				//#6
				if (Modifier.isTransient(fields[i].getModifiers())) { //#6
					child = fields[i].get(source); //#6
					
				} //#6
				fElt.addContent(serializeVariable(fieldtype, child, //#6
						target, table)); //#6
				//#6
				oElt.addContent(fElt); //#6
			}
		} else {
			Class componentType = sourceclass.getComponentType(); //#7
			//#7
			int length = Array.getLength(source); //#7
			oElt.setAttribute("length", Integer.toString(length)); //#7
			//#7
			for (int i = 0; i < length; i++) { //#7
				oElt.addContent(serializeVariable(componentType, //#7
						Array.get(source, i),//#7
						target, //#7
						table)); //#7
			} //#7
		}
		return target;
	}
	
	private static Element serializeVariable(Class fieldtype, Object child,
			Document target, Map table) throws Exception {
		if (child == null) {
			return new Element("null");
		} else if (!fieldtype.isPrimitive()) {
			Element reference = new Element("reference");
			if (table.containsKey(child)) {
				reference.setText(table.get(child).toString());
			} else {
				reference.setText(Integer.toString(table.size()));
				serializeHelper(child, target, table);
			}
			return reference;
		} else {
			Element value = new Element("value");
			value.setText(child.toString());
			return value;
		}
	}
}