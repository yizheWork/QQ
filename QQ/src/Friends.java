import java.net.InetAddress;


public class Friends {
	private String name;
	private int number;
	private int status;
	private InetAddress ip;
	private static int length;
	
	public Friends(String name, int number, int status, InetAddress ip) {
		this.name = name;
		this.number = number;
		this.status = status;
		this.ip = ip;
	}
	
	protected static int getLength() {
		return length;
	}

	protected static void setLength(int length) {
		Friends.length = length;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public InetAddress getIp() {
		return ip;
	}
	public void setIp(InetAddress ip) {
		this.ip = ip;
	}
}
