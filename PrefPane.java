//
//	File:		PrefPane.java
//

import java.awt.*;
import java.awt.event.*;

public class PrefPane extends Frame implements ActionListener
{
    protected Button okButton;
    protected Label prefsText;
	protected TextField time;
	private Object  parent;
	
	public int the_time;

    public PrefPane(Object prnt)
    {
        super();
        this.setLayout(new BorderLayout(40, 40));
        this.setFont(new Font ("SansSerif", Font.BOLD, 14));
		parent = prnt;

        prefsText = new Label ("Time limit:");
		time = new TextField("3", 5);
		time.setSize(10,10);
        Panel textPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		textPanel.add(prefsText);
		textPanel.add(time);
		this.add (textPanel, BorderLayout.NORTH);
		
        okButton = new Button("OK");
        Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add (okButton);
        okButton.addActionListener(this);
        this.add(buttonPanel, BorderLayout.SOUTH);
		
        this.setSize(200,120);
    }
	

    public void actionPerformed(ActionEvent newEvent) 
    {
		the_time = Integer.valueOf(time.getText()).intValue();
        setVisible(false);
    }	
	
}