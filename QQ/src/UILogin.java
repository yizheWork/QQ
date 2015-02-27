import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.*;

public class UILogin extends JFrame{
	Font f =new Font("宋体",Font.PLAIN,12);//字体
	public UILogin(){
	setTitle("登录");
   setSize(400,280);
   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   this.setVisible(true);
	JTextField numJTF = new JTextField(10);
	JPasswordField passJPF = new JPasswordField(50);
	JButton loginJB = new JButton("登陆");
	String[] status = new String[]{"在线","隐身","离线"};
   JComboBox staJCB = new JComboBox(status);
   JLabel blankJL1 = new JLabel();
   JLabel blankJL2 = new JLabel();
   JLabel blankJL3 = new JLabel();
   JLabel blankJL4 = new JLabel();
   JLabel blankJL5 = new JLabel();
   JLabel blankJL6 = new JLabel();
   JLabel blankJL7 = new JLabel();
   JLabel blankJL8 = new JLabel();
   JLabel blankJL9 = new JLabel();
   JLabel blankJL10 = new JLabel();
   JLabel blankJL11 = new JLabel();
   JLabel blankJL12 = new JLabel();
   JLabel blankJL13 = new JLabel();

   this.add(blankJL1);  
   this.add(blankJL2);
   this.add(blankJL3);
   this.add(numJTF);
   this.add(blankJL4);
   this.add(blankJL5);
   this.add(blankJL6);
   this.add(passJPF);
   this.add(blankJL7);
   this.add(blankJL8);
   this.add(blankJL9);
   this.add(loginJB);
   this.add(blankJL10);
   this.add(staJCB);
   this.add(blankJL11);
   this.add(blankJL12);
   this.add(blankJL13);
   
   GridBagLayout layout = new GridBagLayout();  
   GridBagConstraints s= new GridBagConstraints();//定义一个GridBagConstraints，
   this.setLayout(layout);
   //是用来控制添加进的组件的显示位置  
   s.fill = GridBagConstraints.BOTH;  
   //该方法是为了设置如果组件所在的区域比组件本身要大时的显示情况  
   //NONE：不调整组件大小。  
   //HORIZONTAL：加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。  
   //VERTICAL：加高组件，使它在垂直方向上填满其显示区域，但是不改变宽度。  
   //BOTH：使组件完全填满其显示区域。 
   s.weightx=1;
   s.weighty=1;
   s.gridwidth=1;
   s.gridheight=7;
   layout.setConstraints(blankJL1, s);
   
   s.gridheight=1;
   s.gridwidth=5;
   layout.setConstraints(blankJL2, s);
   layout.setConstraints(blankJL5, s);
   layout.setConstraints(blankJL8, s);
   layout.setConstraints(blankJL12, s);
   layout.setConstraints(numJTF, s);//设置组件
   layout.setConstraints(passJPF, s);
   
   s.gridheight=1;
   s.gridwidth = GridBagConstraints.REMAINDER;
   layout.setConstraints(blankJL3, s);
   layout.setConstraints(blankJL4, s);
   layout.setConstraints(blankJL6, s);
   layout.setConstraints(blankJL7, s);
   layout.setConstraints(blankJL9, s);
   layout.setConstraints(blankJL11, s);
   layout.setConstraints(blankJL13, s);
   
   s.gridwidth=2;
   s.gridheight=1;
   layout.setConstraints(loginJB, s);
   layout.setConstraints(staJCB, s);
   
   s.gridheight=1;
   s.gridwidth=1;
   layout.setConstraints(blankJL10, s);
   
	}
	public static void main(String[] args){
		UILogin uil = new UILogin();
		uil.setVisible(true);
		//ui.show();
	}
}