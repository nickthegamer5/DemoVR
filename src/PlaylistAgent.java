import java.util.ArrayList;
import java.util.List;

public class PlaylistAgent {

	private int CurrentPlayTime,
				MaxPlayTime;
	
	private List<GamePath> gameList = new ArrayList<GamePath>();
	
	public PlaylistAgent(int currTime, int maxTime)
	{
		CurrentPlayTime = currTime;
		MaxPlayTime = maxTime;
	}
	
	public void addGameToList(String path, String name) { gameList.add(new GamePath(path, name)); }
	public void setMaxPlayTime(int i) { MaxPlayTime = i; }
	public void setCurrentPlayTime(int i) { CurrentPlayTime = i; }
	
	public List<GamePath> getGameList() { return gameList; }
	public int getMaxPlayTime() { return MaxPlayTime; }
	public int getCurrentPlayTime() { return CurrentPlayTime; }
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
