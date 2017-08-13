package stats;

/**
 * This class is used to store information regarding any winning percentage.
 * 
 * @author paultouma
 *
 */
public class WinningPercentage implements Comparable<WinningPercentage> {

	private int numWins, totalGames;

	/**
	 * Constructor
	 */
	public WinningPercentage() {
		numWins = 0;
		totalGames = 0;
	}

	/**
	 * Constructor for cloning previous winning percentage
	 * 
	 * @param other
	 *            - other winning percentage
	 */
	public WinningPercentage(WinningPercentage other) {
		this.numWins = other.numWins;
		this.totalGames = other.totalGames;
	}

	/**
	 * Update values for either winning or losing a game
	 * 
	 * @param wonGame
	 *            - whether team won or lost game
	 */
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

	/**
	 * Returns total amount of games played for this winning percentage stat
	 * 
	 * @return total amount of games
	 */
	public int getTotalGames() {
		return totalGames;
	}

	/**
	 * Returns the winning percentage
	 * 
	 * @return winning percentage
	 */
	public double getWinningPercentage() {
		return totalGames == 0 ? 0 : (double) numWins / totalGames;
	}

	/**
	 * Returns the total number of wins for this winning percentage
	 * 
	 * @return number of wins
	 */
	public int getNumWins() {
		return numWins;
	}

}
