package com.myIGCoach.tools;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;



@Component
public class DataBaseInitialization  {

	private final static char CSV_SEPARATOR = ';';
	private final static char EMPTY_FIELD = '-';
	
	

	/**
	 * Loads food data into Food table. Reads csv file to import food group data.
	 * This function is used to import a multi column file as food items.
	 * 
	 * @param foodFileLocation
	 *          the location of csv file to import
	 */
	private final void loadFoodTable(String foodFileLocation) {

		CSVParser csvParser = new CSVParserBuilder().withSeparator(CSV_SEPARATOR).build();

		try (CSVReader reader = new CSVReaderBuilder(new FileReader(foodFileLocation)).withCSVParser(csvParser).build()) {

			final String d = "\\d+";

			// Reading all file content into fileLineList.
			List<String[]> fileLineList = reader.readAll();

			/*
			 * For each file line :
			 * - check if each filled category if it exists
			 *     - if a category is present, we get it
			 *     - if a category is unknown, we create it and link it to previous one
			 * - check existence of aliment (looking by name) 
			 *     - if it is existing -> we update this aliment
			 *     - if not, we create this aliment and link it to previous category
			 * 
			 */
			/*
			List<Food> newFoodItemList = new ArrayList<>();
			for (String[] fileLine : fileLineList) {
				String groupFullHierarchy = "";
				FoodGroup group;
				
				if (fileLine[2] != null && !fileLine[2].isEmpty() && !fileLine[2].equals("-")) {
					groupFullHierarchy += fileLine[2];
				}
				
				if (fileLine[1] != null && !fileLine[1].isEmpty() && !fileLine[1].equals("-")) {
					groupFullHierarchy += fileLine[1];
				}
				
				if (fileLine[0] != null && !fileLine[0].isEmpty() && !fileLine[0].equals("-")) {
					groupFullHierarchy += fileLine[0];
				}
				
				group = groupMap.get(groupFullHierarchy);

				Food food = new Food(fileLine[3], group, null, fileLine[4].matches(d) ? new BigDecimal(fileLine[4]) : null,
						fileLine[5].matches(d) ? new BigDecimal(fileLine[5]) : null,
						fileLine[6].matches(d) ? new BigDecimal(fileLine[6]) : null,
						fileLine[7].matches(d) ? new BigDecimal(fileLine[7]) : null,
						fileLine[8].matches(d) ? new BigDecimal(fileLine[8]) : null,
						fileLine[9].matches(d) ? new BigDecimal(fileLine[9]) : null,
						fileLine[10].matches(d) ? new BigDecimal(fileLine[10]) : null,
						fileLine[11].matches(d) ? new BigDecimal(fileLine[11]) : null,
						fileLine[12].matches(d) ? new BigDecimal(fileLine[12]) : null,
						fileLine[13].matches(d) ? new BigDecimal(fileLine[13]) : null,
						fileLine[14].matches(d) ? new BigDecimal(fileLine[14]) : null,
						fileLine[15].matches(d) ? new BigDecimal(fileLine[15]) : null,
						fileLine[16].matches(d) ? new BigDecimal(fileLine[16]) : null,
						fileLine[17].matches(d) ? new BigDecimal(fileLine[17]) : null,
						fileLine[18].matches(d) ? new BigDecimal(fileLine[18]) : null,
						fileLine[19].matches(d) ? new BigDecimal(fileLine[19]) : null,
						fileLine[20].matches(d) ? new BigDecimal(fileLine[20]) : null,
						fileLine[21].matches(d) ? new BigDecimal(fileLine[21]) : null,
						fileLine[22].matches(d) ? new BigDecimal(fileLine[22]) : null,
						fileLine[23].matches(d) ? new BigDecimal(fileLine[23]) : null,
						fileLine[24].matches(d) ? new BigDecimal(fileLine[24]) : null,
						fileLine[25].matches(d) ? new BigDecimal(fileLine[25]) : null,
						fileLine[26].matches(d) ? new BigDecimal(fileLine[26]) : null,
						fileLine[27].matches(d) ? new BigDecimal(fileLine[27]) : null,
						fileLine[28].matches(d) ? new BigDecimal(fileLine[28]) : null,
						fileLine[29].matches(d) ? new BigDecimal(fileLine[29]) : null,
						fileLine[30].matches(d) ? new BigDecimal(fileLine[30]) : null,
						fileLine[31].matches(d) ? new BigDecimal(fileLine[31]) : null,
						fileLine[32].matches(d) ? new BigDecimal(fileLine[32]) : null,
						fileLine[33].matches(d) ? new BigDecimal(fileLine[33]) : null,
						fileLine[34].matches(d) ? new BigDecimal(fileLine[34]) : null,
						fileLine[35].matches(d) ? new BigDecimal(fileLine[35]) : null,
						fileLine[36].matches(d) ? new BigDecimal(fileLine[36]) : null,
						fileLine[37].matches(d) ? new BigDecimal(fileLine[37]) : null,
						fileLine[38].matches(d) ? new BigDecimal(fileLine[38]) : null,
						fileLine[39].matches(d) ? new BigDecimal(fileLine[39]) : null,
						fileLine[40].matches(d) ? new BigDecimal(fileLine[40]) : null,
						fileLine[41].matches(d) ? new BigDecimal(fileLine[41]) : null,
						fileLine[42].matches(d) ? new BigDecimal(fileLine[42]) : null);

				newFoodItemList.add(food);
			}

			foodRepo.saveAll(newFoodItemList);
*/
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// We do nothing John Snow, as this is for Dev DB fill up
		} catch (IOException e) {
			e.printStackTrace();
			// We do nothing John Snow, as this is for Dev DB fill up
		}
	}

		
	private void loadGlycemicFile(String fileLocation) {

		CSVParser csvParser = new CSVParserBuilder().withSeparator(CSV_SEPARATOR).build();

		try (CSVReader reader = new CSVReaderBuilder(new FileReader(fileLocation)).withCSVParser(csvParser).build()) {

			List<String[]> fileLineList = reader.readAll();
		//	List<Food> foodToUpdateList = new ArrayList<>();
/*
			for (String[] fileLine : fileLineList) {
				List<Food> possibleFoodsToUpdate = foodRepo.findByNameContaining(fileLine[0]);
				BigDecimal glycemicIndex = (StringUtils.isNotEmpty(fileLine[1])) ? new BigDecimal(fileLine[1]) : null;
				for (Food foodToUpdate : possibleFoodsToUpdate) {
					foodToUpdate.setGlycemic_index(glycemicIndex);
					foodToUpdateList.add(foodToUpdate);
				}
			}

			foodRepo.saveAll(foodToUpdateList);
*/
		} catch (FileNotFoundException e) {
			// We do nothing John Snow, as this is for Dev DB fill up
			e.printStackTrace();
		} catch (IOException e) {
			// We do nothing John Snow, as this is for Dev DB fill up
			e.printStackTrace();
		}
	}

	/**
	 * Loads Daily Recommended Intakes based on ANSES recommendations.
	 */
	/*
	private final void loadDRITemplatesTable() {
		DRITemplate menTemplate = new DRITemplate("Homme", new BigDecimal(80), new BigDecimal(2600), new BigDecimal(0.15),
				new BigDecimal(0.45), new BigDecimal(0.4), new BigDecimal(100), new BigDecimal(30), new BigDecimal(950),
				new BigDecimal(1.3), new BigDecimal(11), new BigDecimal(150), new BigDecimal(420), new BigDecimal(2.8),
				new BigDecimal(700), new BigDecimal(70), new BigDecimal(12), new BigDecimal(750), new BigDecimal(1.5),
				new BigDecimal(1.8), new BigDecimal(17.4), new BigDecimal(5.8), new BigDecimal(1.8), new BigDecimal(330),
				new BigDecimal(4), new BigDecimal(110), new BigDecimal(15), new BigDecimal(10.5), new BigDecimal(0),
				new BigDecimal(0));

		DRITemplate womenTemplate = new DRITemplate("Femme", new BigDecimal(80), new BigDecimal(2100), new BigDecimal(0.15),
				new BigDecimal(0.45), new BigDecimal(0.4), new BigDecimal(100), new BigDecimal(30), new BigDecimal(950),
				new BigDecimal(1), new BigDecimal(13), new BigDecimal(150), new BigDecimal(360), new BigDecimal(2.5),
				new BigDecimal(700), new BigDecimal(70), new BigDecimal(9), new BigDecimal(650), new BigDecimal(1.2),
				new BigDecimal(1.5), new BigDecimal(14), new BigDecimal(4.7), new BigDecimal(1.5), new BigDecimal(330),
				new BigDecimal(4), new BigDecimal(110), new BigDecimal(15), new BigDecimal(9.9), new BigDecimal(0),
				new BigDecimal(0));

		driRepo.save(menTemplate);
		driRepo.save(womenTemplate);
	}
	*/
}

