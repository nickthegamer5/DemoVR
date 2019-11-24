import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Component;
import java.awt.event.ActionEvent;

public class PlaylistEditorPanel extends JFrame {

	private JPanel contentPane;
	
	private PlaylistAgent playlistAgent;
	private JTable table;
	
	public AddGamePanel addGamePanel;

	/**
	 * Create the frame.
	 */
	public PlaylistEditorPanel(PlaylistAgent plyAgt) {
		setTitle("Edit Game Database");
		setResizable(false);
		
		getContentPane().setLayout(null);
		playlistAgent = plyAgt;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 408, 273);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList<String> listCurrentGameDatabase = new JList();
		listCurrentGameDatabase.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Current Game Database", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		listCurrentGameDatabase.setBounds(10, 45, 382, 188);
		contentPane.add(listCurrentGameDatabase);
		JButton btnAddGame = new JButton("Add Game");
		btnAddGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disablePanel(contentPane);
				addGamePanel = new AddGamePanel();
				addGamePanel.setVisible(true);
				
				addGamePanel.addWindowListener(new WindowAdapter(){
	                public void windowClosing(WindowEvent e){
	                	
	                	System.out.println("out");
	                	contentPane.setEnabled(true);
	                	
	                	if (addGamePanel.isValidExit)
	                	{
	                		plyAgt.addGameToList(addGamePanel.gamePath.gamePath, addGamePanel.gamePath.gameName);
	                	}
	                	
	                	enablePanel(contentPane);
	                	listCurrentGameDatabase.setListData(playlistAgent.getGameTitles());
	                }
	            });
			}
		});
		btnAddGame.setBounds(10, 11, 185, 23);
		contentPane.add(btnAddGame);
		
		JButton btnRemoveGame = new JButton("Remove Game");
		btnRemoveGame.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (listCurrentGameDatabase.isSelectionEmpty()) return;
				
				playlistAgent.removeGameIndex(listCurrentGameDatabase.getSelectedIndex());
				listCurrentGameDatabase.setListData(playlistAgent.getGameTitles());
			}
		});
		btnRemoveGame.setBounds(207, 11, 185, 23);
		contentPane.add(btnRemoveGame);
		listCurrentGameDatabase.setListData(playlistAgent.getGameTitles());
		
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
