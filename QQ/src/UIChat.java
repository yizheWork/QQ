import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class UIChat extends JFrame{
	final JTextArea show;
	final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	private int num;
	private String name;
	public UIChat(){
		setTitle("对话");
		setSize(500,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	   GridBagLayout layout = new GridBagLayout();  
		GridBagConstraints s= new GridBagConstraints();
		this.setLayout(layout);
		JLabel info = new JLabel(name+"("+num+")");
		show= new JTextArea(20,60);
		show.setEditable(false);
		final JTextArea input = new JTextArea(4,60);
		JLabel blank = new JLabel();
		JButton send = new JButton("发送");
		JScrollPane listScroller = new JScrollPane(show);
		JScrollPane listScroller2 = new JScrollPane(input);
		this.add(info);
		this.add(blank);
		this.add(listScroller);
		this.add(listScroller2);
		this.add(send);

		s.weightx=1;
		s.gridheight=1;
		s.gridwidth=GridBagConstraints.REMAINDER;
		layout.setConstraints(blank, s);
		
		s.gridheight=1;
		s.gridwidth=3;
		layout.setConstraints(info, s);

		s.gridheight=20;
		s.gridwidth=GridBagConstraints.REMAINDER;
		layout.setConstraints(listScroller, s);
		s.gridheight=4;
		s.gridwidth=GridBagConstraints.REMAINDER;
		layout.setConstraints(listScroller2, s);
		layout.setConstraints(send, s);
		
		input.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
					QQ.infOut(new Msg(QQ.getNumber(),num,1,input.getText()));
					show.append(QQ.getName()+" "+df.format(new Date())+"\n"+input.getText()+"\n");
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
					input.setText(null);
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}
			
		});
		
		send.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				QQ.infOut(new Msg(QQ.getNumber(),num,1,input.getText()));
				show.append(QQ.getName()+" "+df.format(new Date())+"\n"+input.getText()+"\n");
				input.setText(null);
			}
			
		});
	}
	public void ear(Msg msg){
		show.append(msg.getSender()+" "+df.format(new Date())+"\n"+msg.getContent()+"\n");
	}
	public void UIChat(int num,String name){
		this.name = name;
		this.num = num;
	}
	public static void main(String[] args){
		UIChat chat = new UIChat();
		chat.setVisible(true);
	}
}
