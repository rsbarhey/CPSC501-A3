import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * @author Ragheb Barheyan
 * @version 1.0, 22 Oct, 2015
 */
public class ThreadedConnection extends Thread{
	private Socket connectedSocket;
	/**
	 * 
	 * @param socket The client's socket
	 */
	public ThreadedConnection(Socket socket)
	{
		connectedSocket = socket;
	}
	
	/**
	 * Runs the thread responsible to send back a response
	 */
	public void run()
	{
		try {
			//Get the input stream of the socket
			while(true)
			{
				InputStream in = connectedSocket.getInputStream();
				if(in.available() > 0)
				{
					byte[] fileBytes = new byte[in.available()];
					in.read(fileBytes);
					File file = new File("XmlFile.xml");
					FileOutputStream fos = new FileOutputStream(file);
					fos.write(fileBytes);
					fos.close();
					
					SAXBuilder builder = new SAXBuilder();
					Document doc = (Document) builder.build(file);
					try {
						Object obj = Deserializer.deserializeObject(doc);
						//Inspector inspector = new Inspector();
						//inspector.inspect(obj, true);
						System.out.println("\n\n");
						ObjectInspector inspector = new ObjectInspector();
						inspector.inspect(obj, true);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}
	}
}
