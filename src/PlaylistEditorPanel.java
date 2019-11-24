import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class PlaylistEditorPanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private PlaylistAgent gameDatabase;
	public PlaylistAgent currentGamePlaylist;
	
	public AddGamePanel addGamePanel;

	/**
	 * Create the frame.
	 */
	public PlaylistEditorPanel(PlaylistAgent gameDB, PlaylistAgent gamePlaylist) {
		setTitle("Edit Game Database");
		setResizable(false);
		
		getContentPane().setLayout(null);
		gameDatabase = gameDB;
		currentGamePlaylist = gamePlaylist;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 408, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList<String> listCurrentGameDatabase = new JList<String>();
		listCurrentGameDatabase.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Current Game Database", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		listCurrentGameDatabase.setBounds(10, 45, 382, 188);
		contentPane.add(listCurrentGameDatabase);
		
		JButton btnAddGame = new JButton("Add Game To DB");
		btnAddGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disablePanel(contentPane);
				addGamePanel = new AddGamePanel();
				addGamePanel.setVisible(true);
				
				addGamePanel.addComponentListener ( new ComponentAdapter ()
			    {
			        public void componentHidden ( ComponentEvent e )
			        {
	                	if (addGamePanel.isValidExit)
	                	{
	                		for (GamePath game : gameDB.getGameList())
	                		{
	                			if (game.gamePath.toLowerCase() == addGamePanel.gamePathFinal.gamePath.toLowerCase() || game.gameName.toLowerCase() == addGamePanel.gamePathFinal.gameName.toLowerCase())
	                				return;
	                		}
	                		gameDB.addGameToList(addGamePanel.gamePathFinal.gamePath, addGamePanel.gamePathFinal.gameName);
	                	}
	                	
	                	enablePanel(contentPane);
	                	listCurrentGameDatabase.setListData(gameDatabase.getGameTitles());
			        }
			    } );
				
				addGamePanel.addWindowListener(new WindowAdapter(){
	                public void windowClosing(WindowEvent e){
	                	enablePanel(contentPane);
	                	listCurrentGameDatabase.setListData(gameDatabase.getGameTitles());
	                }
	            });
			}
		});
		btnAddGame.setBounds(10, 11, 185, 23);
		contentPane.add(btnAddGame);
		
		JButton btnRemoveGame = new JButton("Remove Game From DB");
		btnRemoveGame.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (listCurrentGameDatabase.isSelectionEmpty()) return;
				
				gameDatabase.removeGameIndex(listCurrentGameDatabase.getSelectedIndex());
				listCurrentGameDatabase.setListData(gameDatabase.getGameTitles());
			}
		});
		btnRemoveGame.setBounds(207, 11, 185, 23);
		contentPane.add(btnRemoveGame);
		
		JList<String> listCurrentGamePlaylist = new JList<String>();
		listCurrentGamePlaylist.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Current Game Playlist", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		listCurrentGamePlaylist.setBounds(10, 272, 320, 188);
		contentPane.add(listCurrentGamePlaylist);
		
		JButton btnAddToPlaylist = new JButton("Add To Playlist");
		btnAddToPlaylist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// If there is nothing selected in the current database, return
				if (listCurrentGameDatabase.isSelectionEmpty()) return;
				
				// If there is a game with the same title already in the
				// playlist then return
				for (String game : currentGamePlaylist.getGameTitles())
				{
					if (game.toLowerCase().compareTo(listCurrentGameDatabase.getSelectedValue().toLowerCase()) == 0)
						return;
				}
				
				currentGamePlaylist.addGameToList(gameDatabase.getGameList().get(listCurrentGameDatabase.getSelectedIndex()).gamePath, gameDatabase.getGameList().get(listCurrentGameDatabase.getSelectedIndex()).gameName);
				listCurrentGamePlaylist.setListData(currentGamePlaylist.getGameTitles());
			}
		});
		btnAddToPlaylist.setBounds(10, 238, 185, 23);
		contentPane.add(btnAddToPlaylist);
		
		JButton btnRemoveFromPlaylist = new JButton("Remove From Playlist");
		btnRemoveFromPlaylist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (listCurrentGamePlaylist.isSelectionEmpty()) return;

				currentGamePlaylist.removeGameIndex(listCurrentGamePlaylist.getSelectedIndex());
				listCurrentGamePlaylist.setListData(currentGamePlaylist.getGameTitles());
			}
		});
		btnRemoveFromPlaylist.setBounds(207, 238, 185, 23);
		contentPane.add(btnRemoveFromPlaylist);
		
		JButton btnDown = new JButton("\u2193");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listCurrentGamePlaylist.isSelectionEmpty()) return;
				if (listCurrentGamePlaylist.getSelectedIndex() == currentGamePlaylist.getGameList().size()-1) return;
				
				currentGamePlaylist.shiftIndexDown(listCurrentGamePlaylist.getSelectedIndex());
				listCurrentGamePlaylist.setListData(currentGamePlaylist.getGameTitles());
			}
		});
		
		btnDown.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDown.setBounds(340, 370, 52, 90);
		contentPane.add(btnDown);
		
		JButton btnUp = new JButton("\u2191");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listCurrentGamePlaylist.isSelectionEmpty()) return;
				if (listCurrentGamePlaylist.getSelectedIndex() == 0) return;
				
				currentGamePlaylist.shiftIndexUp(listCurrentGamePlaylist.getSelectedIndex());
				listCurrentGamePlaylist.setListData(currentGamePlaylist.getGameTitles());
			}
		});
		btnUp.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUp.setBounds(340, 272, 52, 90);
		contentPane.add(btnUp);
		listCurrentGameDatabase.setListData(gameDatabase.getGameTitles());
		listCurrentGamePlaylist.setListData(currentGamePlaylist.getGameTitles());
	}
	
	public void disablePanel(JPanel content)
	{
		for (Component ob : content.getComponents())
		{
			ob.setEnabled(false);
		}
	}
	
	public void enablePanel(JPanel content)
	{
		for (Component ob : content.getComponents())
		{
			ob.setEnabled(true);
		}
	}
}
