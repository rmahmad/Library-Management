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


class cust_detail extends JFrame
{
JTextField t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,jt2,jt1;
JTextArea a1,a2,a3,a5,a4,a6,a7;
JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b10;
JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14; 
JPanel jp1,jp2,jp3,jp4,jp5,jp6;
 FileReader rd1,rd2;
 JTextField read1;
 FileWriter wr1;
int m=0,xx=0,mo3,mo1,visible=0;
String year="20",cus_name,book_name,year1="20",year3="20";
Container cp;

cust_detail(String strz){
super("Customer Details");


cus_name=strz;
cp=getContentPane();
cp.setLayout(null);

jp1=new JPanel(null,true);

l1=new JLabel("Customer ID");
l1.setBounds(10,10,100,25);
jp1.add(l1);

t1=new JTextField();
t1.setBounds(120,10,150,25);
jp1.add(t1);

l2=new JLabel("Customer Name");
l2.setBounds(10,40,100,25);
jp1.add(l2);

t2=new JTextField();
t2.setBounds(120,40,150,25);
jp1.add(t2);

l3=new JLabel("Registration Date");
l3.setBounds(10,70,100,25);
jp1.add(l3);

t3=new JTextField();
t3.setBounds(120,70,150,25);
jp1.add(t3);

l4=new JLabel("Last Book");
l4.setBounds(10,100,100,25);
jp1.add(l4);

t4=new JTextField();
t4.setBounds(120,100,150,25);
jp1.add(t4);

l5=new JLabel("Purchased Date");
l5.setBounds(10,130,100,25);
jp1.add(l5);

t5=new JTextField();
t5.setBounds(120,130,150,25);
jp1.add(t5);

l6=new JLabel("Returned Date");
l6.setBounds(10,160,100,25);
jp1.add(l6);

t6=new JTextField();
t6.setBounds(120,160,150,25);
jp1.add(t6);

l7=new JLabel("New Book Name");
l7.setBounds(10,190,100,25);
jp1.add(l7);

t7=new JTextField();
t7.setBounds(120,190,150,25);
jp1.add(t7);


l8=new JLabel("Purchase Date");
l8.setBounds(10,220,100,25);
jp1.add(l8);

t8=new JTextField();
t8.setBounds(120,220,150,25);
jp1.add(t8);

l9=new JLabel("Return Date");
l9.setBounds(10,250,100,25);
jp1.add(l9);

t9=new JTextField();
t9.setEditable(false);
t9.setBounds(120,250,150,25);
jp1.add(t9);


l10=new JLabel("All Purchased Book");
l10.setBounds(10,280,100,25);
jp1.add(l10);

int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

a1=new JTextArea();
a1.setEditable(false);
JScrollPane jsp=new JScrollPane(a1,v,h);
jsp.setBounds(120,280,170,190);
jp1.add(jsp);

b1=new JButton("Ok");
b1.setBounds(30,475,100,25);
jp1.add(b1);

b2=new JButton("Cancel");
b2.setBounds(140,475,100,25);
jp1.add(b2);

jp1.setBounds(0,0,300,550);
cp.add(jp1);
try{
 
	rd2=new FileReader("Cust_Details/pointer.mmm");
	jt2=new JTextField();
	jt2.read(rd2,null);
	rd2.close();			
	int no1=Integer.parseInt(jt2.getText());

	for(int h1=0;h1<no1;h++)
	{
	rd2=new FileReader("Cust_Details/Cus"+h1+".id");
	jt2=new JTextField();
	jt2.read(rd2,null);
	rd2.close();	
	if(strz.equals(jt2.getText()))
	{
	 visible=1;
	}
	else{
		setVisible(false);
		visible=0;
	    }	
	}
	if(visible==0)
	{
	JOptionPane.showMessageDialog((Component)null,strz+" such no Customer Found !  ","Library Management(Pravin Rane)",JOptionPane.OK_OPTION);
	}
}catch(Exception ht){}

try{

	rd2=new FileReader("Cust_Details/pointer.mmm");
	jt2=new JTextField();
	jt2.read(rd2,null);
	rd2.close();			
	int no=Integer.parseInt(jt2.getText());

	for(int z=1;z<no;z++)
	{
	rd2=new FileReader("Cust_Details/Cus"+z+".id");
	jt2=new JTextField();
	jt2.read(rd2,null);
	rd2.close();
	
	m=z;
	if(jt2.getText().equals(cus_name))
	{
		xx=m;
		rd1=new FileReader("Cust_Details/Cus"+xx+".id");
		jt1=new JTextField();
		jt1.read(rd1,null);
		t1.setText(jt1.getText()+"");
		t1.setEditable(false);
		rd1.close();
	
		
		rd1=new FileReader("Cust_Details/Cus"+xx+".name");
		jt1=new JTextField();
		jt1.read(rd1,null);
		t2.setText(jt1.getText()+"");
		t2.setEditable(false);
		rd1.close();

		
		rd1=new FileReader("Cust_Details/Cus"+xx+".date");
		jt1=new JTextField();
		jt1.read(rd1,null);
		t3.setText(jt1.getText()+"");
		t3.setEditable(false);
		rd1.close();

		
		rd1=new FileReader("Cust_Details/Cus"+xx+".bname");
		jt1=new JTextField();
		jt1.read(rd1,null);
		t4.setText(jt1.getText()+"");
		t4.setEditable(false);
		rd1.close();
		
		
		rd1=new FileReader("Cust_Details/Cus"+xx+".purchase");
		jt1=new JTextField();
		jt1.read(rd1,null);
		t5.setText(jt1.getText()+"");
		t5.setEditable(false);
		rd1.close();

		
		rd1=new FileReader("Cust_Details/Cus"+xx+".return");
		jt1=new JTextField();
		jt1.read(rd1,null);
		
		t6.setText(jt1.getText()+"");
		if(!t6.getText().equals("") )
		{
		t6.setEditable(false);
	
		Date d3=new Date();
		int date3=d3.getDate();
		int mon3=d3.getMonth();
		mo3=mo3+1;
		int y3=d3.getYear();
		String str3=y3+"";	
		int len3=str3.length();
		for(int i3=0;i3<len3;i3++)
			{
			if(i3>0)
				{
				 year3=year3+str3.charAt(i3);
				}
				}
				String issue3=date3+"/"+mo3+"/"+year3;
				t5.setText(issue3);
		
		}
		else if( !t4.getText().equals("") &&  !t5.getText().equals(""))
		{
		t7.setEditable(false);
		t8.setEditable(false);
		t9.setEditable(false);
		Date d1=new Date();
		int date1=d1.getDate();
		int mon1=d1.getMonth();
		mo1=mon1+1;
		int y1=d1.getYear();
		String str1=y1+"";	
		int len1=str1.length();
		for(int i1=0;i1<len1;i1++)
			{
			if(i1>0)
			{
				  year1=year1+str1.charAt(i1);
			}
			}
			String issue1=date1+"/"+mo1+"/"+year1;
			
			t6.setText(issue1);
		}
		else
		{
			t6.setEditable(false);
			t9.setEditable(false);

		}
	
		rd1.close();	

		rd1=new FileReader("Cust_Details/Cus"+xx+".detail");
		jt1=new JTextField();
		jt1.read(rd1,null);
		a1.setText("All Purchased Book"+"\n"+jt1.getText());
		rd1.close();
	}

	}

}catch(Exception de){System.out.println(de);}


	b1.addActionListener(new ActionListener()
	{
	public void actionPerformed(ActionEvent de)
	{
	
	try{

			if(!t7.getText().equals(""))
			{
			wr1=new FileWriter("Cust_Details/Cus"+xx+".bname");
	 		wr1.write(t7.getText()+"");
	 		wr1.close();
	
			
			

				if(!t7.getText().equals(""))
				{
					
				 wr1=new FileWriter("Cust_Details/Cus"+xx+".bname");
				 wr1.write(t7.getText()+"");
				 wr1.close();

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
				 System.out.println(issue);
				 wr1=new FileWriter("Cust_Details/Cus"+xx+".purchase");
				 wr1.write(issue);
				 wr1.close();
				
				
				wr1=new FileWriter("Cust_Details/Cus"+xx+".return");
			        wr1.write(t9.getText()+"");
				wr1.close();

			rd2=new FileReader("Database/pointer.mmm");
			jt2=new JTextField();
			jt2.read(rd2,null);
			int count=Integer.parseInt(jt2.getText());
			rd2.close();

				for(int i=1;i<count;i++)
					{	try{
						rd2=new FileReader("Database/"+i+".name");
						jt2=new JTextField();
						jt2.read(rd2,null);
						rd2.close();
	
						
								book_name=jt2.getText();
	
								if(book_name.toLowerCase().equals(t7.getText().toLowerCase()))
								{
								wr1=new FileWriter("Database/"+i+".id");
								wr1.write(t1.getText()+"");
								wr1.close();
								
								wr1=new FileWriter("Database/"+i+".issue");
								wr1.write(issue+"");
								wr1.close();
								
								wr1=new FileWriter("Database/"+i+".return");
								wr1.write("not return");
								wr1.close();
								
								}

							}catch(Exception dea){System.out.println(dea);}
					}
					}

					
	    				setVisible(false);	
	    			}
				else{
					try{
					if(!t6.getText().equals(""))
					 {
					 wr1=new FileWriter("Cust_Details/Cus"+xx+".return");
					 wr1.write(t6.getText()+"");
					 wr1.close();
						
					 FileReader rf=new FileReader("Cust_Details/Cus"+xx+".detail");
					 JTextField gh=new JTextField();
					 gh.read(rf,null);
					  rf.close();

					 wr1=new FileWriter("Cust_Details/Cus"+xx+".detail");
					 wr1.write(gh.getText()+"\n"+t4.getText()+"\t"+t5.getText()+"\t"+t6.getText());
					 wr1.close();   	

					rd2=new FileReader("Database/pointer.mmm");
					jt2=new JTextField();
					jt2.read(rd2,null);
					int count=Integer.parseInt(jt2.getText());
					rd2.close();

					for(int i=1;i<count;i++)
					{
						rd2=new FileReader("Database/"+i+".name");
						jt1=new JTextField();
						jt1.read(rd2,null);
						rd2.close();
	
						
								book_name=jt1.getText();
	
								if(book_name.toLowerCase().equals(t4.getText().toLowerCase()))
								{
								System.out.println("Book Was Return");
								wr1=new FileWriter("Database/"+i+".id");
								wr1.write(t1.getText()+"");
								wr1.close();
								
								wr1=new FileWriter("Database/"+i+".return");
								wr1.write(t6.getText()+"");
								wr1.close();
								
								}
								else if(jt1.getText().equals(t4.getText()))
								{
								wr1=new FileWriter("Database/"+i+".id");
								wr1.write(t1.getText()+"");
								wr1.close();
								
								wr1=new FileWriter("Database/"+i+".return");
								wr1.write(t6.getText()+"");
								wr1.close();
							
								}
	
					}	
					
					 }
					else
					{
					JOptionPane.showMessageDialog((Component)null,"Please Enter the Return Date ","Library Management(Pravin Rane)",JOptionPane.OK_OPTION);
					}
					}catch(Exception deb){System.out.println(deb);}
				    }	
		setVisible(false);
	    }catch(Exception jr){ System.out.println(jr);}
	
	}
	});

	b2.addActionListener(new ActionListener()
	{
	public void actionPerformed(ActionEvent de)
	{
		setVisible(false);
	}
	});

}
}