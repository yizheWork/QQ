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
	接受者=0 代表服务器；
*@author work
*
*/
public class QQ {
	private String name;
	private int number;
	private int status;
	private Friends []friends;
	
	public QQ(String name, int number, int status, Friends[] friends) {
		this.name = name;
		this.number = number;
		this.status = status;
		this.friends = friends;
	}
	
	public static void main(String[] args){
		
	}
	
	private int infIn(Msg msg)
	{
		switch (msg.getType())
		{
			case 1:return showMsg(msg);
			case 2:return showSta(msg);
			case 4:return showAdd(msg);
			case 5:return showDelete(msg);
			default:return -1;
		}
	}
	
	private int infOut(Msg msg)
	{
		return 0;
		}
	private int infCenter(Msg msg){return 0;}
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
	private int showSta(Msg msg) {
		// TODO Auto-generated method stub
		return 0;
		
	}
	private int showDelete(Msg msg) {
		// TODO Auto-generated method stub
		return 0;
	}
	private int showAdd(Msg msg) {
		// TODO Auto-generated method stub
		return 0;
	}
	private int showMsg(Msg msg) {
		// TODO Auto-generated method stub
		return 0;
	}
}