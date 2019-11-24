import java.awt.Component;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;

public class MainPanel extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	private JPanel contentPane;
	
	private PlaylistAgent gameDatabase;
	private PlaylistAgent currentGamePlaylist;
	
	private Timer timer = new Timer();
	
	private boolean isActive = false;
	private int currentGameIndex = 0;
	private Process runningGame;
	
	private int maxPlaytime = 5;
	private long currentPlayTime = -1;

	/**
	 * Create the frame.
	 */
	public MainPanel(PlaylistAgent gameDB, PlaylistAgent gamePlaylist) {
		gameDatabase = gameDB;
		currentGamePlaylist = gamePlaylist;
		
		setTitle("DemoVR");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 299, 350);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
	
		
		JMenu mnFileMenu = new JMenu("File");
		menuBar.add(mnFileMenu);
		
		JMenuItem mntmSaveDatabase = new JMenuItem("Save Game Database");
		mnFileMenu.add(mntmSaveDatabase);
		
		JMenuItem mntmLoadDatabase = new JMenuItem("Load Game Database");
		mnFileMenu.add(mntmLoadDatabase);
		
		JMenu mnGamePlaylist = new JMenu("Game Playlist");
		menuBar.add(mnGamePlaylist);
		
		
		JMenuItem mntmEditGamePlaylist = new JMenuItem("Edit Game Playlist / Database");
		
		
		mnGamePlaylist.add(mntmEditGamePlaylist);
		
		JMenuItem mntmSaveGamePlaylist = new JMenuItem("Save Game Playlist");
		mnGamePlaylist.add(mntmSaveGamePlaylist);
		
		JMenuItem mntmLoadGamePlaylist = new JMenuItem("Load Game Playlist");
		mnGamePlaylist.add(mntmLoadGamePlaylist);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList<String> listGamePlaylist = new JList<String>();
		listGamePlaylist.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Game Playlist", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		listGamePlaylist.setBackground(SystemColor.text);
		listGamePlaylist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listGamePlaylist.setBounds(10, 11, 273, 228);
		contentPane.add(listGamePlaylist);
		listGamePlaylist.setListData(currentGamePlaylist.getGameTitles());
		
		JButton btnStartStopPlaylist = new JButton("Start");
		btnStartStopPlaylist.setBounds(10, 248, 107, 23);
		contentPane.add(btnStartStopPlaylist);
		
		JLabel lblTime = new JLabel("Time: 0:00/5:00");
		lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTime.setBounds(185, 277, 98, 14);
		contentPane.add(lblTime);
		
		JSpinner spinnerTime = new JSpinner();
		
		// Every time the spinner changes, update the time label
		spinnerTime.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				maxPlaytime = ((int)spinnerTime.getValue());
				setMaxTime(lblTime, maxPlaytime);
			}
		});
		
		spinnerTime.setModel(new SpinnerNumberModel(5, 1, 60, 1));
		spinnerTime.setBounds(244, 249, 39, 20);
		contentPane.add(spinnerTime);
		
		JLabel lblTimePerGame = new JLabel("Time Per Game (Min):");
		lblTimePerGame.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTimePerGame.setBounds(115, 252, 126, 14);
		contentPane.add(lblTimePerGame);
		
		JLabel lblCurrentGame = new JLabel("Current Game: Idle");
		lblCurrentGame.setBounds(10, 277, 165, 14);
		contentPane.add(lblCurrentGame);
		
		// When the edit game playlist is selected...
		mntmEditGamePlaylist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// Create and show the playlist editor
				PlaylistEditorPanel playlistEditor = new PlaylistEditorPanel(gameDatabase, currentGamePlaylist);
				playlistEditor.setVisible(true);
				
				// Disable the main panel
				disablePanel(contentPane, menuBar);
				
				// When the panel is closed, re-enable the main panel
				playlistEditor.addWindowListener(new WindowAdapter(){
	                public void windowClosing(WindowEvent e){
	                	currentGamePlaylist = playlistEditor.currentGamePlaylist;
	                	enablePanel(contentPane, menuBar);
	                	listGamePlaylist.setListData(currentGamePlaylist.getGameTitles());
	                	if (playlistEditor.addGamePanel != null)
	                		playlistEditor.addGamePanel.setVisible(false);
	                }
	            });
			}
		});
		
		btnStartStopPlaylist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentGamePlaylist.getGameList().size() == 0) return;
				
				if (!isActive)
				{
					btnStartStopPlaylist.setText("Stop");
					isActive = !isActive;
					
					spinnerTime.setEnabled(false);
					for (Component ob : menuBar.getComponents())
					{
						ob.setEnabled(false);
					}
					
					timer.scheduleAtFixedRate(new TimerTask() {
						@Override
						public void run() {
							if (runningGame != null)
								runningGame.destroyForcibly();
							if (currentGameIndex == currentGamePlaylist.getGameList().size())
							{
								runningGame.destroyForcibly();
								actionPerformed(e);
								return;
							}
							
							ProcessBuilder pb = new ProcessBuilder(currentGamePlaylist.getGameList().get(currentGameIndex).gamePath);
							try {
								runningGame = pb.start();
							} catch (IOException e) {
								JOptionPane.showMessageDialog(null, "Unexpected Error!: " + e.toString(), "ERROR!", JOptionPane.ERROR_MESSAGE);
								System.exit(-1);
							}
							
							setGame(lblCurrentGame, currentGamePlaylist.getGameList().get(currentGameIndex).gameName);
							
							currentGameIndex++;
						}
					}, 0, (long)(Long.parseLong(spinnerTime.getValue().toString())*60000)/currentGamePlaylist.getGameList().size());
					
					timer.scheduleAtFixedRate(new TimerTask() {
						@Override
						public void run() {
							currentPlayTime++;
							setCurrentTime(lblTime, currentPlayTime);
						}
					}, 0, 1000);
					
				} else
				{
					btnStartStopPlaylist.setText("Start");
					isActive = !isActive;
					
					spinnerTime.setEnabled(true);
					for (Component ob : menuBar.getComponents())
					{
						ob.setEnabled(true);
					}
					if (runningGame != null)
						runningGame.destroyForcibly();
					
					timer.cancel();
					timer.purge();
					timer = new Timer();
					currentGameIndex = 0;
					currentPlayTime = -1;
					setCurrentTime(lblTime, 0);
					setGame(lblCurrentGame, "Idle");
				}
			}
		});
	}
	
	// Set the time label max time
	public void setMaxTime(JLabel lblTime, int maxtime)
	{
		// Use regex to select everything after the / and replace it
		lblTime.setText(lblTime.getText().replaceAll("([^\\/]+$)", maxtime + ":00"));
	}
	
	public void setCurrentTime(JLabel lblTime, long currenttime)
	{
		// Use regex to select everything after the / and replace it
		int minutes = (int) Math.floor(currenttime/60);
		int seconds = (int) Math.floor((currenttime - Math.floor(currenttime/60)*60));
		
		if (String.valueOf(seconds).length() == 0)
			lblTime.setText("Time: " + minutes + ":00/"+maxPlaytime+":00");
		if (String.valueOf(seconds).length() == 1)
			lblTime.setText("Time: " + minutes + ":0"+seconds+"/"+maxPlaytime+":00");
		if (String.valueOf(seconds).length() == 2)
			lblTime.setText("Time: " + minutes + ":"+seconds+"/"+maxPlaytime+":00");
	}
	
	public void setGame(JLabel lblCurrentGame, String game)
	{
		
		// Use regex to select everything after the / and replace it
		lblCurrentGame.setText("Current Game: " + game);
	}
	
	// Disable the entire panel
	public void disablePanel(JPanel content, JMenuBar mnuBar)
	{
		for (Component ob : content.getComponents())
		{
			ob.setEnabled(false);
		}
		for (Component ob : mnuBar.getComponents())
		{
			ob.setEnabled(false);
		}
	}
	
	// Enable the entire panel
	public void enablePanel(JPanel content, JMenuBar mnuBar)
	{
		for (Component ob : content.getComponents())
		{
			ob.setEnabled(true);
		}
		for (Component ob : mnuBar.getComponents())
		{
			ob.setEnabled(true);
		}
	}

}
