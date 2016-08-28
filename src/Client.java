import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Merzier
 * Client sends data to server.
 *
 */
public class Client {
	public static void main(String args[]) throws IOException {
		Scanner scan=new Scanner(System.in);
		char choice; 
		double a,b,c,root1,root2;
		boolean repeat = false;
		boolean flag = true;
		
		System.out.println("Connecting to the server...");
		Socket client=new Socket("localhost",12345);
		System.out.println("Server connected.");
		
		ObjectOutputStream output=
				new ObjectOutputStream(client.getOutputStream());
			
			ObjectInputStream input=
				new ObjectInputStream(client.getInputStream());
		
			System.out.print("Do you wish to solve quadratic equations Y/N: ");
			choice = scan.next().charAt(0);
		
		if(choice == 'Y' || choice == 'y')
		{
			repeat = true;
			output.writeObject("Begin");
			output.flush();
			while(repeat == true)
			{
				System.out.print("Enter the 1st coeffiecent a= ");
				a = scan.nextDouble();
				output.writeDouble(a);
				output.flush();
				System.out.print("\nEnter the 2nd coeffiecent b= ");
				b = scan.nextDouble();
				output.writeDouble(b);
				output.flush();
				System.out.print("\nEnter the 3rd coeffiecent c= ");
				c = scan.nextDouble();
				output.writeDouble(c);
				output.flush();
				
				flag = input.readBoolean();

				if(flag == true)
				{
					root1 = input.readDouble();
					root2 = input.readDouble();
					System.out.println(root1);
					System.out.println(root2);
				}
				else
					
				System.out.println("\nNo roots founds");
				System.out.println("Solve for Quadratic Again Y/N: ");
				choice = scan.next().charAt(0);
				if(choice == 'Y' || choice == 'y')
				{
					repeat = true;
					System.out.println();
					output.writeObject("Begin");
					output.flush();
				}
				
				else
				{
					repeat = false;
					output.writeObject("TERMINATES");
					output.flush();
					input.close();
					output.close();
					client.close();
					System.out.println("Client shut down");
				}
			}
		}
		
		else if(choice == 'N' || choice == 'n')
		{
			output.writeObject("TERMINATES");
			output.flush();
			input.close();
			output.close();
			client.close();
			System.out.println("Client shut down");
		}

	}
}
