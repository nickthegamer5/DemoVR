 
public class Main {
	
	private static PlaylistAgent gameDatabase;
	private static PlaylistAgent currentGamePlaylist;

	public static void main(String[] args) {

		gameDatabase = new PlaylistAgent();
		gameDatabase.addGameToList("C:/gay", "Beat Saber");
		gameDatabase.addGameToList("C:/gay", "Asgards Wrath");
		gameDatabase.addGameToList("C:/gay", "Tilt Brush");
		
		currentGamePlaylist = new PlaylistAgent();
		
		MainPanel mainPanel = new MainPanel(gameDatabase, currentGamePlaylist);
		mainPanel.setVisible(true);
	}

}
