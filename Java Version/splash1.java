import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.applet.AppletContext.*;
import java.util.*;
import javax.swing.event.*;
import java.lang.reflect.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;
import  java.awt.image.ImageConsumer ;
import java.awt.image.ImageObserver; 
import   java.applet.AudioClip;
import javax.swing.filechooser.FileView;
import java.net.*;


class splash1 extends JFrame
{
JTextField t1,t2,t3,t4,t5,t6;
JTextArea a1,a2,a3,a5,a4,a6,a7;
JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b10;
JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14; 
JPanel jp1,jp2,jp3,jp4,jp5,jp6;
 FileReader rd1;
 JTextField read1;
 FileWriter wr1;
JPasswordField ps;	
Container cp;

splash1(){
super("Login ");


cp=getContentPane();
cp.setLayout(null);

jp1=new JPanel(null,true);
JLabel l1=new JLabel("User Name");
l1.setForeground(Color.black);
l1.setBounds(10,10,120,25);
jp1.add(l1);

t1=new JTextField();
t1.setToolTipText("User Name : admin");
t1.setEditable(true);
t1.setBounds(100,10,120,25);
jp1.add(t1);

l2=new JLabel("Password");
l2.setForeground(Color.black);
l2.setBounds(10,50,120,25);
jp1.add(l2);

 ps=new JPasswordField();
ps.setToolTipText("Password : admin");
ps.setBounds(100,50,120,25);
jp1.add(ps);

l3=new JLabel();
l3.setBounds(15,125,260,25);
jp1.add(l3);

b1=new JButton("Ok");
b1.setBounds(30,90,100,25);
jp1.add(b1);

b2=new JButton("cancel");
b2.setBounds(150,90,100,25);
jp1.add(b2);

Color r3=new Color(122,145,201);
//jp1.setBackground(r3);
jp1.setBounds(0,0,260,260);
cp.add(jp1);

	b1.addActionListener(new ActionListener()
	{
	public void actionPerformed(ActionEvent e)
	{
	if(t1.getText().equals("admin") && ps.getText().equals("admin"))
	{
	Library lb=new Library();
	//splash1 s=new splash1();
	setVisible(false);
	lb.setVisible(true);
	lb.setSize(800,600);	

	}
	else{
	l3.setText("Please Check Username and Password");
	l3.setForeground(Color.red);
	}

	}
	});

	b2.addActionListener(new ActionListener()
	{
	public void actionPerformed(ActionEvent e)
	{
	try{
	System.exit(0);
	}catch(Exception ftr){}
	}
	});
}
}
