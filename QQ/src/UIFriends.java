import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class UIFriends extends JFrame{
	public UIFriends(){
		setTitle("好友");
		setSize(400,1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
			
		JPanel upPanel = new JPanel();
		JLabel QQ = new JLabel("QQ:",Label.LEFT);
		JLabel status = new JLabel("Status:",Label.LEFT);
		JLabel name = new JLabel("Name:",Label.LEFT);
		JTextField search = new JTextField(40);
		String[] args={"apple","pear","orange","banana"};
		JList friends = new JList(args);
		JScrollPane listScroller = new JScrollPane(friends);
		listScroller.setPreferredSize(new Dimension(380, 600));

		upPanel.add(QQ);
		upPanel.add(status);
		upPanel.add(name);
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
		layout.setConstraints(QQ, s);
		s.gridwidth=GridBagConstraints.REMAINDER;
		layout.setConstraints(status, s);
		layout.setConstraints(name, s);
		layout.setConstraints(search, s);
	}
	public static void main(String[] args){
		UIFriends fr = new UIFriends();
		fr.setVisible(true);;
	}
}
