import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Color;
import javax.swing.JList;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.SpinnerNumberModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private SearchAgent searchAgent = new SearchAgent();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main frame = new main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public main() {
		
		searchAgent.run();
		
		setResizable(false);
		setTitle("DemoVR - Show VR To All!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 431);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel statusLable = new JLabel("Current Status: IDLE");
		statusLable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		statusLable.setBounds(5, 382, 200, 17);
		contentPane.add(statusLable);
		
		JButton btnStart = new JButton("START");
		btnStart.setEnabled(false);
		btnStart.setForeground(Color.GREEN);
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnStart.setBounds(10, 322, 195, 57);
		contentPane.add(btnStart);
		
		JButton btnStop = new JButton("STOP");
		btnStop.setEnabled(false);
		btnStop.setForeground(Color.RED);
		btnStop.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnStop.setBounds(209, 322, 195, 57);
		contentPane.add(btnStop);
		
		JList foundGamesList = new JList();
		foundGamesList.setEnabled(false);
		foundGamesList.setFont(new Font("Tahoma", Font.PLAIN, 14));
		foundGamesList.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Found Games", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		foundGamesList.setBounds(10, 40, 394, 96);
		contentPane.add(foundGamesList);
		
		JList currentGamesList = new JList();
		currentGamesList.setEnabled(false);
		currentGamesList.setFont(new Font("Tahoma", Font.PLAIN, 14));
		currentGamesList.setBorder(new TitledBorder(null, "Current Game Playlist", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		currentGamesList.setBounds(10, 186, 394, 125);
		contentPane.add(currentGamesList);
		
		JButton addGameBtn = new JButton("\u2193");
		addGameBtn.setEnabled(false);
		addGameBtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		addGameBtn.setBounds(20, 141, 39, 39);
		addGameBtn.setBorder(null);
		contentPane.add(addGameBtn);
		
		JButton removeGameBtn = new JButton("\u2191");
		removeGameBtn.setEnabled(false);
		removeGameBtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		removeGameBtn.setBounds(69, 141, 39, 39);
		removeGameBtn.setBorder(null);
		contentPane.add(removeGameBtn);
		
		JSpinner timePerSpinner = new JSpinner();
		timePerSpinner.setEnabled(false);
		timePerSpinner.setFont(new Font("Tahoma", Font.PLAIN, 14));
		timePerSpinner.setModel(new SpinnerNumberModel(5, 0, 60, 1));
		timePerSpinner.setBounds(278, 150, 126, 28);
		contentPane.add(timePerSpinner);
		
		JLabel lblTimePerGame = new JLabel("Time Per Game (Minutes)");
		lblTimePerGame.setEnabled(false);
		lblTimePerGame.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimePerGame.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTimePerGame.setBounds(118, 147, 161, 28);
		contentPane.add(lblTimePerGame);
		
		JLabel timeLabel = new JLabel("");
		timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		timeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		timeLabel.setBounds(215, 496, 189, 17);
		contentPane.add(timeLabel);
		
		JButton selectGameFolderBtn = new JButton("Add Game Folder");
		selectGameFolderBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JFileChooser f = new JFileChooser();
				f.setDialogTitle("Select Game Folder");
		        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
		        f.showSaveDialog(null);

		        f.getSelectedFile();
			}
		});
		selectGameFolderBtn.setBounds(10, 11, 394, 23);
		contentPane.add(selectGameFolderBtn);
		contentPane.setVisible(true);
		
		
	}
}
