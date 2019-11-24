import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddGamePanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldGamePath;
	private JTextField textFieldGameName;
	
	public Boolean isValidExit = false;
	public GamePath gamePathFinal;

	/**
	 * Create the frame.
	 */
	public AddGamePanel() {
		setTitle("Add Game");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 340, 121);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblGamePath = new JLabel("Game Path:");
		lblGamePath.setBounds(10, 11, 81, 14);
		contentPane.add(lblGamePath);
		
		textFieldGamePath = new JTextField();
		textFieldGamePath.setBounds(101, 8, 191, 20);
		contentPane.add(textFieldGamePath);
		textFieldGamePath.setColumns(10);
		
		JButton button = new JButton("...");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser("C:\\Program Files (x86)\\Steam\\steamapps\\common");
				fc.setDialogTitle("Select Game Executable");
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
		                "Executable & Batch", "exe", "bat", "cmd");
				fc.setFileFilter(filter);
				
				int rnt = fc.showOpenDialog(null);
		        if(rnt == JFileChooser.APPROVE_OPTION) {
		            textFieldGamePath.setText(fc.getSelectedFile().getPath());
		        }
			}
		});
		button.setBounds(302, 7, 23, 23);
		contentPane.add(button);
		
		textFieldGameName = new JTextField();
		textFieldGameName.setColumns(10);
		textFieldGameName.setBounds(101, 37, 224, 20);
		contentPane.add(textFieldGameName);
		
		JLabel lblGameName = new JLabel("Game Name:");
		lblGameName.setBounds(10, 40, 81, 14);
		contentPane.add(lblGameName);
		
		JButton btnAddGame = new JButton("Add Game");
		btnAddGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (addGame(textFieldGamePath.getText(), textFieldGameName.getText()))
					setVisible(false);
			}
		});
		btnAddGame.setBounds(10, 62, 315, 23);
		contentPane.add(btnAddGame);
	}
	
	public boolean addGame(String gamePath, String gameName)
	{
		if (gamePath.length() != 0 && gameName.length() != 0)
		{
			isValidExit = true;
			gamePathFinal = new GamePath(gamePath, gameName);
			return true;
		}
		return false;
	}
}
