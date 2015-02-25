import java.io.*;
import java.util.*;
import java.net.*;
public class Server {
	public static void main(String [] args){
		try{
			ServerSocket s = new ServerSocket(8888);
			Socket incoming = s.accept();
			try{
				InputStream inStream = incoming.getInputStream();
				OutputStream outStream = incoming.getOutputStream();
				Scanner in = new Scanner(inStream);
				PrintWriter out = new PrintWriter(outStream,true);
				out.println("Server connected");
				while(in.hasNextLine()){
					String test = in.nextLine();
					System.out.println(test);
					if(test.matches("BYE"))
					{
						out.println("BYE BYE");
						break;
						}
					
				}
				
				System.out.println("Sever going to close");
			}
			finally{
				incoming.close();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
