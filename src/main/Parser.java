package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * This class parses the NBA regular season information from
 * local .xlsx file.
 * @author paultouma
 *
 */
public class Parser {

	/**
	 * This method reads and parses Division/Conference information for all teams.
	 * @return map containing Division/Conference information 
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static Map<String, Map<String, List<String>>> readDivConfInfo() throws IOException {

		Map<String, Map<String, List<String>>> divConfInfoMap = new HashMap<String, Map<String, List<String>>>();

		File myFile = new File("Analytics_Attachment.xlsx");
		FileInputStream fis = new FileInputStream(myFile);
		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);
		Iterator<Row> rowIterator = mySheet.iterator();

		rowIterator.next();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {

				Cell cell = cellIterator.next();

				String teamName = cell.getStringCellValue();
				cell = cellIterator.next();
				String divisionName = cell.getStringCellValue();
				cell = cellIterator.next();
				String conferenceName = cell.getStringCellValue();

				Map<String, List<String>> divisionsInConference = divConfInfoMap.get(conferenceName);

				if (divisionsInConference == null) {
					divisionsInConference = new HashMap<String, List<String>>();
				}

				List<String> teamsInDivision = divisionsInConference.get(divisionName);
				if (teamsInDivision == null) {
					teamsInDivision = new ArrayList<String>();
				}

				teamsInDivision.add(teamName); // add the team name to list
				divisionsInConference.put(divisionName, teamsInDivision); // add
																			// division
				divConfInfoMap.put(conferenceName, divisionsInConference);
			}
		}

		return divConfInfoMap;
	}

	/**
	 * This method reads and parses all Game information for all games from the regular season.
	 * @return map containing all game information 
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static List<Map<String, String>> readGameInfo() throws IOException {

		List<Map<String, String>> listGameInfo = new ArrayList<Map<String, String>>();

		File myFile = new File("Analytics_Attachment.xlsx");
		FileInputStream fis = new FileInputStream(myFile);
		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
		XSSFSheet mySheet = myWorkBook.getSheetAt(1);
		Iterator<Row> rowIterator = mySheet.iterator();

		rowIterator.next();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {

				Cell cell = cellIterator.next();

				String date = cell.getDateCellValue().toString();
				cell = cellIterator.next();
				String homeTeam = cell.getStringCellValue();
				cell = cellIterator.next();
				String awayTeam = cell.getStringCellValue();
				cell = cellIterator.next();
				String homeScore = cell.getNumericCellValue() + "";
				cell = cellIterator.next();
				String awayScore = cell.getNumericCellValue() + "";
				cell = cellIterator.next();
				String winner = cell.getStringCellValue();

				Map<String, String> gameMap = new HashMap<String, String>();

				gameMap.put("date", date);
				gameMap.put("homeTeam", homeTeam);
				gameMap.put("awayTeam", awayTeam);
				gameMap.put("homeScore", homeScore);
				gameMap.put("awayScore", awayScore);
				gameMap.put("winner", winner);

				listGameInfo.add(gameMap);
			}
		}

		return listGameInfo;
	}
}
