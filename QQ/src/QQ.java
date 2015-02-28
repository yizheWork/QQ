import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
* 属性：name(String)姓名、number(int)QQ号码、status(0在线、1隐身、2离线)状态、
*friends（String,int,int,IP）(姓名，QQ，状态，IP地址)好友
*方法：infIn(String)接受消息、infOut(String)发送消息、InfCenter(String)处理消息
*		changeStatus(int,int)变更状态-》{login(int)登陆、quit(int)注销}
*		add(int)添加好友、delete(int)删除好友
*
*   Msg类设计：
 
	发送者（int），接受者（int），消息类型（int），消息体（string）
	消息类型有：更改状态消息=0，消息体：on，off，hidden
				 好友聊天信息=1，消息体：string
				 服务器推送的状态变更信息=2，消息体：QQ+状态
				 查找=3，消息体：QQ
				 添加好友=4，消息体：QQ
				 删除好友=5，消息体：QQ
				 断开连接=6，消息体：null
	接受者=0 代表服务器；
*@author work
*
*/
public class QQ {
	private static String name;
	private static int number;
	private static int status;
	private static Friends []friends = new Friends[300];
	private static final int THREADPOOLSIZE = 2;
	static int friNum=0;
	 static UILogin login;
	 static UIFriends UIfr;
	public QQ(String name, int number, int status, Friends[] friends) {
		this.name = name;
		this.number = number;
		this.status = status;
		this.friends = friends;
	}
	
	public static void main(String[] args)throws IOException{  
	        final ServerSocket server = new ServerSocket(6666);  	          
	        for(int i=0;i<THREADPOOLSIZE;i++){  
	            Thread thread = new Thread(){  
	                public void run(){  
	                     
	                    while(true){  
	                        try {  
	                            Socket client = server.accept();  
	                            System.out.println("收到链接");  
	                            QQ.execute(client);  
	                        } catch (IOException e) {  
	                            e.printStackTrace();  
	                        }  
	                    }   
	                }  
	            }; 
	            thread.start();  
	        }  
	       login = new UILogin();
	       login.setVisible(true);
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
				break;
				}
				if(inmsg.getType()==6){
					break;
				}
				if(ois.readByte()!=0)
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

	static int infIn(Msg msg)
	{
		switch (msg.getType())
		{
			case 0:return showSta(msg);
			case 1:return showMsg(msg);
			case 2:return showSta(msg);
			case 4:return showAdd(msg);
			case 5:return showDelete(msg);
			default:return -1;
		}
	}
	
	static int infOut(Msg msg)
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
	private static InetAddress getIp(int receiver) {
		if(receiver == 0)
			try {
				return InetAddress.getByName("localhost");
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}

	//private int infCenter(Msg msg){return 0;}
	private int changeStatus(String sta){
		if(infOut(new Msg(number,0,0,sta))==0)
			return 0;
		else
			return -1;
		}
	private int add(int num){
		if(infOut(new Msg(number,0,4,Integer.toString(num)))==0)
			return 0;
		else
			return -1;
		}
	private int delete(int num){
		if(infOut(new Msg(number,0,5,Integer.toString(num)))==0)
			return 0;
		else
			return -1;
		}
	private static int showSta(Msg msg) {
		// TODO Auto-generated method stub
		if(msg.getType()==0){
		System.out.println("登陆状态："+msg.getContent());
		if(msg.getContent().matches("on")){
			login.close();
			QQ.number=msg.getReceiver();
		}
		}
		else{
			System.out.println(msg.getContent());
			if(msg.getContent().compareTo("END")==0){
				UIfr = new UIFriends(QQ.getName(),QQ.getNumber(),(QQ.getStatus()==0?"on":"hidden"),friends);
				UIfr.setVisible(true);
				
			}else{
			String []content = msg.getContent().split("\\|");
			try {
				Friends temp = new Friends(content[1],Integer.parseInt(content[0]),(content[2].equals("on")?0:2),InetAddress.getByName(content[3].substring(1, content[3].length())));
				QQ.addFriends(temp);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			
		}
			}
		return 0;
		
	}
	private static void addFriends(Friends fri){
		//Friends[] old = QQ.getFriends();
		//int i=0;
		friends[friNum]=fri;
		//UIfr.add(fri);
		//UIfr.repaint();
		friNum++;
		
		//QQ.setFriends(old);
		//return old;
	}
	private static int showDelete(Msg msg) {
		// TODO Auto-generated method stub
		return 0;
	}
	private static int showAdd(Msg msg) {
		// TODO Auto-generated method stub
		return 0;
	}
	private static int showMsg(Msg msg) {
		// TODO Auto-generated method stub
		return 0;
	}

	protected static String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected static int getNumber() {
		return number;
	}

	protected void setNumber(int number) {
		this.number = number;
	}

	protected static int getStatus() {
		return status;
	}

	protected void setStatus(int status) {
		this.status = status;
	}

	protected static Friends[] getFriends() {
		return friends;
	}

	protected static void setFriends(Friends[] friends) {
		QQ.friends = friends;
	}
}