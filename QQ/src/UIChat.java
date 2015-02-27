import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class UIChat extends JFrame{
	public UIChat(){
		setTitle("对话");
		setSize(500,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	   GridBagLayout layout = new GridBagLayout();  
		GridBagConstraints s= new GridBagConstraints();//定义一个GridBagConstraints，
		//upPanel.setLayout(layout);
		this.setLayout(layout);
		JLabel info = new JLabel("QQ:Name:");
		JTextArea show = new JTextArea(20,60);
		show.setEditable(false);
		JTextArea input = new JTextArea(4,60);
		JLabel blank = new JLabel();
		JButton send = new JButton("发送");
		JScrollPane listScroller = new JScrollPane(show);
		//listScroller.setPreferredSize(new Dimension(100,100));
		JScrollPane listScroller2 = new JScrollPane(input);
		//listScroller.setPreferredSize(new Dimension(150,100));
		this.add(info);
		this.add(blank);
		
		this.add(listScroller);
		this.add(listScroller2);
		//this.add(blank);
		this.add(send);

		s.weightx=1;
		s.gridheight=1;
		s.gridwidth=GridBagConstraints.REMAINDER;
		layout.setConstraints(blank, s);
		
		s.gridheight=1;
		s.gridwidth=3;
		layout.setConstraints(info, s);
		//layout.setConstraints(send, s);

		s.gridheight=20;
		s.gridwidth=GridBagConstraints.REMAINDER;
		layout.setConstraints(listScroller, s);
		s.gridheight=4;
		s.gridwidth=GridBagConstraints.REMAINDER;
		layout.setConstraints(listScroller2, s);
		layout.setConstraints(send, s);

	}
	public static void main(String[] args){
		UIChat chat = new UIChat();
		chat.setVisible(true);
	}
}
