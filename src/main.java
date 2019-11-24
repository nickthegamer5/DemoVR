 
public class Main {
	
	private static PlaylistAgent playlistAgent;

	public static void main(String[] args) {
		
		int currentPlayTime = 0,
				maxPlayTime = 0;

		playlistAgent = new PlaylistAgent(currentPlayTime, maxPlayTime);
		playlistAgent.addGameToList("C:/gay", "Beat Saber");
		playlistAgent.addGameToList("C:/gay", "Asgards Wrath");
		playlistAgent.addGameToList("C:/gay", "Tilt Brush");
		
		MainPanel mainPanel = new MainPanel(playlistAgent);
		mainPanel.setVisible(true);
	}

}
