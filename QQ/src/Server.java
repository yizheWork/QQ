import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import com.mysql.jdbc.PreparedStatement;
/**
 *  Msg类设计：
 
	发送者（int），接受者（int），消息类型（int），消息体（string）
	消息类型有：更改状态消息=0，消息体：on[密码]，off，hidden[密码]
				 好友聊天信息=1，消息体：string
				 服务器推送的状态变更信息=2，消息体：QQ+状态
				 查找=3，消息体：QQ
				 添加好友=4，消息体：QQ
				 删除好友=5，消息体：QQ
				 断开连接=6，消息体：null
	接受者=0 代表服务器；
 * @author work
 *
 */


public class Server {
	private static final int THREADPOOLSIZE = 2; 
	static Statement stmt;
	static Connection conn;
	public static void main(String[] args)throws IOException{  
        final ServerSocket server = new ServerSocket(8888);  	          
        for(int i=0;i<THREADPOOLSIZE;i++){  
            Thread thread = new Thread(){  
                public void run(){  
                    while(true){  
                        try {  
                            Socket client = server.accept();  
                            System.out.println("收到链接");  
                            Server.execute(client);  
                        } catch (IOException e) {  
                            e.printStackTrace();  
                        }  
                    }   
                }  
            }; 
            thread.start();  
        }  
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("成功加载MySQL驱动！");
        }catch(ClassNotFoundException e1){
            System.out.println("找不到MySQL驱动!");
            e1.printStackTrace();
        }       
        String url="jdbc:mysql://localhost:3306/mysql";     
        try {
            conn = DriverManager.getConnection(url,    "root","workhard");
             stmt= conn.createStatement(); 
            System.out.print("成功连接到数据库！");
            stmt.close();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
	
	private static String dbGet(int target,String type) {
		String sql,info,pass,sta,name;
		switch(type){
		case "info": sql = "select * from User where QQ ="+target; 
					try {
							ResultSet rs = stmt.executeQuery(sql);
							pass = rs.getString(6);
							sta = rs.getString(3);
							name = rs.getString(2);
							info = target+name+sta;
							rs.close();
							return info;
						}
					catch (SQLException e) {
						e.printStackTrace();
					}
		case "pass": sql = "select pass from user where QQ ="+target;
						try {
							ResultSet rs = stmt.executeQuery(sql);
							pass = rs.getString(6);
							rs.close();
							return pass;
						} catch (SQLException e) {	
							e.printStackTrace();
						}
		case "sta": sql = "select sta from User where QQ ="+target;
						try {
							ResultSet rs = stmt.executeQuery(sql);
							sta = rs.getString(3);
							rs.close();
							return sta;
						} catch (SQLException e) {				
							e.printStackTrace();
						}
		default:return null;
		}
	}
	private static InetAddress getIp(int receiver){
		try {
			return dbGet("IP",receiver);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static InetAddress dbGet(String string, int content) throws UnknownHostException {
		String sql,IP;
		InetAddress ip;
		try {
			sql = "select IP from User where QQ="+content;
			ResultSet rs = stmt.executeQuery(sql);
			IP = rs.getString(4);
			ip=InetAddress.getByName(IP);
			rs.close();
			return ip;
		} catch (SQLException e) {				
			e.printStackTrace();
		}
		return null;
	}
	private static int dbSet(int target, String type, String content) {
		String sql;
		PreparedStatement pst;
		switch(type){
		case "sta": 
			 sql = "update User set sta=? where QQ=?";
			try {
				pst = conn.prepareStatement(sql);
				pst.setString(1,content);
		      pst.setInt(2,target);
		      pst.executeUpdate();
		      pst.close();
		      if(pst.isCloseOnCompletion())
		    	  return 0;
		      else 
		    	  return -1;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		case "add":	
				try {
					sql = "select friends from User where QQ="+content;
					ResultSet rs = stmt.executeQuery(sql);
					String friends = rs.getString(4);
					rs.close();
					sql = "update User set friends=? where QQ=?";
					pst = conn.prepareStatement(sql);
					pst.setString(1,friends+"|"+content);
			      pst.setInt(2,target);
			      pst.executeUpdate();
			      pst.close();
			      if(pst.isCloseOnCompletion())
			    	  return 0;
			      else 
			    	  return -1;
				} catch (SQLException e) {
					e.printStackTrace();
				}
		case "delete":
			try {
				sql = "select friends from User where QQ="+target;
				ResultSet rs = stmt.executeQuery(sql);
				String friends = rs.getString(4);
				rs.close();
				sql = "update User set friends=? where QQ=?";
				pst = conn.prepareStatement(sql);
				friends.replace("|"+content, null);
				pst.setString(1,friends);
		      pst.setInt(2,target);
		      pst.executeUpdate();
		      pst.close();
		      if(pst.isCloseOnCompletion())
		    	  return 0;
		      else 
		    	  return -1;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			default :return -1;
			}
	}
	
	protected static void execute(Socket client) {
		ObjectInputStream ois;
		try {	
		ois = new ObjectInputStream(client.getInputStream());
		Msg inmsg = (Msg) ois.readObject();
		while(true){
			if(inmsg!=null){
			if(infIn(inmsg)==-1)
				System.out.println("Error in infIn");
			}
			if(inmsg.getType()==6){
				break;
			}
			inmsg = (Msg) ois.readObject();
		}
		ois.close();
		client.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static int infIn(Msg msg) {
		switch (msg.getType())
		{
			case 0:return changeSta(msg);
			case 3:return returnInfo(msg);
			case 4:return pushAdd(msg);
			case 5:return pushDelete(msg);
			default:return -1;
		}
	}
	
	private static int infOut(Msg msg)
	{
		InetAddress ip = getIp(msg.getReceiver());
		try {
			Socket s = new Socket(ip,8888);
			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			out.writeObject(msg);
			out.flush();
			out.close();
			s.close();
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		}
	


	private static int pushDelete(Msg msg) {
		if (infOut(new Msg(0,msg.getSender(),5,msg.getContent()))==-1)
			return -1;
		if (infOut(new Msg(0,Integer.parseInt(msg.getContent()),5,Integer.toString(msg.getSender())))==-1)
			return -1;
		if (dbSet(msg.getSender(),"delete",msg.getContent())==-1)
			return -1;
		if (dbSet(Integer.parseInt(msg.getContent()),"delete",Integer.toString(msg.getSender()))==-1)
			return -1;
		return 0;
	}
	

	
	private static int pushAdd(Msg msg) {
		if(msg.getContent().endsWith("Y")){
			if(infOut(new Msg(0,Integer.parseInt(msg.getContent().substring(0, -1)),4,"Y"))==-1)
				return -1;
			dbSet(msg.getSender(),"add",msg.getContent().substring(0, -1));
			dbSet(Integer.parseInt(msg.getContent().substring(0, -1)),"add",Integer.toString(msg.getSender()));
			}
		else if(msg.getContent().endsWith("N"))
			if(infOut(new Msg(0,Integer.parseInt(msg.getContent().substring(0, -1)),4,"N"))==-1)
				return -1;
		else if (infOut(new Msg(0,Integer.parseInt(msg.getContent()),4,Integer.toString(msg.getSender())))==-1)
			return -1;
		
		return 0;
	}
	
	private static int returnInfo(Msg msg) {
		String content = dbGet(Integer.parseInt(msg.getContent()),"Info");
		if(infOut(new Msg(0,msg.getSender(),3,content))==-1){
			return -1;
		}
		return 0;
	}
	
	private static int changeSta(Msg msg) {
		switch (msg.getContent())
		{
		case "on":if(dbGet(msg.getSender(),"sta")=="hidden")
						return login(msg.getSender());
					 else 
						return login(msg.getSender(),msg.getContent());
		case "off":return logout(msg.getSender());
		case "hidden":if(dbGet(msg.getSender(),"sta")=="on")
						  	return hidden(msg.getSender());
						  else 
							return hidden(msg.getSender(),msg.getContent());
		default:return -1;
		}
	}
	
	private static int hidden(int sender){
		if(dbSet(sender,"sta","hidden")==-1)
			return -1;
		return 0;
	}
	
	private static int hidden(int sender,String pass) {
		if(verify(sender,pass.substring(2, pass.length()))){
			if(dbSet(sender,"sta","hidden")==-1)
				return -1;
		if(infOut(new Msg(0,sender,0,"hidden"))==-1)
			return -1;
		}
		else if(infOut(new Msg(0,sender,0,"failure"))==-1)
				return -1;
		return 0;
	}
	
	private static int logout(int sender) {
		if(dbSet(sender,"sta","off")==-1)
			return -1;
		return 0;
	}
	
	private static int login(int sender) {
			if (dbSet(sender,"sta","on")==-1)
				return -1;
			return 0;
	}
	
	private static int login(int sender,String pass) {
		if(verify(sender,pass.substring(2, pass.length()))){
			if(dbSet(sender,"sta","on")==-1)
				return -1;
			if(infOut(new Msg(0,sender,0,"on"))==-1)
				return -1;
		}
		else if(infOut(new Msg(0,sender,0,"failure"))==-1)
			return -1;
		return 0;
	}
	
	private static boolean verify(int sender, String pass) {
		if(pass.equals(dbGet(sender,"pass")))
			return true;
		else 
			return false;
	}  
}
