import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client {
	public static void main(String[] args){
		Socket s;
		try{
			s = new Socket("localhost",8888);
			Scanner in = new Scanner(s.getInputStream());
			PrintWriter out = new PrintWriter(s.getOutputStream());
			Scanner words = new Scanner(System.in);
			String next = words.nextLine();
			while(!next.matches("quit")){
				out.println(next);
				out.flush();
				words = new Scanner(System.in);
				next = words.nextLine();
			}
			out.flush();
			while(in.hasNextLine()){
				String test = in.nextLine();
				System.out.println(test);
				if(test.matches("BYE BYE"))
				{
					out.println("closed");
					break;
					}
			}
			s.close();
			System.out.println("Client going to close");
			
		}
		
		catch(IOException e){
			e.printStackTrace();
		}
		
	}

}
