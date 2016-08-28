import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Merzier
 * Waits for client to connect, and then computes the quadratic equation, and send the data back to
 * the client.
 *
 */
public class Server {
	public static void main(String args[]) throws IOException, ClassNotFoundException, EOFException {
		
		// creating the server
		ServerSocket server=new ServerSocket(12345, 100);
		System.out.println("Server is listening at port 12345...");
		Socket connection=server.accept();
		
		System.out.println("A client connected.");
		ObjectOutputStream output=
			new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		ObjectInputStream input=
			new ObjectInputStream(connection.getInputStream());
		
		Object obj = null;//was not used
		double a = 0,b,c, root1, root2;
		
	

		while(true)
		{	//loop stores input from client and determines root
			   String message = (String) input.readObject();//read object cast as string and tores it
		        
				if(message.equals("TERMINATES"))
				{//will close the server
				     input.close();
		             output.close();
		             connection.close();
		             System.out.println("Server shut down");
		             break;
				}
									
			 a  = input.readDouble();
			 b  = input.readDouble();
			 c  = input.readDouble();
			 double discriminant = ((b*b) - (4 * a *c));
			 boolean flag = false; //determines if there is a root and will be sent to cleint
			if(a== 0)
			{
					output.writeBoolean(flag);
					output.flush();
			}// end of i a == 0
			
			else if (discriminant > 0)
			{
				 	flag = true;
				 	output.writeBoolean(flag);
				 	output.flush();
		            root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
		            output.writeDouble(root1);
		            output.flush();
		            root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
		            output.writeDouble(root2);
		            output.flush();
			}// end of else if
			
	        	
		}//end of while loop
				input.close();
				
				output.close();

				connection.close();
	}
	
	
}
