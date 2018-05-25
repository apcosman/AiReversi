//
//  aireversi.java
//  AI Reversi
//
//  Created by Alin Cosmanescu on 3/28/05.
//

//import java.util.Locale;
//import java.util.ResourceBundle;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
//import java.awt.Desktop.*;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.lang.reflect.*;
import java.text.*;
import java.awt.print.*;

//import com.apple.eawt.*;

public class aireversi extends Frame implements WindowListener {

    private Font font = new Font("serif", Font.ITALIC+Font.BOLD, 36);
    protected ResourceBundle resbundle;
    protected AboutBox aboutBox;
    protected PrefPane prefs;
    private Desktop fApplication = Desktop.getDesktop();

    protected Action newAction, openAction, printAction, saveAction, saveAsAction, legalAction;
	
    // Declarations for menus
    static final MenuBar mainMenuBar = new MenuBar();
	
    protected Menu gameMenu;
    protected MenuItem miNew;
    protected MenuItem miOpen;
    protected MenuItem miPrint;
    protected MenuItem miSave;
    protected MenuItem miSaveAs;
    protected MenuItem miLegal;
    
    //my stuff
    private board the_board;
    private agent the_agent;
	
    aireversi() 
    {
	setBackground(Color.lightGray);
	setLayout(new BorderLayout());
	setSize(500, 500);
	setTitle("Alin Cosmanescu's AI Reversi");
	addWindowListener(this);
	addMenus();
		
	the_board = new board(this);
	the_agent = new agent(the_board);
	add(the_board);
		
	//fApplication.setEnabledPreferencesMenu(true);
	/*fApplication.addApplicationListener(new com.apple.eawt.ApplicationAdapter() 
	{
	    public void handleAbout(ApplicationEvent e) 
	    {
		if (aboutBox == null) { aboutBox = new AboutBox(); }
		about(e);
		e.setHandled(true);
	    }
	    public void handleOpenApplication(ApplicationEvent e) {}
	    public void handleOpenFile(ApplicationEvent e) {}
	    public void handlePreferences(ApplicationEvent e) 
	    {
		if (prefs == null) 
		{
		    prefs = new PrefPane(e.getSource());
		}
		preferences(e);
	    }
	    public void handlePrintFile(ApplicationEvent e) {}
            
            public void handleQuit(ApplicationEvent e) 
	    {
                quit(e);
            }
        });*/
	fApplication.setAboutHandler(new java.awt.desktop.AboutHandler()
			{
				public void handleAbout(java.awt.desktop.AboutEvent e) {
					if (aboutBox == null) { aboutBox = new AboutBox(); }
					about(e);
					//e.setHandled(true);
				}
			});
	    
        setVisible(true);
    }
    
    public void Turn(int player) 
    { 
	int time_limit = 3;
	if (prefs != null) { time_limit = prefs.the_time; }
	int pref_time = time_limit * 1000;
	if (the_board.numMoves(player) > 0) 
	{
	    the_agent.Turn(player, pref_time, (new Date()).getTime()); 
	    if (the_board.numMoves(1) == 0) { this.Turn(2); }
	}
	if (the_board.numMoves(1) == 0 && the_board.numMoves(2) == 0)
	{
	    if (the_board.winner() == 0) 
	    { 
		System.out.println("We have fought to a draw!"); 
	    }
	    else
	    {
		System.out.println("Player " + the_board.winner() + " has won");
		System.out.println(the_board.generateScr(1) + " to " + the_board.generateScr(2));
	    }
	}
    }

    public void about(java.awt.desktop.AboutEvent e) 
    {
        aboutBox.setResizable(false);
        aboutBox.setVisible(true);
        aboutBox.show();
    }

    public void preferences(java.awt.desktop.AppEvent e) 
    {
        prefs.setResizable(false);
        prefs.setVisible(true);
        prefs.show();
    }

    public void quit(java.awt.desktop.AppEvent e) 
    {
        System.exit(0);
    }

    public void createActions() 
    {
        int shortcutKeyMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

        //Create actions that can be used by menus, buttons, toolbars, etc.
        newAction = new newActionClass("newItem",
            KeyStroke.getKeyStroke(KeyEvent.VK_N, shortcutKeyMask) );
        openAction = new openActionClass("openItem",
            KeyStroke.getKeyStroke(KeyEvent.VK_O, shortcutKeyMask) );
        printAction = new printActionClass("printItem",
            KeyStroke.getKeyStroke(KeyEvent.VK_P, shortcutKeyMask) );
        saveAction = new saveActionClass("saveItem",
            KeyStroke.getKeyStroke(KeyEvent.VK_S, shortcutKeyMask) );
        saveAsAction = new saveAsActionClass("saveAsItem");
	legalAction =  new showLegalSquaresActionClass("legalAction", KeyStroke.getKeyStroke(KeyEvent.VK_L, shortcutKeyMask) );
    } 
    
    public void addGameMenuItems() 
    {
        miNew = new MenuItem ("New Game");
        miNew.setShortcut(new MenuShortcut(KeyEvent.VK_N, false));
        gameMenu.add(miNew).setEnabled(true);
        miNew.addActionListener(newAction);
		
        miOpen = new MenuItem ("Open Game");
        miOpen.setShortcut(new MenuShortcut(KeyEvent.VK_O, false));
        gameMenu.add(miOpen).setEnabled(true);
        miOpen.addActionListener(openAction);
		
        miPrint = new MenuItem ("Print Board");
        miPrint.setShortcut(new MenuShortcut(KeyEvent.VK_P, false));
        gameMenu.add(miPrint).setEnabled(true);
        miPrint.addActionListener(printAction);
		
        miSave = new MenuItem ("Save Game");
        miSave.setShortcut(new MenuShortcut(KeyEvent.VK_S, false));
        gameMenu.add(miSave).setEnabled(true);
        miSave.addActionListener(saveAction);
		
        miSaveAs = new MenuItem ("Save Game as...");
        gameMenu.add(miSaveAs).setEnabled(true);
        miSaveAs.addActionListener(saveAsAction);
	
	miLegal = new MenuItem ("Show Legal Moves");
	miLegal.setShortcut(new MenuShortcut(KeyEvent.VK_L, false));
	gameMenu.add(miLegal).setEnabled(true);
	miLegal.addActionListener(legalAction);
		
        mainMenuBar.add(gameMenu);
    }
	
    public void addMenus() 
    {
        gameMenu = new Menu("Game");
        createActions();
	addGameMenuItems();
        setMenuBar (mainMenuBar);
    }
        
    public void paint(Graphics g) 
    {
        g.setColor(Color.blue);
        g.setFont (font);
        g.drawString("test", 40, 80);
    }
    
    public void handleAbout()
    {
        aboutBox.setResizable(false);
        aboutBox.setVisible(true);
        aboutBox.show();
    }
	
    public void handleQuit() { System.exit(0); }
    	
    public class newActionClass extends AbstractAction 
    {
        public newActionClass(String text, KeyStroke shortcut) 
	{
            super(text);
            putValue(ACCELERATOR_KEY, shortcut);
        }
        public void actionPerformed(ActionEvent e) { System.out.println("New..."); }
    }

    public class openActionClass extends AbstractAction 
    {
        public openActionClass(String text, KeyStroke shortcut) 
	{
            super(text);
            putValue(ACCELERATOR_KEY, shortcut);
        }
        public void actionPerformed(ActionEvent e) { System.out.println("Open..."); }
    }
	
    public class printActionClass extends AbstractAction 
    {
        public printActionClass(String text, KeyStroke shortcut) 
	{
            super(text);
            putValue(ACCELERATOR_KEY, shortcut);
        }
        public void actionPerformed(ActionEvent e) 
	{
	    System.out.println("Print...");
	    
	    int[][] temp = the_board.getState();
	    for (int lcx = 0; lcx < 8; lcx++)
	    {
		for (int lcy = 0; lcy < 8; lcy++) { System.out.print(temp[lcx][lcy]); }
		System.out.println("");
	    } 	    
	}
    }
	
    public class saveActionClass extends AbstractAction 
    {
        public saveActionClass(String text, KeyStroke shortcut) 
	{
            super(text);
            putValue(ACCELERATOR_KEY, shortcut);
        }
        public void actionPerformed(ActionEvent e) { System.out.println("Save..."); }
    }
	
    public class saveAsActionClass extends AbstractAction 
    {
        public saveAsActionClass(String text) 
	{
            super(text);
        }
        public void actionPerformed(ActionEvent e) { System.out.println("Save As..."); }
    }
	
    public class showLegalSquaresActionClass extends AbstractAction
    {
	public showLegalSquaresActionClass(String text, KeyStroke shortcut)
	{
	    super(text);
	    putValue(ACCELERATOR_KEY, shortcut);
	}
	public void actionPerformed(ActionEvent e)
	{
	    the_board.getLegalSquares(1, true);
	}
    }
	
    public void windowClosing(WindowEvent e)     { handleQuit(); } 
    public void windowOpened(WindowEvent e)      { }
    public void windowClosed(WindowEvent e)      { }
    public void windowIconified(WindowEvent e)   { } 
    public void windowDeiconified(WindowEvent e) { }
    public void windowActivated(WindowEvent e)   { } 
    public void windowDeactivated(WindowEvent e) { }
    
    public static void main(String args[]) { new aireversi(); }
}

class agent
{
    private board    the_game;
    private gameTree minmax; 
    
    private int the_move = 0;
    private int num_moves = 0;
    private int legal_moves[][];
    
    public boolean Time;

    agent(board game)
    {
	the_game = game;
	Time = true;
    }
    
    public void Turn(int player, int time, long start)
    {
	int the_move = 0;
	long the_eVal = 0;
	int legal_moves[][] = the_game.getLegalSquares(player, false);
	int level;

	
	minmax = new gameTree(this, null, new state_board(the_game), player, true, 0, 0);
	level = 1;
	Time = minmax.generateKids(level, time, start);
	while (Time)
	{
	    the_move = minmax.getMove();
	    the_eVal = minmax.eVal;
	    System.out.println("After level: " + level + " would choose move " + the_move);
	    level++;
	    minmax = new gameTree(this, null, new state_board(the_game), player, true, 0, 0);
	    Time = minmax.generateKids(level, time, start);
	}
	 System.out.println("Got to level: " + level + " in " + ((new Date()).getTime() - start) / 1000 + " seconds" + ", taking legal move number: " + the_move);
	//System.out.println(the_move);
	//System.out.println(the_eVal);
	
	the_game.playSquare(legal_moves[the_move][0], legal_moves[the_move][1], player);
	minmax = null;
	Time = true;
    }
}

class gameTree
{
    private gameTree children[];
    public state_board game_state;
    private agent user;
    private gameTree parent;
    public long eVal;

    public boolean max_node;
    private int root_player;
    private int opposite;
    private int plyTurn;

    private int level;
    private int branches;
    public int node_number;
    
    private int lcx;
    private int lcy;
    private int legal[][];
    
    private boolean ret_val;

    gameTree(agent usr, gameTree prnt, state_board state, int plyr, boolean mxnd, int lvl, int ndn)
    {
	user = usr;
	parent = prnt;
	game_state = state;
	 
	node_number = ndn;
	level = lvl;
	
	root_player = plyr;
	max_node = mxnd;
    
	if (max_node)  { plyTurn = root_player; }
	if (!max_node && root_player == 2)  { plyTurn = 1; } 
	if (!max_node && root_player == 1) { plyTurn = 2; } 
	
	eVal = Long.MIN_VALUE;
	if (!max_node) { eVal = Long.MAX_VALUE; }
	
	ret_val = true;
    }
    
    public boolean generateKids(int lvl, int time, long start)
    {
	branches = Array.getLength(game_state.getLegalSquares(plyTurn));
	children = new gameTree[branches];
	legal = game_state.getLegalSquares(plyTurn);
	
	if ( (new Date()).getTime() - start < time ) 
	{  
	    if (branches > 0)
	    {
		for (lcx = 0; lcx < branches; lcx++)
		{
//		  System.out.print("Generating child: " + lcx + "/" + Array.getLength(legal) + " from node: " + node_number + " of level: " + level); 
		    children[lcx] = new gameTree(user, this, (new state_board(game_state)).playSquare(legal[lcx][0], legal[lcx][1], plyTurn), root_player, !max_node, level + 1, lcx);
		    if ((level+1) < lvl)
		    {
//			System.out.println(" ");
			ret_val = children[lcx].generateKids(lvl, time, start);
			if ( (max_node && (children[lcx].eVal > eVal)) || (!max_node && (children[lcx].eVal < eVal)) ) 
			{ 
//			    System.out.println("   child: " + lcx + "/" + Array.getLength(legal) + " from node: " + node_number + " of level: " + level + " has a better eVal: " + children[lcx].eVal + " then its " + max_node + " parent's " + eVal); 
			    eVal = children[lcx].eVal; 
			}
			else
			{
			    children[lcx] = null;
			}
		    }
		    else
		    {
//			System.out.println(" evaluation: " + children[lcx].evaluate());
			if ( (max_node && (children[lcx].evaluate() > eVal)) || (!max_node && (children[lcx].evaluate() < eVal)) ) 
			{ 
			    eVal = children[lcx].evaluate(); 
			}
			else
			{
			    children[lcx] = null;
			}
		    }
		    if ( parent != null && ((!parent.max_node && eVal > parent.eVal) || (parent.max_node && eVal < parent.eVal)) ) { return ret_val; }
		}
	    }
	    else
	    {
		if (plyTurn == 1) 
		{ 
		    plyTurn = 2; 
		}
		else
		{
		    plyTurn = 1;
		}
		if ( Array.getLength(game_state.getLegalSquares(plyTurn)) > 0 ) { ret_val = generateKids(lvl, time, start); }
	    }
//	   System.out.println("--eVal: " + eVal);
	}
	else
	{
	    return false;
	}
	return ret_val;
    }

    public long evaluate()
    {
	long temp = 0;
	int opposite = 1;
	if (root_player == 1) { opposite = 2; }
    
	temp = (9 * game_state.numMoves(root_player) + 100 * game_state.generateScr(root_player)) - (9 * game_state.numMoves(opposite) + 100 * game_state.generateScr(opposite));
	if ( game_state.getState()[0][0] == root_player ) { temp += 999990; }
	if ( game_state.getState()[7][0] == root_player ) { temp += 999990; }
	if ( game_state.getState()[0][7] == root_player ) { temp += 999990; }
	if ( game_state.getState()[7][7] == root_player ) { temp += 999990; }
	
	if ( game_state.getState()[0][0] == opposite ) { temp -= 999990; }
	if ( game_state.getState()[7][0] == opposite ) { temp -= 999990; }
	if ( game_state.getState()[0][7] == opposite ) { temp -= 999990; }
	if ( game_state.getState()[7][7] == opposite ) { temp -= 999990; }

	for (lcy = 0; lcy < 8; lcy++)
	{
	    if ( game_state.getState()[0][lcy] == root_player ) { temp += 999; }
	    if ( game_state.getState()[lcy][0] == root_player ) { temp += 999; }
	    if ( game_state.getState()[7][lcy] == root_player ) { temp += 999; }
	    if ( game_state.getState()[lcy][7] == root_player ) { temp += 999; }
	
	    if ( game_state.getState()[0][lcy] == opposite ) { temp -= 999; }
	    if ( game_state.getState()[lcy][0] == opposite ) { temp -= 999; }
	    if ( game_state.getState()[7][lcy] == opposite ) { temp -= 999; }
	    if ( game_state.getState()[lcy][7] == opposite ) { temp -= 999; }
	
	}
	return temp;
    }
    
    public int getMove() 
    { 
	for (lcx = 0; lcx < branches; lcx++)
	{
	    if (children[lcx] != null)
	    {
		if (children[lcx].eVal == eVal) { return lcx; }
	    }
	}
	return 0;
    }
}

class state_board
{
    private int[][] grid;
    
    private int lcx = 0;
    private int lcy = 0;
    
    state_board(board st) 
    { 
	grid = new int[8][8];
	for (lcx = 0; lcx < 8; lcx++)
	{
	    for (lcy = 0; lcy < 8; lcy++)
	    {
		grid[lcx][lcy] = st.getState()[lcx][lcy];
	    }
	}
    }
    
    state_board(state_board st) 
    { 
	grid = new int[8][8];
	for (lcx = 0; lcx < 8; lcx++)
	{
	    for (lcy = 0; lcy < 8; lcy++)
	    {
		grid[lcx][lcy] = st.getState()[lcx][lcy];
	    }
	}
    }
    
    public int[][] getState() { return grid; }
	    
    public int generateScr(int player)
    {
	int ret_val = 0;
	int full = 0;
    
	for (lcx = 0; lcx < 8; lcx++)
	{
	    for (lcy = 0; lcy < 8; lcy++)
	    {
		if (grid[lcx][lcy] == player) { ret_val++; }
	    }
	}   
	return ret_val;
    }
	
    public int winner()
    {
	if (generateScr(1) >  generateScr(2)) { return 1; }
	if (generateScr(1) <  generateScr(2)) { return 2; }
	return 0;
    }
	
    public int[][] getLegalSquares(int player)
    {
	int idx = 0;
	int[][] ret_val = new int[numMoves(player)][2];
	    
	for (lcx = 0; lcx < 8; lcx++)
	{
	    for(lcy = 0; lcy < 8; lcy++)
	    {
		if (isSquareLegal(lcx, lcy, player))
		{
		    ret_val[idx][0] = lcx;
		    ret_val[idx][1] = lcy;
		    idx++;
		}
	    }
	}
	return ret_val;
    }

    public int numMoves(int player)
    {
	int idx = 0;
	for (lcx = 0; lcx < 8; lcx++)
	{
	    for(lcy = 0; lcy < 8; lcy++)
	    {
		if (isSquareLegal(lcx, lcy, player)) { idx++; }
	    }
	}
	return idx;
    }

    public boolean isSquareLegal(int x, int y, int player)
    {
	if (grid[x][y] == 0)
	{
	    if (findXLEFT(x, y, player)  < x)  { return true; }
	    if (findXRIGHT(x, y, player) > x)  { return true; }
	    if (findYUP(x, y, player)    < y)  { return true; }
	    if (findYDOWN(x, y, player)  > y)  { return true; }
	    if (findRDdown(x, y, player)[0] > x && findRDdown(x, y, player)[1]   > y) {return true;}
	    if (findRUup(x, y, player)[0]   > x && findRUup(x, y, player)[1]     < y) {return true;}
	    if (findLDdown(x, y, player)[0] < x && findLDdown(x, y, player)[1]   > y) {return true;}
	    if (findLUup(x, y, player)[0]   < x && findLUup(x, y, player)[1]     < y) {return true;}
	}
	return false;
    }
	
	public state_board playSquare(int x, int y, int player) 
	{ 	    
	    for(lcx = findXLEFT(x,y,player); lcx < x; lcx++)
	    {
		grid[lcx][y] = player;
	    }
	    for(lcx = findXRIGHT(x,y,player); lcx > x; lcx--)
	    {
		grid[lcx][y] = player;
	    }
	    for(lcy = findYUP(x,y,player); lcy < y; lcy++)
	    {
		grid[x][lcy] = player;
	    }
	    for(lcy = findYDOWN(x,y,player); lcy > y; lcy--)
	    {
		grid[x][lcy] = player;
	    }
	    for(lcx = findRDdown(x, y, player)[0], lcy = findRDdown(x, y, player)[1]; lcx > x && lcy > y; lcy--, lcx--)
	    {
		grid[lcx][lcy] = player;
	    }
	    for(lcx = findRUup(x, y, player)[0], lcy = findRUup(x, y, player)[1]; lcx > x && lcy < y; lcy++, lcx--)
	    {
		grid[lcx][lcy] = player;
	    }
	    for(lcx = findLDdown(x, y, player)[0], lcy = findLDdown(x, y, player)[1]; lcx < x && lcy > y; lcy--, lcx++)
	    {
		grid[lcx][lcy] = player;
	    }
	    for(lcx = findLUup(x, y, player)[0], lcy = findLUup(x, y, player)[1]; lcx < x && lcy < y; lcy++, lcx++)
	    {
		grid[lcx][lcy] = player;
	    }
	    grid[x][y] = player;
	    return this;
	}

	public int findXLEFT(int x, int y, int player)
	{
	    int left = x;
	    if (x - 1 > -1)
	    {
		if (grid[x - 1][y] != player && grid[x - 1][y] > 0)
		{
		    left--;
		    while (left >= 0 && grid[left][y] != player && grid[left][y] > 0) { left--; }
		    if (left >= 0)
		    {
		    	if (grid[left][y] == player) { return left; }
		    }
		}
	    }
	    return x;
	}
	
	public int findXRIGHT(int x, int y, int player)
	{
	    int right = x;
	    if (x + 1 < 8)
	    {
		if (grid[x + 1][y] != player && grid[x + 1][y] > 0)
		{
		    right++;
		    while (right < 8 && grid[right][y] != player && grid[right][y] > 0) { right++; }
		    if (right < 8)
		    {
			if (grid[right][y] == player) { return right; }
		    }
		}
	    }
	    return x;
	}
	
	public int findYUP(int x, int y, int player)
	{
	    int up = y;
	    if (y - 1 > -1)
	    {
		if (grid[x][y - 1] != player && grid[x][y - 1] > 0)
		{
		    up--;
		    while (up >= 0 && grid[x][up] != player && grid[x][up] > 0) { up--; }
		    if (up >= 0)
		    {
			if (grid[x][up] == player) { return up; }
		    }
		}
	    }
	    return y;
	}

	public int findYDOWN(int x, int y, int player)
	{
	    int down = y;
	    if (y + 1 < 8)
	    {
		if (grid[x][y + 1] != player && grid[x][y + 1] > 0)
		{
		    down++;
		    while (down < 8 && grid[x][down] != player && grid[x][down] > 0) { down++; }
		    if (down < 8)
		    {
			if (grid[x][down] == player) { return down; }
		    }
		}
	    }
	    return y;
	}

	public int[] findRDdown(int x, int y, int player)
	{
	    int down = y;
	    int right = x;
	    int[] ret_val = {x, y};
	    
	    if (y + 1 < 8 && x + 1 < 8)
	    {
		if (grid[x + 1][y + 1] != player && grid[x + 1][y + 1] > 0)
		{
		    down++;
		    right++;
		    while (down < 8 && right < 8 && grid[right][down] != player && grid[right][down] > 0) 
		    { 
			down++;
			right++; 
		    }
		    if (down < 8 && right < 8) 
		    {
			if (grid[right][down] == player) 
			{	
			    ret_val[0] = right;
			    ret_val[1] = down; 
			}
		    }
		}
	    }
	    return ret_val;
	}
	
	public int[] findRUup(int x, int y, int player)
	{
	    int up = y;
	    int right = x;
	    int[] ret_val = {x, y};
	    
	    if (y - 1 > -1 && x + 1 < 8)
	    {
		if (grid[x + 1][y - 1] != player && grid[x + 1][y - 1] > 0)
		{
		    up--;
		    right++;
		    while (up >= 0 && right < 8 && grid[right][up] != player && grid[right][up] > 0) 
		    { 
			up--;
			right++; 
		    }
		    if (up >= 0 && right < 8) 
		    {
			if (grid[right][up] == player) 
			{	
			    ret_val[0] = right;
			    ret_val[1] = up; 
			}
		    }
		}
	    }
	    return ret_val;
	}
	
	public int[] findLDdown(int x, int y, int player)
	{
	    int down = y;
	    int left = x;
	    int[] ret_val = {x, y};
	    
	    if (y + 1 < 8 && x - 1 > -1)
	    {
		if (grid[x - 1][y + 1] != player && grid[x - 1][y + 1] > 0)
		{
		    down++;
		    left--;
		    while (down < 8 && left >= 0 && grid[left][down] != player && grid[left][down] > 0) 
		    { 
			down++;
			left--; 
		    }
		    if (down < 8 && left >= 0) 
		    {
			if (grid[left][down] == player) 
			{	
			    ret_val[0] = left;
			    ret_val[1] = down; 
			}
		    }
		}
	    }
	    return ret_val;
	}
	
	public int[] findLUup(int x, int y, int player)
	{
	    int up = y;
	    int left = x;
	    int[] ret_val = {x, y};
	    
	    if (y - 1 > -1 && x - 1 > -1)
	    {
		if (grid[x - 1][y - 1] != player && grid[x - 1][y - 1] > 0)
		{
		    up--;
		    left--;
		    while (up >= 0 && left >= 0 && grid[left][up] != player && grid[left][up] > 0) 
		    { 
			up--;
			left--; 
		    }
		    if (up >= 0 && left >= 0) 
		    {
			if (grid[left][up]== player) 
			{	
			    ret_val[0] = left;
			    ret_val[1] = up; 
			}
		    }
		}
	    }
	    return ret_val;
	}
}


class board extends Panel
{
	private square[][] grid; //the board
	private GridBagLayout layout;
	
	private aireversi cont;
	
	private int lcx = 0;
	private int lcy = 0;
	
	board(aireversi cnt)
	{
		setBackground(new Color(0,255,0));
		cont = cnt;
	    
		grid = new square[8][8];
	    
		layout = new GridBagLayout();
		setLayout(layout);
	    
		for (lcx = 0; lcx < 8; lcx++)
		{
		    for (lcy = 0; lcy < 8; lcy++)
		    {
			grid[lcx][lcy] = new square(this, lcx, lcy);
			layout.setConstraints(grid[lcx][lcy], new GridBagConstraints(lcx, lcy, 
								    1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1,1,1,1), 0, 0));
			add(grid[lcx][lcy]);
		    }
		}
		
		grid[3][3].setState(1);
		grid[3][4].setState(2);
		grid[4][3].setState(2);
		grid[4][4].setState(1);
	}

	public int[][] getState()
	{
	    int[][] ret_val = new int[8][8];	    
	    for (lcx = 0; lcx < 8; lcx++)
	    {
		for (lcy = 0; lcy < 8; lcy++)
		{
		    ret_val[lcx][lcy] = grid[lcx][lcy].getState();
		}
	    } 	    
	    return ret_val;
	}
	    
	public int generateScr(int player)
	{
	    int ret_val = 0;
	    int full = 0;
    
	    for (lcx = 0; lcx < 8; lcx++)
	    {
		for (lcy = 0; lcy < 8; lcy++)
		{
		    if (grid[lcx][lcy].getState() == player) { ret_val++; }
		}
	    }   
	    return ret_val;
	}
	
	public int winner()
	{
	    if (generateScr(1) >  generateScr(2)) { return 1; }
	    if (generateScr(1) <  generateScr(2)) { return 2; }
	    return 0;
	}
	
	public void setState(int[][] state) 
	{
	    for (lcx = 0; lcx < 8; lcx++)
	    {
		for (lcy = 0; lcy < 8; lcy++)
		{
		    grid[lcx][lcy] = new square(this, lcx, lcy);
		    grid[lcx][lcy].setState(state[lcx][lcy]);
		}
	    }
	}
	
	public void Turn(int player) { cont.Turn(player); }

	public int[][] getLegalSquares(int player, boolean show)
	{
	    int idx = 0;
	    int[][] ret_val = new int[numMoves(player)][2];
	    
	    for (lcx = 0; lcx < 8; lcx++)
	    {
		for(lcy = 0; lcy < 8; lcy++)
		{
		    if (isSquareLegal(lcx, lcy, player))
		    {
			if (show) 
			{ 
			    grid[lcx][lcy].highlight(); 
			}
			else
			{
			    grid[lcx][lcy].deselect();
			}
			ret_val[idx][0] = lcx;
			ret_val[idx][1] = lcy;
			idx++;
		    }
		}
	    }
	    return ret_val;
	}
	
	public square getSquare(int x, int y)
	{
	    return grid[x][y];
	}

	public int numMoves(int player)
	{
	    int idx = 0;
	    for (lcx = 0; lcx < 8; lcx++)
	    {
		for(lcy = 0; lcy < 8; lcy++)
		{
		    if (isSquareLegal(lcx, lcy, player)) { idx++; }
		}
	    }
	    return idx;
	}

	public boolean isSquareLegal(int x, int y, int player)
	{
	    if (grid[x][y].getState() == 0)
	    {
		if (findXLEFT(x, y, player)  < x)  { return true; }
		if (findXRIGHT(x, y, player) > x)  { return true; }
		if (findYUP(x, y, player)    < y)  { return true; }
		if (findYDOWN(x, y, player)  > y)  { return true; }
		if (findRDdown(x, y, player)[0] > x && findRDdown(x, y, player)[1]   > y) {return true;}
		if (findRUup(x, y, player)[0]   > x && findRUup(x, y, player)[1]     < y) {return true;}
		if (findLDdown(x, y, player)[0] < x && findLDdown(x, y, player)[1]   > y) {return true;}
		if (findLUup(x, y, player)[0]   < x && findLUup(x, y, player)[1]     < y) {return true;}
	    }
	    return false;
	}
	
	public board playSquare(int x, int y, int player) 
	{ 	    
	    for(lcx = findXLEFT(x,y,player); lcx < x; lcx++)
	    {
		grid[lcx][y].setState(player);
	    }
	    for(lcx = findXRIGHT(x,y,player); lcx > x; lcx--)
	    {
		grid[lcx][y].setState(player);
	    }
	    for(lcy = findYUP(x,y,player); lcy < y; lcy++)
	    {
		grid[x][lcy].setState(player);
	    }
	    for(lcy = findYDOWN(x,y,player); lcy > y; lcy--)
	    {
		grid[x][lcy].setState(player);
	    }
	    for(lcx = findRDdown(x, y, player)[0], lcy = findRDdown(x, y, player)[1]; lcx > x && lcy > y; lcy--, lcx--)
	    {
		grid[lcx][lcy].setState(player);
	    }
	    for(lcx = findRUup(x, y, player)[0], lcy = findRUup(x, y, player)[1]; lcx > x && lcy < y; lcy++, lcx--)
	    {
		grid[lcx][lcy].setState(player);
	    }
	    for(lcx = findLDdown(x, y, player)[0], lcy = findLDdown(x, y, player)[1]; lcx < x && lcy > y; lcy--, lcx++)
	    {
		grid[lcx][lcy].setState(player);
	    }
	    for(lcx = findLUup(x, y, player)[0], lcy = findLUup(x, y, player)[1]; lcx < x && lcy < y; lcy++, lcx++)
	    {
		grid[lcx][lcy].setState(player);
	    }
	    grid[x][y].setState(player);
	    return this;
	}

	public int findXLEFT(int x, int y, int player)
	{
	    int left = x;
	    if (x - 1 > -1)
	    {
		if (grid[x - 1][y].getState() != player && grid[x - 1][y].getState() > 0)
		{
		    left--;
		    while (left >= 0 && grid[left][y].getState() != player && grid[left][y].getState() > 0) { left--; }
		    if (left >= 0)
		    {
		    	if (grid[left][y].getState() == player) { return left; }
		    }
		}
	    }
	    return x;
	}
	
	public int findXRIGHT(int x, int y, int player)
	{
	    int right = x;
	    if (x + 1 < 8)
	    {
		if (grid[x + 1][y].getState() != player && grid[x + 1][y].getState() > 0)
		{
		    right++;
		    while (right < 8 && grid[right][y].getState() != player && grid[right][y].getState() > 0) { right++; }
		    if (right < 8)
		    {
			if (grid[right][y].getState() == player) { return right; }
		    }
		}
	    }
	    return x;
	}
	
	public int findYUP(int x, int y, int player)
	{
	    int up = y;
	    if (y - 1 > -1)
	    {
		if (grid[x][y - 1].getState() != player && grid[x][y - 1].getState() > 0)
		{
		    up--;
		    while (up >= 0 && grid[x][up].getState() != player && grid[x][up].getState() > 0) { up--; }
		    if (up >= 0)
		    {
			if (grid[x][up].getState() == player) { return up; }
		    }
		}
	    }
	    return y;
	}

	public int findYDOWN(int x, int y, int player)
	{
	    int down = y;
	    if (y + 1 < 8)
	    {
		if (grid[x][y + 1].getState() != player && grid[x][y + 1].getState() > 0)
		{
		    down++;
		    while (down < 8 && grid[x][down].getState() != player && grid[x][down].getState() > 0) { down++; }
		    if (down < 8)
		    {
			if (grid[x][down].getState() == player) { return down; }
		    }
		}
	    }
	    return y;
	}

	public int[] findRDdown(int x, int y, int player)
	{
	    int down = y;
	    int right = x;
	    int[] ret_val = {x, y};
	    
	    if (y + 1 < 8 && x + 1 < 8)
	    {
		if (grid[x + 1][y + 1].getState() != player && grid[x + 1][y + 1].getState() > 0)
		{
		    down++;
		    right++;
		    while (down < 8 && right < 8 && grid[right][down].getState() != player && grid[right][down].getState() > 0) 
		    { 
			down++;
			right++; 
		    }
		    if (down < 8 && right < 8) 
		    {
			if (grid[right][down].getState() == player) 
			{	
			    ret_val[0] = right;
			    ret_val[1] = down; 
			}
		    }
		}
	    }
	    return ret_val;
	}
	
	public int[] findRUup(int x, int y, int player)
	{
	    int up = y;
	    int right = x;
	    int[] ret_val = {x, y};
	    
	    if (y - 1 > -1 && x + 1 < 8)
	    {
		if (grid[x + 1][y - 1].getState() != player && grid[x + 1][y - 1].getState() > 0)
		{
		    up--;
		    right++;
		    while (up >= 0 && right < 8 && grid[right][up].getState() != player && grid[right][up].getState() > 0) 
		    { 
			up--;
			right++; 
		    }
		    if (up >= 0 && right < 8) 
		    {
			if (grid[right][up].getState() == player) 
			{	
			    ret_val[0] = right;
			    ret_val[1] = up; 
			}
		    }
		}
	    }
	    return ret_val;
	}
	
	public int[] findLDdown(int x, int y, int player)
	{
	    int down = y;
	    int left = x;
	    int[] ret_val = {x, y};
	    
	    if (y + 1 < 8 && x - 1 > -1)
	    {
		if (grid[x - 1][y + 1].getState() != player && grid[x - 1][y + 1].getState() > 0)
		{
		    down++;
		    left--;
		    while (down < 8 && left >= 0 && grid[left][down].getState() != player && grid[left][down].getState() > 0) 
		    { 
			down++;
			left--; 
		    }
		    if (down < 8 && left >= 0) 
		    {
			if (grid[left][down].getState() == player) 
			{	
			    ret_val[0] = left;
			    ret_val[1] = down; 
			}
		    }
		}
	    }
	    return ret_val;
	}
	
	public int[] findLUup(int x, int y, int player)
	{
	    int up = y;
	    int left = x;
	    int[] ret_val = {x, y};
	    
	    if (y - 1 > -1 && x - 1 > -1)
	    {
		if (grid[x - 1][y - 1].getState() != player && grid[x - 1][y - 1].getState() > 0)
		{
		    up--;
		    left--;
		    while (up >= 0 && left >= 0 && grid[left][up].getState() != player && grid[left][up].getState() > 0) 
		    { 
			up--;
			left--; 
		    }
		    if (up >= 0 && left >= 0) 
		    {
			if (grid[left][up].getState() == player) 
			{	
			    ret_val[0] = left;
			    ret_val[1] = up; 
			}
		    }
		}
	    }
	    return ret_val;
	}
}

class square extends Panel implements MouseListener
{
	private int state;	    //black, white, or empty
	private boolean thinking;   //thinking about it
	
	private board parent;
	private int   pos_x;
	private int   pos_y;
	
	square(board paren, int x, int y, int st)
	{
		setBackground(new Color(0,175,0));
		parent = paren;
		state  = st;	    //empty
		pos_x = x;
		pos_y = y;
		thinking = false;   //nobody loves me
		addMouseListener(null);
	}
	
	square(board paren, int x, int y)
	{
		setBackground(new Color(0,175,0));
		parent = paren;
		state  = 0;	    //empty
		pos_x = x;
		pos_y = y;
		thinking = false;   //nobody loves me
		addMouseListener(this);
	}

	public void paint(Graphics g)
    	{
	    Rectangle r = getBounds();
	    
	    g.setColor(new Color(0,175,0));
	    g.fillRect(0,0,r.width,r.height);
	    switch (state)
	    {
		case 1:
		    g.setColor(Color.black);
		    g.fillOval(1, 1, r.width - 2, r.height - 2);
		break;
		case 2:
		    g.setColor(Color.white);
		    g.fillOval(1, 1, r.width - 2, r.height - 2);
		break;
	    }
	    if (thinking)
	    {
		g.setColor(Color.yellow);
		g.drawRect(1 , 1,r.width - 2 ,r.height - 2);
	    }
	}

	public void update( Graphics g )
	{
	    paint(g);
	}
	 
	public int getState() { return state; }
	
	public void setState(int st) 
	{ 
	    state = st; 
	    repaint();
	}
	
	public void highlight()
	{
	    thinking = true;
	    repaint();
	}
	
	public void deselect()
	{
	    thinking = false;
	    repaint();
	}
	
	public void mouseClicked(MouseEvent e)  
	{
	    if (parent.isSquareLegal(pos_x, pos_y, 1))
	    {
		deselect();
		parent.playSquare(pos_x, pos_y, 1);
		parent.Turn(2);
	    }
	    repaint();
	} 
        public void mouseEntered(MouseEvent e)  
	{
	    thinking = parent.isSquareLegal(pos_x, pos_y, 1);
	    repaint(); 
	} 
        public void mouseExited(MouseEvent e)   
	{
	    thinking = false;
	    repaint(); 
	} 	
	public void mousePressed(MouseEvent e)  { }
 	public void mouseReleased(MouseEvent e) { } 
}
	
