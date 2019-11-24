import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlaylistAgent {
	
	private List<GamePath> gameList = new ArrayList<GamePath>();
	
	public PlaylistAgent()
	{
	}
	
	public void addGameToList(String path, String name) { gameList.add(new GamePath(path, name)); }
	
	public List<GamePath> getGameList() { return gameList; }
	public void shiftIndexUp(int index)
	{
		Collections.swap(gameList, index, index-1);
	}
	public void shiftIndexDown(int index)
	{
		Collections.swap(gameList, index, index+1);
	}
	public String[] getGameTitles() {
		String[] rnt = new String[gameList.size()];
		for (int k = 0; k != gameList.size(); k++)
			rnt[k] = gameList.get(k).gameName;
		return rnt;
	}
	
	public void removeGameIndex(int i)
	{
		gameList.remove(i);
	}
}
