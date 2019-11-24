import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
	
	private PlaylistAgent playlistAgent;

	/**
	 * Create the frame.
	 */
	public MainPanel(PlaylistAgent plyAgnt) {
		playlistAgent = plyAgnt;
		
		setTitle("DemoVR");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 299, 350);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
	
		
		JMenu mnFileMenu = new JMenu("File");
		menuBar.add(mnFileMenu);
		
		JMenuItem mntmEditGameDatabase = new JMenuItem("Edit Game Database");
		mnFileMenu.add(mntmEditGameDatabase);
		
		JMenuItem mntmSaveDatabase = new JMenuItem("Save Game Database");
		mnFileMenu.add(mntmSaveDatabase);
		
		JMenuItem mntmLoadDatabase = new JMenuItem("Load Game Database");
		mnFileMenu.add(mntmLoadDatabase);
		
		JMenu mnGamePlaylist = new JMenu("Game Playlist");
		menuBar.add(mnGamePlaylist);
		
		
		JMenuItem mntmEditGamePlaylist = new JMenuItem("Edit Game Playlist");
		
		
		mnGamePlaylist.add(mntmEditGamePlaylist);
		
		JMenuItem mntmSaveGamePlaylist = new JMenuItem("Save Game Playlist");
		mnGamePlaylist.add(mntmSaveGamePlaylist);
		
		JMenuItem mntmLoadGamePlaylist = new JMenuItem("Load Game Playlist");
		mnGamePlaylist.add(mntmLoadGamePlaylist);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList<String> listGamePlaylist = new JList();
		listGamePlaylist.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Game Playlist", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		listGamePlaylist.setBackground(SystemColor.text);
		listGamePlaylist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listGamePlaylist.setBounds(10, 11, 273, 228);
		contentPane.add(listGamePlaylist);
		listGamePlaylist.setListData(playlistAgent.getGameTitles());
		
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
				playlistAgent.setMaxPlayTime((int)spinnerTime.getValue());
				setMaxTime(lblTime, playlistAgent.getMaxPlayTime());
				
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
						PlaylistEditorPanel playlistEditor = new PlaylistEditorPanel(playlistAgent);
						playlistEditor.setVisible(true);
						
						// Disable the main panel
						disablePanel(contentPane, menuBar);
						
						// When the panel is closed, re-enable the main panel
						playlistEditor.addWindowListener(new WindowAdapter(){
			                public void windowClosing(WindowEvent e){
			                	contentPane.setEnabled(true);
			                	enablePanel(contentPane, menuBar);
			                	listGamePlaylist.setListData(playlistAgent.getGameTitles());
			                	if (playlistEditor.addGamePanel != null)
			                		playlistEditor.addGamePanel.setVisible(false);
			                }
			            });
					}
				});
	}
	
	// Set the time label max time
	public void setMaxTime(JLabel lblTime, int maxtime)
	{
		// Use regex to select everything after the / and replace it
		lblTime.setText(lblTime.getText().replaceAll("([^\\/]+$)", maxtime + ":00"));
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
