package main;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import league.Team;

/**
 * This class contains the main method for the program. Instantiates an NBA
 * simulator after parsing regular season information. Prints out the
 * elimination information for all teams at the end of the season.
 * 
 * @author paultouma
 *
 */
public class NBAHackathon2K17 {

	public static void main(String[] args) throws IOException {

		Map<String, Map<String, List<String>>> divConfInfoMap = Parser.readDivConfInfo();
		List<Map<String, String>> games = Parser.readGameInfo();

		NBAOrganization nbaOrganization = new NBAOrganization(divConfInfoMap, games);

		// set elimination dates for all teams
		nbaOrganization.setEliminationDays();

		// print out results
		for (Team t : nbaOrganization.getAllTeams()) {
			Date eliminationDate = t.getDateEliminated();
			System.out.println(t.getName() + ": " + (eliminationDate == null ? "playoffs" : eliminationDate));
		}
	}
}
