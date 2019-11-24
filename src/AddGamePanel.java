import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddGamePanel extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldGamePath;
	private JTextField textFieldGameName;
	
	public Boolean isValidExit = false;
	public GamePath gamePath;

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
				if (lblGameName.getText().length() != 0 && lblGameName.getText().length() != 0)
				{
					isValidExit = true;
					gamePath = new GamePath(lblGamePath.getText(), lblGameName.getText());
				}
			}
		});
		btnAddGame.setBounds(10, 62, 315, 23);
		contentPane.add(btnAddGame);
	}
}
