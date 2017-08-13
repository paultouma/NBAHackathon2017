package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import league.League;
import league.RegularPlayoffComparator;
import league.Team;

/**
 * This class represents the state of the NBA as a whole.
 * 
 * @author paultouma
 *
 */
public class NBAOrganization extends League {

	private final int NUMGAMESINREGULARSEASON = 82;

	public NBAOrganization(Map<String, Map<String, List<String>>> divConfInfoMap, List<Map<String, String>> games) {
		super(divConfInfoMap, games);
	}

	/**
	 * This method calculates and sets the elimination date for all of the teams
	 * in the league.
	 */
	public void setEliminationDays() {

		for (Team current : teams.values()) {

			List<Team> playoffTeams = current.getC().getPlayoffTeams(datesOfAllGames.get(datesOfAllGames.size() - 1));

			if (!playoffTeams.contains(current)) {

				for (int i = datesOfAllGames.size() - 1; i >= 0; i--) {
					Date previousDate = datesOfAllGames.get(i - 1);
					Date currentDate = datesOfAllGames.get(i);

					current.setCurrentDate(previousDate);

					int possibleMoreWins = (NUMGAMESINREGULARSEASON
							- current.getStats().getCurrentWinningPercentage().getTotalGames());
					int currentWins = current.getStats().getCurrentWinningPercentage().getNumWins();

					int currentEightSeedWins = current.getC().getPlayoffTeams(previousDate).get(7).getStats()
							.getCurrentWinningPercentage().getNumWins();

					if (currentWins + possibleMoreWins > currentEightSeedWins) {
						current.setDateEliminated(currentDate);
						break;
					} else if (currentWins + possibleMoreWins == currentEightSeedWins) {

						List<Team> simulatedTeams = new ArrayList<Team>();
						List<Team> betterRankingTeams = current.getC().getTeamsAbove(current, previousDate);

						for (Team t : betterRankingTeams) {
							simulatedTeams.add(t.simulateAfterDate(previousDate, false, betterRankingTeams));
						}

						Team currentSim = current.simulateAfterDate(previousDate, true, betterRankingTeams);

						simulatedTeams.add(currentSim);

						Collections.sort(simulatedTeams, new RegularPlayoffComparator());

						if (simulatedTeams.subList(0, 8).contains(currentSim)) {
							current.setDateEliminated(currentDate);
							break;
						}
					}
				}
			}
		}
	}
}
