//The following program is the last thing written for Tenafly High School by Alin Cosmanescu '01
//It is a program to help the graduation committe arrange the seating for (you guessed it) 
//graduation. Among Alin's other works are various versions of the Olympics Program (written in
//conjuction with Matt Calhoun '00, Kenny Jones '00, and Andrew Paolino '02), and The Gold-T Database.
//Hopefully this last effort will be the best of all of them, Alin is relativly pleased with the effort, 
//although he sees room for improvement (specifically efficiency and some of the graphics code, 
//either Java or Alin is screwy probably both).

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.lang.reflect.*;
import java.text.*;
import java.awt.print.*;

//Whats really on my mind? nor school nor work or play:
//"Theres only four things running through my mind: 
//How hard will it be if she is nice to me?
//How hard will it get if I let her get to know me?
//Should I be the willing dog or should she see the jungle cat?
//And most of all, my god, how does she make her eyes do that?
//And I don't need another girl inside my head!"
//                    --Blues Traveler

public class grad extends Frame implements WindowListener
{
	private field the_field;	
	
	private Panel butt_panel;
	private Button swap_button;  
	private Button del_button;
	private Button aev_button;
	private Button prn_button;
	private Button sav_button;	

	private RandomAccessFile rdr;
	private String sdr_buff;
	private int x;

	grad()
	{
		setBackground(Color.lightGray);
		setLayout(new BorderLayout());
	 	setSize(875, 70);
		setTitle("Alin Cosmanescu's THS Graduation Chair Arranger");
		
		the_field = new field(this);
		add(the_field);
	
		butt_panel = new Panel();
		butt_panel.setSize(875, 50);
		swap_button = new Button("Swap");
				swap_button.setSize(290, 50);
				swap_button.setEnabled(false);
		del_button  = new Button("Delete");
				del_button.setSize(290, 50);
				del_button.addActionListener(the_field);
		aev_button = new Button("Add/Edit/View");
				aev_button.setSize(290, 50);
				aev_button.addActionListener(the_field);
		prn_button  = new Button("Print");
				prn_button.setSize(290, 50);
				prn_button.addActionListener(the_field);
		sav_button  = new Button("Save");
				sav_button.setSize(290, 50);
				sav_button.addActionListener(the_field);
		butt_panel.add(aev_button);
		butt_panel.add(swap_button);
		butt_panel.add(del_button);
		butt_panel.add(prn_button);
		butt_panel.add(sav_button);
		 
		add(butt_panel, BorderLayout.SOUTH);

		//read names into chairs and add to field		
		try
		{
			rdr = new RandomAccessFile("class.dat", "r");   
		      sdr_buff = rdr.readLine();
		      while (!(sdr_buff.equals(String.valueOf('*'))))
		      {
				the_field.addChairToFieldArray(new chair(the_field, sdr_buff, rdr.readLine(), rdr.readLine()));
				
				sdr_buff = rdr.readLine(); //get the period
				sdr_buff = rdr.readLine(); //next number (or *)
			}
		      rdr.close();
		} catch (Exception e) { System.out.println(e.getMessage()); }	
		the_field.addChairsToField();	

		addWindowListener(this);
  	}
	
	public void windowClosing(WindowEvent e)     { System.exit(0); } 
	public void windowOpened(WindowEvent e)      { }
	public void windowClosed(WindowEvent e)      { }
    	public void windowIconified(WindowEvent e)   { } 
    	public void windowDeiconified(WindowEvent e) { }
    	public void windowActivated(WindowEvent e)   { } 
    	public void windowDeactivated(WindowEvent e) { } 	    

	public static void main(String[] args)
	{       
		new grad().show();
	}	

}

class field extends Panel implements ActionListener, Printable
{
	private chair[] chairs;	//all the chairs

	private GridBagLayout grid;

	private chair[] Sel; //the selected chairs

	private Frame cont;
	
	Dialog addForm;
	Panel fnamPan; 
	Panel lnamPan;
	Panel snumPan;
	Button dK;
	TextField fname;
	TextField lname;
	TextField snumb;
				
	field(Frame cnt)
	{
		setBackground(new Color(20,144,0));
		setSize(875, 34);		
		cont = cnt;

		addForm = new Dialog(cont, "Add/Edit/View", true);
		fnamPan = new Panel();
		lnamPan = new Panel();
		snumPan = new Panel();
		dK = new Button();
		fname = new TextField(15);
		lname = new TextField(30);
		snumb = new TextField(15);
		addForm.setSize(215,98);
		addForm.setLocation(50, 60);
		addForm.setResizable(false);
		dK.addActionListener(this);
		fnamPan.add(fname);
		lnamPan.add(lname);
		snumPan.add(snumb);
		addForm.setBackground(Color.lightGray);			
		addForm.add(snumb, BorderLayout.NORTH);
		addForm.add(fname, BorderLayout.WEST);
		addForm.add(dK, BorderLayout.EAST);
		addForm.add(lname, BorderLayout.SOUTH);		

		chairs = new chair[0];
		Sel = new chair[0];
	}

	public void addChairsToField()
	{
		removeAll();
		grid = new GridBagLayout();
		setLayout(grid);

		int index = 0;
		int bdx   = 0;		
		int xcell = 23;
		int ycell = 1;

		Panel blpn = new Panel();  //blank spots.
			blpn.setBackground(new Color(20,144,0));
			blpn.setSize(38,34);
		grid.setConstraints(blpn, new GridBagConstraints(12, 1, 
								     1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1,1,1,1), 0, 0));

		while (index < Array.getLength(chairs))
		{
			grid.setConstraints(chairs[index], new GridBagConstraints(xcell, ycell, 
								     1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1,1,1,1), 0, 0));
    			add(chairs[index]);
			index++;
			if ((xcell < 24) && (xcell > 1)) 
			{
				xcell--; 
				if (xcell == 12) 
				{ 
					remove(blpn);
					grid.setConstraints(blpn, new GridBagConstraints(12, 1, 
								     1, ycell, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1,1,1,1), 0, 0)); 
					add(blpn);
					xcell--;
				}
			} else {
				if (xcell == 1) 
				{
					xcell = 23; 
					ycell++;
				}
			}
		}
		cont.setSize(875,((ycell * 34) + 70));
		cont.setVisible(true); //there are couple of things I don't like about this program, 
					     //this is the thing I hate the most
	}

	public void addChairToFieldArray(chair ctba)
	{
		chairs = addChairToArray(chairs, ctba); 
	}

	public void setSel(chair sel)
	{
		if (Array.getLength(Sel) < 2)
		{
			Sel = addChairToArray(Sel, sel);
			sel.select();
		}
		else
		{
			rmSel(Sel[0]);
			Sel = addChairToArray(Sel, sel);
			sel.select();
		}
	}
	public void rmSel(chair rm)
	{
		Sel = removeChairFromArray(Sel, rm);
		rm.deselect();	
	}

	private void alphabatize()
	{
		Collator usc = Collator.getInstance(Locale.US);	
		chair tmpChair;
		boolean done = false;	
	
		//gonna stick with a bubble sort
		//i had ambitions about writing a really efficient QuickSort of my own
		//but laziness and procrastination got in the way
		//so I just copiend the bubble sort from the comp sci book		

		for (int x = 1; ((x <= Array.getLength(chairs)) && (!done)); x++)
		{
			done = true;
			for (int y = 0; y < (Array.getLength(chairs) - x); y++)
			{
				if (usc.compare(chairs[y + 1].getlName(), chairs[y].getlName()) < 0) 
				{
					tmpChair = chairs[y + 1];
					chairs[y + 1] = chairs[y];
					chairs[y] = tmpChair;
					done = false;
				}
				else
				{
					if (usc.compare(chairs[y + 1].getlName(), chairs[y].getlName()) == 0) 
					{
						if (usc.compare(chairs[y + 1].getfName(), chairs[y].getfName()) < 0) 
						{
							tmpChair = chairs[y + 1];
							chairs[y + 1] = chairs[y];
							chairs[y] = tmpChair;
							done = false;
						}
					}
				}  
			}
		}
	}

	private chair[] addChairToArray(chair[] aca, chair ctb)
  	{
	      chair[] tmpChair;  
	      int n  = Array.getLength(aca); 
      	tmpChair = aca;                              
      	aca = (chair[])Array.newInstance(ctb.getClass(), (n + 1));    
      	System.arraycopy(tmpChair, 0, aca, 0, n);
      	aca[n] = ctb;
      	return aca;
    	}

	private chair[] removeChairFromArray(chair[] rcr, chair ctbr)
	{
		int org_len = Array.getLength(rcr);
		chair[] tmpChairs = rcr;
		int llcv = 0;

	    	rcr = (chair[])Array.newInstance(ctbr.getClass(), (org_len - 1));
    
	  	for (int lcv = 0; lcv < org_len; lcv++)
	    	{
			if (!(ctbr.getlName().equals(tmpChairs[lcv].getlName()) && ctbr.getfName().equals(tmpChairs[lcv].getfName()) && ctbr.getNumber().equals(tmpChairs[lcv].getNumber()) ))
  			{
		  		rcr[llcv] = tmpChairs[lcv];
		  		llcv++;
			}       
	    	}
		return rcr;
	}

	public int print(Graphics g, PageFormat pf, int pi) throws PrinterException
	{
		if (pi >= 1) { return Printable.NO_SUCH_PAGE; }
		int x = 23;
		int y = 1;
		g.setColor(Color.black);
		g.setFont(new Font("ARIAL", 1, 6));		
		for (int dx = 0; dx < Array.getLength(chairs); dx++)
		{
			g.drawRect((x * 32) - 11, (y * 40) - 11, 29, 38);
			
			g.setFont(new Font("ARIAL", 1, 6));	
			if (chairs[dx].getNumber().length() > 7) { g.setFont(new Font("ARIAL", 1, 4)); }
			
			g.drawString(chairs[dx].getNumber(), (x * 32) - 10,      (y * 40));
			
			g.setFont(new Font("ARIAL", 1, 6));	
			if (chairs[dx].getfName().length() > 7)  { g.setFont(new Font("ARIAL", 1, 4)); }
			
			g.drawString(chairs[dx].getfName(), (x * 32) - 10, 9 + (y * 40));
			
			g.setFont(new Font("ARIAL", 1, 6));	
			if (chairs[dx].getlName().length() > 7)  { g.setFont(new Font("ARIAL", 1, 4)); }
			
			g.drawString(chairs[dx].getlName(),  (x * 32) - 10, 18 + (y * 40));
					
			if ((x < 24) && (x > 1)) 
			{
				x--; 
				if (x == 12) { x--; }
			} else {
				if (x == 1) 
				{
					x = 23; 
					y++;
				}
			}
		} 
		return Printable.PAGE_EXISTS;
	}

	public void actionPerformed(ActionEvent e)
	{		
		if (e.getActionCommand().equals("Delete"))
		{
			if ((Array.getLength(Sel) > 0) && (Array.getLength(Sel) < 2))
			{
				chairs = removeChairFromArray(chairs, Sel[0]);
				rmSel(Sel[0]);
			}
			addChairsToField();			
		}
		if (e.getActionCommand().equals("Add/Edit/View"))
		{		
			if ((Array.getLength(Sel) > 0) && (Array.getLength(Sel) < 2))
			{
				dK.setLabel("Edit");
				snumb.setText(Sel[0].getNumber());
				fname.setText(Sel[0].getfName());
				lname.setText(Sel[0].getlName());
			}
			if ((Array.getLength(Sel) == 0))
			{
				dK.setLabel("Add");
				snumb.setText("");
				fname.setText("");
				lname.setText("");
			}
			addForm.show();
		}
		if (e.getActionCommand().equals("Add"))
		{
			addChairToFieldArray(new chair(this, snumb.getText(), lname.getText(), fname.getText()));
			addForm.hide();			
			alphabatize();
			addChairsToField();		
		}
		if (e.getActionCommand().equals("Edit"))
		{
			chairs = removeChairFromArray(chairs, Sel[0]);
			rmSel(Sel[0]);
			addChairToFieldArray(new chair(this, snumb.getText(), lname.getText(), fname.getText()));
			addForm.hide();
			alphabatize();
			addChairsToField();
		}
		if (e.getActionCommand().equals("Print"))
		{
			PrinterJob printJob = PrinterJob.getPrinterJob();
			PageFormat form = new PageFormat();
			Paper page = new Paper();			
			page.setSize(612, 792);
			page.setImageableArea(1, 1, 610, 780);
			form.setPaper(page);
			form.setOrientation(PageFormat.LANDSCAPE);
			printJob.setPrintable(this, form);
			try { printJob.print(); } catch (Exception ex) { System.out.println(ex.getMessage()); }
		}
		if (e.getActionCommand().equals("Save"))
		{
			FileWriter fdr;	

			try
			{
				fdr = new FileWriter("class.dat");
				for (int x = 0; x < Array.getLength(chairs); x++)
				{
					fdr.write(chairs[x].getNumber().toCharArray());
					fdr.write('\n');
					fdr.write(chairs[x].getlName().toCharArray());
					fdr.write('\n');
					fdr.write(chairs[x].getfName().toCharArray());
					fdr.write('\n');
					fdr.write('.');
					fdr.write('\n');			
				}	
				fdr.write('*');
				fdr.close();
				System.out.println("Save was successful!");
			} catch (Exception ef) { System.out.println(ef.getMessage()); }
		}
	}
}

class chair extends Panel implements MouseListener
{
	private String last_name;
	private String first_name;
	private String number;

      private boolean selected;

	private field parent;

 	chair(field paren)
	{	
		parent = paren;
		selected = false;
		setSize(38, 34);
		number = new String("");
		last_name = new String("");
		first_name = new String("");
		addMouseListener(this);
	}

	chair(field paren, String num, String lnam, String fnam)
	{	
		parent = paren;
		selected = false;
		setSize(38, 34);
		last_name = lnam;
		first_name = fnam;
		number = num;
		addMouseListener(this);
	}

	public void setInfo(String num, String lnam, String fnam)   
	{ 
		first_name = new String(fnam);
		last_name  = lnam;
		number     = num;
		repaint();
	}

	public String getfName()  { return first_name; }
	public String getlName()  { return last_name;  } 
	public String getNumber() { return number;     }

	public void deselect()
	{
		selected = false;
		repaint();
	}

	public void select()
	{
		selected = true;
		repaint();
	}

	public void mouseClicked(MouseEvent e)
	{
		if (selected)    
		{ 
			parent.rmSel(this);
		}
		else
		{
			if (!(selected)) 
			{  
				parent.setSel(this);
			}
		}
		repaint();
	} 
      public void mouseEntered(MouseEvent e)  { } 
      public void mouseExited(MouseEvent e)   { } 	
	public void mousePressed(MouseEvent e)  { }
 	public void mouseReleased(MouseEvent e) { } 

	public void paint(Graphics g)
    	{
		if (!(selected)) 
		{ 
			g.setColor(Color.white);
			g.fillRect(0,0,38,34); 
			g.setColor(Color.black);
		}
		if (selected)    
		{			 
			g.setColor(Color.black); 
			g.fillRect(0,0,38,34);
			g.setColor(Color.white);
		}
		g.setFont(new Font("ARIAL", 1, 10));			
		if (number.length() > 5)
		{ 
			g.drawString(number.substring(0,5), 0, 10);     
		}
		else
		{
			g.drawString(number, 0, 10);     
		}
		if (first_name.length() > 5)
		{ 
			g.drawString(first_name.substring(0,5), 0, 20);     
		}
		else
		{
			g.drawString(first_name, 0, 20);     
		}
		if (last_name.length() > 5)
		{ 
			g.drawString(last_name.substring(0,5), 0, 30);     
		}
		else
		{
			g.drawString(last_name, 0, 30);     
		}
	}

	public void update( Graphics g )
      {
         paint(g);
      }
}