package league;

import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

/**
 * Comparator class that contains method that compares two teams based on
 * play-off ranking.
 * 
 * @author paultouma
 *
 */
public class RegularPlayoffComparator implements Comparator<Team> {
	@Override
	public int compare(Team t, Team other) {

		Boolean thisTeamIsDivisionLeader = new Boolean(t.getD().getDivisionLeaderForDate(t.getCurrentDate()).equals(t));
		Boolean otherTeamIsDivisionLeader = new Boolean(
				other.getD().getDivisionLeaderForDate(other.getCurrentDate()).equals(other));

		return new CompareToBuilder()
				.append(other.getStats().getCurrentWinningPercentage(), t.getStats().getCurrentWinningPercentage())
				.append(other.getStats().getCurrentWinningPercentageAgainstTeam(t),
						t.getStats().getCurrentWinningPercentageAgainstTeam(other))
				.append(otherTeamIsDivisionLeader, thisTeamIsDivisionLeader)
				.append(other.getStats().getCurrentDivisionWinningPercentage(),
						t.getStats().getCurrentDivisionWinningPercentage())
				.append(other.getStats().getCurrentConferenceWinningPercentage(),
						t.getStats().getCurrentConferenceWinningPercentage())
				.toComparison();
	}
}
