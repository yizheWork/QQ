import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class UIFriends extends JFrame{
	private String name;
	private int num;
	private String sta;
	private Friends[] friends = new Friends[300];
	private static int friNum=0;
	private JList friendsJL;
	String[] args = new String[300];
	/**public UIFriends(String name,int num,String sta,Friends[] friends){
		this.name = name;
		this.num = num;
		this.sta = sta;
		this.friends=friends;
		new UIFriends();
	}**/
	/*public void add(Friends fri){
		friends[friNum]=fri;
		 args= new String[300];
		for(int i=0;i<friNum;i++){
			args[i]=friends[i].getName()+" "+friends[i].getNumber();
		}
		for(int i=0;i<friNum;i++){
			args[i]=friends[i].getName()+" "+friends[i].getNumber();
		}
		//String[] args={"apple","pear","orange","banana"};
		 friendsJL = new JList(args);
	}*/
	public UIFriends(String name,int num,String sta,Friends [] fri){
		this.name = name;
		this.num = num;
		this.sta = sta;
		try {
			friends[0]=new Friends("friends",8888,0,InetAddress.getByName("127.0.0.1"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//this.friends=friends;
		setTitle("好友");
		setSize(400,1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
			
		JPanel upPanel = new JPanel();
		JLabel qq = new JLabel("QQ:"+num,Label.LEFT);
		JLabel status = new JLabel("Status:"+sta,Label.LEFT);
		JLabel nameJL = new JLabel("Name:"+name,Label.LEFT);
		JTextField search = new JTextField(40);
		for(int i=0;i<QQ.friNum;i++){
			friends[i]=fri[i];
			friNum++;
			System.out.println("Name:"+friends[i].getName());
			System.out.println("Sta:"+friends[i].getStatus());
			System.out.println("Num:"+friends[i].getNumber());
			args[i]=friends[i].getName()+" "+friends[i].getStatus()+" "+friends[i].getNumber();
		}
		//String[] args={"apple","pear","orange","banana"};
		 friendsJL = new JList(args);
		
		JScrollPane listScroller = new JScrollPane(friendsJL);
		listScroller.setPreferredSize(new Dimension(380, 600));

		upPanel.add(qq);
		upPanel.add(status);
		upPanel.add(nameJL);
		upPanel.add(search);
		upPanel.setVisible(true);
		
		this.add(upPanel);
		this.add(listScroller);
      GridBagLayout layout = new GridBagLayout();  
		GridBagConstraints s= new GridBagConstraints();//定义一个GridBagConstraints，
		upPanel.setLayout(layout);
		this.setLayout(layout);
		s.weighty=1;
		s.gridheight=10;
		s.gridwidth=1;
		s.gridheight=1;
		s.gridwidth=GridBagConstraints.REMAINDER;
		layout.setConstraints(upPanel, s);
		
		
		s.weightx=1;
		s.weighty=0.5;
		s.gridheight=1;
		s.gridwidth=1;
		layout.setConstraints(qq, s);
		s.gridwidth=GridBagConstraints.REMAINDER;
		layout.setConstraints(status, s);
		layout.setConstraints(nameJL, s);
		layout.setConstraints(search, s);
		final JMenuItem chat = new JMenuItem();
		final JMenuItem delete = new JMenuItem();
		//final String tempName = null;
		chat.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String name = friendsJL.getSelectedValue().toString();
				int num=Integer.parseInt(name.substring(name.lastIndexOf(" ")+1,name.length()));
				QQ.infIn(new Msg(num,QQ.getNumber(),1,name.substring(0, name.indexOf(" "))));
				
			}
			
		});
		delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String name = friendsJL.getSelectedValue().toString();
				int num=Integer.parseInt(name.substring(name.lastIndexOf(" ")+1,name.length()));
				QQ.infIn(new Msg(num,QQ.getNumber(),5,""+num));
			}
			
		});
		friendsJL.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2)
				   {
				      String name = friendsJL.getSelectedValue().toString();
				      int num=Integer.parseInt(name.substring(name.lastIndexOf(" ")+1,name.length()));
				      QQ.infIn(new Msg(num,QQ.getNumber(),1,name.substring(0, name.indexOf(" "))));
				   }
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent evt) {
				JPopupMenu menu = new JPopupMenu();
				String name=null;
				if(friendsJL.getSelectedValue() == null)
					return;
				 name= friendsJL.getSelectedValue().toString();				
			   int num=Integer.parseInt(name.substring(name.lastIndexOf(" ")+1,name.length()));
				chat.setText("发起会话");
				delete.setText("删除好友");
				menu.add(chat);
				menu.add(delete);
				if(evt.getButton() == 3 && friendsJL.getSelectedValue()!=null)
                 menu.show(friendsJL,evt.getX(),evt.getY());
			
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
