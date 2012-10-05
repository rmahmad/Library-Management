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


class Addcust extends JFrame
{
JTextField t1,t2,t3,t4,t5,t6;
JTextArea a1,a2,a3,a5,a4,a6,a7;
JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b10;
JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14; 
JPanel jp1,jp2,jp3,jp4,jp5,jp6;
 FileReader rd1;
 JTextField read1;
 FileWriter wr1;
String year="20";
Container cp;

Addcust(){
super("Add customer");

cp=getContentPane();
cp.setLayout(null);

jp1=new JPanel(null,true);

l1=new JLabel("Customer ID.");
l1.setBounds(10,10,150,25);
jp1.add(l1);

t1=new JTextField();
t1.setBounds(100,10,120,25);
jp1.add(t1);

l2=new JLabel("Customer Name");
l2.setBounds(10,40,150,25);
jp1.add(l2);

Date d=new Date();
int date=d.getDate();
int mon=d.getMonth();
mon=mon+1;
int y=d.getYear();

String str=y+"";
int len=str.length();

for(int i=0;i<len;i++)
{

if(i>0)
{
year=year+str.charAt(i);
}
}
String issue=date+"/"+mon+"/"+year;

t2=new JTextField();
t2.setBounds(100,40,120,25);
jp1.add(t2);


l3=new JLabel("Registration Date");
l3.setBounds(10,70,150,25);
jp1.add(l3);

t3=new JTextField(issue);
t3.setEditable(false);
t3.setBounds(100,70,120,25);
jp1.add(t3);



l4=new JLabel("Book Name");
l4.setBounds(10,100,150,25);
jp1.add(l4);

t4=new JTextField();
t4.setEditable(false);
t4.setBounds(100,100,120,25);
jp1.add(t4);

l5=new JLabel("Date Of Purcase");
l5.setBounds(10,130,150,25);
jp1.add(l5);

t5=new JTextField();
t5.setEditable(false);
t5.setBounds(100,130,120,25);
jp1.add(t5);

l6=new JLabel("Date Of Return");
l6.setBounds(10,160,150,25);
jp1.add(l6);

t6=new JTextField();
t6.setEditable(false);
t6.setBounds(100,160,120,25);
jp1.add(t6);


l7=new JLabel("Details");
l7.setBounds(10,190,150,25);
jp1.add(l7);

a1=new JTextArea();
a1.setBounds(10,215,200,70);
jp1.add(a1);


b1=new JButton("Register");
b1.setBounds(10,310,90,20);
jp1.add(b1);

b2=new JButton("Reset");
b2.setBounds(120,310,90,20);
jp1.add(b2);

b3=new JButton("Cancel");
b3.setBounds(230,310,90,20);
jp1.add(b3);

	
jp1.setBounds(10,10,350,350);
cp.add(jp1);

b2.addActionListener(new ActionListener()
{
public void actionPerformed(ActionEvent e)
{
t1.setText("");
t6.setText("");
t4.setText("");
t2.setText("");
a1.setText("");
t5.setText("");
}
});


b3.addActionListener(new ActionListener()
{
public void actionPerformed(ActionEvent e)
{
setVisible(false);
}
});


b1.addActionListener(new ActionListener()
{
public void actionPerformed(ActionEvent e)
{
try{
File file=new File("Cust_Details");
if(file.isFile() && (!file.exists()))
{
file.mkdir();
}

File file2=new File("Cust_Details/pointer.mmm");
if(file.isFile() && (!file.exists()))
{
FileWriter w1=new FileWriter("Cust_Details/pointer.mmm");
w1.write(1+"");
w1.close();
}

rd1=new FileReader("Cust_Details/pointer.mmm");
read1=new JTextField();
read1.read(rd1,null);
rd1.close();

int z=Integer.parseInt(read1.getText());

wr1=new FileWriter("Cust_Details/Cus"+z+".id");
wr1.write(t1.getText()+"");
wr1.close();

wr1=new FileWriter("Cust_Details/Cus"+z+".name");
wr1.write(t2.getText()+"");
wr1.close();

wr1=new FileWriter("Cust_Details/Cus"+z+".date");
wr1.write(t3.getText()+"");
wr1.close();

wr1=new FileWriter("Cust_Details/Cus"+z+".bname");
wr1.write(t4.getText()+"");
wr1.close();

wr1=new FileWriter("Cust_Details/Cus"+z+".purchase");
wr1.write(t5.getText()+"");
wr1.close();

wr1=new FileWriter("Cust_Details/Cus"+z+".return");
wr1.write(t6.getText()+"");
wr1.close();

wr1=new FileWriter("Cust_Details/Cus"+z+".detail");
wr1.write(a1.getText()+"");
wr1.close();

z=z+1;

wr1=new FileWriter("Cust_Details/pointer.mmm");
wr1.write(z+"");
wr1.close();

setVisible(false);


}catch(Exception ge){System.out.println(ge);}
}
});


}
}