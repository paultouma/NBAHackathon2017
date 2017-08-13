package stats;

/**
 * This class is used to store information regarding any winning percentage.
 * 
 * @author paultouma
 *
 */
public class WinningPercentage implements Comparable<WinningPercentage> {

	private int numWins, totalGames;

	public int getNumWins() {
		return numWins;
	}

	public WinningPercentage() {
		numWins = 0;
		totalGames = 0;
	}

	public WinningPercentage(WinningPercentage other) {
		this.numWins = other.numWins;
		this.totalGames = other.totalGames;
	}

	public void wonGame(boolean wonGame) {
		if (wonGame) {
			numWins++;
		}
		totalGames++;
	}

	@Override
	public int compareTo(WinningPercentage other) {
		return (int) ((getWinningPercentage() - other.getWinningPercentage()) * 100);
	}

	public void addWins(int numWinsToAdd) {
		numWins += numWinsToAdd;
	}

	public int getTotalGames() {
		return totalGames;
	}

	public double getWinningPercentage() {
		return totalGames == 0 ? 0 : (double) numWins / totalGames;
	}
}
