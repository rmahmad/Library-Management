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


class addBook extends JFrame
{
JTextField t1,t2,t3,t4,t5,t6;
JTextArea a1,a2,a3,a5,a4,a6,a7;
JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b10;
JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14; 
JPanel jp1,jp2,jp3,jp4,jp5,jp6;
 FileReader rd1;
 JTextField read1;
 FileWriter wr1;
Container cp;


addBook(){
	super("Add Book To Library");
	try{
 	UIManager.setLookAndFeel ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	}catch(Exception de){} 
	
	setVisible(true);
	setSize(350,360);
	setLocation(150,130);
	cp=getContentPane();
	cp.setLayout(null);

	jp1=new JPanel(null,true);
	l1=new JLabel("Book Name");
	l1.setBounds(05,05,100,25);
	jp1.add(l1);

	t1=new JTextField();
	t1.setBounds(110,05,160,25);
	jp1.add(t1);

	l2=new JLabel("Book Author");
	l2.setBounds(05,40,100,25);
	jp1.add(l2);

	t2=new JTextField();
	t2.setBounds(110,40,160,25);
	jp1.add(t2);

	l3=new JLabel("Book Publication");
	l3.setBounds(05,75,100,25);
	jp1.add(l3);

	t3=new JTextField();
	t3.setBounds(110,75,160,25);
	jp1.add(t3);

	l4=new JLabel("Book Issue Date");
	l4.setBounds(05,110,100,25);
	jp1.add(l4);

	t4=new JTextField();
	t4.setBounds(110,110,160,25);
	jp1.add(t4);

	l5=new JLabel("Books Details");
	l5.setBounds(05,145,120,25);
	jp1.add(l5);

	a1=new JTextArea();
	a1.setBounds(110,145,160,70);
	jp1.add(a1);

	b1=new JButton("Save");
	b1.setBounds(15,230,90,25);
	jp1.add(b1);
	
	b2=new JButton("Reset");
	b2.setBounds(115,230,90,25);
	jp1.add(b2);
	
	b3=new JButton("Cancel");
	b3.setBounds(215,230,90,25);
	jp1.add(b3);

	Color r3=new Color(122,145,195);
	jp1.setBackground(r3);
	jp1.setBounds(10,20,310,300);
	cp.add(jp1);
	
	b1.addActionListener(new ActionListener()
	{
	public void actionPerformed(ActionEvent e)
	{

	try{
		rd1=new FileReader("Database/pointer.mmm");
		read1=new JTextField();
		read1.read(rd1,null);
		int count2=Integer.parseInt(read1.getText());
		rd1.close();
		
		wr1=new FileWriter("Database/"+count2+".name");
		wr1.write(t1.getText()+"");
		wr1.close();
		
		wr1=new FileWriter("Database/"+count2+".author");
		wr1.write(t2.getText()+"");
		wr1.close();

		wr1=new FileWriter("Database/"+count2+".publication");
		wr1.write(t3.getText()+"");
		wr1.close();

		wr1=new FileWriter("Database/"+count2+".issue");
		wr1.write(t4.getText()+"");
		wr1.close();
	
		wr1=new FileWriter("Database/"+count2+".return");
		wr1.write("00/00/00");
		wr1.close();

		wr1=new FileWriter("Database/"+count2+".detail");
		wr1.write(a1.getText()+"");
		wr1.close();

		wr1=new FileWriter("Database/"+count2+".id");
		wr1.write("0000");
		wr1.close();
		
		count2=count2+1;
		
		wr1=new FileWriter("Database/pointer.mmm");
		wr1.write(count2+"");
		wr1.close();

		setVisible(false);
	   }catch(Exception gr){}	
	}
	});

		
	b2.addActionListener(new ActionListener()
	{
	public void actionPerformed(ActionEvent e)
	{
	t1.setText("");
	t2.setText("");
	t3.setText("");
	t4.setText("");
	a1.setText("");
	}
	});

	b3.addActionListener(new ActionListener()
	{
	public void actionPerformed(ActionEvent e)
	{
	   setVisible(false);
	}
	});

	}
}