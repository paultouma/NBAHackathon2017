package league;

import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

/**
 * Comparator class that contains method that compares two teams if tied for
 * division leader
 * 
 * @author paultouma
 *
 */
public class DivisionLeaderComparator implements Comparator<Team> {
	@Override
	public int compare(Team t, Team other) {

		return new CompareToBuilder()
				.append(other.getStats().getCurrentWinningPercentage(), t.getStats().getCurrentWinningPercentage())
				.append(other.getStats().getCurrentWinningPercentageAgainstTeam(t),
						t.getStats().getCurrentWinningPercentageAgainstTeam(other))
				.append(other.getStats().getCurrentDivisionWinningPercentage(),
						t.getStats().getCurrentDivisionWinningPercentage())
				.append(other.getStats().getCurrentConferenceWinningPercentage(),
						t.getStats().getCurrentConferenceWinningPercentage())
				.toComparison();
	}
}