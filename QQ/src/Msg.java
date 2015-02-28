import java.io.Serializable;

/**
   Msg类设计：
 
	发送者（int），接受者（int），消息类型（int），消息体（string）
	消息类型有：更改状态消息=0，消息体：on，off，hidden
				 好友聊天信息=1，消息体：string
				 服务器推送的状态变更信息=2，消息体：QQ+状态
				 查找=3，消息体：QQ
				 添加好友=4，消息体：QQ
				 删除好友=5，消息体：QQ
	接受者=0 代表服务器；
**/
public class Msg implements Serializable{
	private int sender;
	private int receiver;
	private int type;
	private String content;
	
	public Msg(int sender,int receiver,int type,String content){
		this.sender = sender;
		this.receiver = receiver;
		this.type = type;
		this.content = content;
	}
	public String toString(){
		return sender+receiver+type+content;
	}
	public int getSender() {
		return sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}

	public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
