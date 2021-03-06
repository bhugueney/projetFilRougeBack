package com.myIGCoach.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
// import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.myIGCoach.models.Category;
import com.myIGCoach.models.Ingredient;
import com.myIGCoach.models.User;
import com.myIGCoach.repository.CategoryRepository;
import com.myIGCoach.repository.IngredientRepository;
import com.myIGCoach.repository.UserRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Component
public class DataBaseInitialization implements ApplicationListener<ContextRefreshedEvent> {

    private final static char CSV_SEPARATOR = ';';
    private final static String EMPTY_FIELD = "-";

    private final static String SYS_ADMIN_EMAIL = "pf1.fhg@gmail.com";
    
    @Value( "${client.url}" )
    private String clientUrl;
	
    
    @Value( "${dataBaseInitialization.importFolder}" )
    private  String importFolder;

    @Value( "${dataBaseInitialization.backupFolder}" )
    private String backupFolder;

    @Value( "${dataBaseInitialization.alimentsFile}" )
    private String alimentsFile;

    @Value( "${dataBaseInitialization.glycemicFile}" )
    private  String glycemicFile;

    
    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    private IngredientRepository ingredientRepository;

    @Inject
    private UserRepository userRepository;

    private static int categoriesAdded = 0;

    
    
    /**
     * check if a folder exists, if not try to add current workdir and retry
     *
     * @param folderPath  folder to check
     */
    private String checkFolder(String folderPath) {
    	// System.out.println("Checking existence for " + folderPath);
		
    	File dir = new File(folderPath);
    	if( dir.exists() ) {
    		// folderpath exists so we return it
    		System.out.println(folderPath + " exists !");
    		return folderPath;
    	} else {
    		// folder path doesn't exists, so we try to check if exists in current working dir
    		System.out.println(folderPath + " doesn't exists !");
    		String testFolderPath = System.getProperty("user.dir");
    		testFolderPath += folderPath.charAt(0) == File.separatorChar  ? "" : File.separator; // if folder path doesn't begins with a file separator char, add one.
    		testFolderPath += folderPath;
    		System.out.println("Checking existence for " + testFolderPath);
    		dir = new File(testFolderPath);
    		if( dir.exists() ) {
    			// if we found the desired folder in the current working dir, we return the new path
        		System.out.println(testFolderPath + " exists !");
        		return testFolderPath;
        	} else {
        		// if the path wasn't found we return the initial path
        		System.out.println(testFolderPath + " doesn't exists !");
        		System.err.println("Warning : the path " + folderPath + " seems not exists !");
        		return folderPath;
        	}
    	}

    	
    }
    
    
    /**
     * Regarding a file name, a backup of this file is done in the backup directory with dated extension
     *
     * @param filePath file path of file to rename
     */
    private void backupFile(String fileName) {
    	
    	// Compute fileLocation
    	String fileLocation = "" + checkFolder(this.importFolder);
    	fileLocation += fileLocation.charAt(fileLocation.length() - 1) == File.separatorChar  ? "" : File.separator;   // add a fileseparator char at end if needed
        fileLocation += fileName;
        
        
        File fileToBackUp = new File(fileLocation);
        if (!fileToBackUp.exists()) {
            System.err.println(fileLocation + " doesn't exists... No backup to perform");
            return;
        }
        
        
        // Creation of dated extension
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        String extension = dtf.format(now);

        // Compute fileBackupLocation
        String fileBackupLocation = "" + checkFolder(this.backupFolder);
        fileBackupLocation += fileLocation.charAt(fileBackupLocation.length() - 1) == File.separatorChar  ? "" : File.separator;   // add a fileseparator char at end if needed
        fileBackupLocation += fileName + "." + extension;
        
        // Creation of new backup file
        File backupFile = new File(fileBackupLocation);


        if (!fileToBackUp.renameTo(backupFile)) {
            System.err.println("Warning !!! " + fileLocation + " has not been renamed ! ");
        } else {
            System.out.println(fileLocation + " has been renamed: " + fileBackupLocation);
        }

    }
    
    
    

    /**
     * This method return an existing category or a new one, matching given criteria
     *
     * @param categoryName : name of the category
     * @param parent       : category parent.
     * @return : category matching given criteria
     */
    private Category getCategory(String categoryName, Category parent) {

        List<Category> existingCategories;
        Category categoryToReturn;

        if (parent == null) {
            existingCategories = this.categoryRepository.findByName(categoryName);
        } else {
            existingCategories = this.categoryRepository.findByNameAndParent(categoryName, parent);
        }

        if (existingCategories == null || existingCategories.size() == 0) {
            categoryToReturn = new Category();
            categoryToReturn.setName(categoryName);
            if (parent != null) {
                categoryToReturn.setParent(parent);
            }
            categoriesAdded++;
            return this.categoryRepository.save(categoryToReturn);
        }

        return existingCategories.get(0);
    }

    /**
     * This function return a Double value regarding text value provided
     *
     * @param fieldValue : String containing field value
     * @return : Null if fieldValue doesn't match a decimal value otherwise
     * corresponding Double value
     */

    private Double getDoubleValueFromField(String fieldValue) {
        // RegExp used to check decimal value
        final String d = "\\d+([,.]\\d*)?";

        if (fieldValue == null || fieldValue.isEmpty() || fieldValue.equals(EMPTY_FIELD)) {
            return null;
        }

        if (fieldValue.matches(d)) {
            fieldValue = fieldValue.replace(',', '.');
            return (new Double(fieldValue));
        }

        return null;
    }

    
    
    
    
    
    
    /**
     * Loads food data into Food table. Reads csv file to import food group data.
     * This function is used to import a multi column file as food items.
     * * Loaded file will be renamed at the end of the process.
     *
     * @param foodFileLocation the location of csv file to import
     */
    private final void loadFoodTable(String fileName) {

        boolean backupIsNeeded = false;
        CSVParser csvParser = new CSVParserBuilder().withSeparator(CSV_SEPARATOR).build();

        // Compute fileLocation
    	String fileLocation = "" + checkFolder(this.importFolder);
        fileLocation += fileLocation.charAt(fileLocation.length() - 1) == File.separatorChar  ? "" : File.separator;   // add a fileseparator char at end if needed
        fileLocation += fileName;
        
        
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(fileLocation)).withCSVParser(csvParser)
                .build()) {

            // Reading all file content into fileLineList.
            List<String[]> fileLineList = reader.readAll();

			/*
             * For each file line : - check if each filled category if it exists - if a
			 * category is present, we get it - if a category is unknown, we create it and
			 * link it to previous one - check existence of aliment (looking by name) - if
			 * it is existing -> we update this aliment - if not, we create this aliment and
			 * link it to previous category
			 * 
			 */

            Optional<User> userSearched = this.userRepository.findByEmail(SYS_ADMIN_EMAIL);

            int ingredientsToAdd = 0;
            int ingredientsToUpdate = 0;
            categoriesAdded = 0;

            User sysAdmin = (userSearched.isPresent() ? userSearched.get() : null);

            if (sysAdmin == null) {
                System.err.println("sysAdmin user doesn't exist in database ! ");
            } else {

                System.out.println("Ingredients creation or updating from " + fileLocation + " ...");

                List<Ingredient> ingredientList = new ArrayList<Ingredient>();

                for (String[] fileLine : fileLineList) {

                    if (fileLine[IndexAlimentFile.IDX_NOM_ALIMENT] != null
                            && !fileLine[IndexAlimentFile.IDX_NOM_ALIMENT].isEmpty()
                            && !fileLine[IndexAlimentFile.IDX_NOM_ALIMENT].equals(EMPTY_FIELD)) {
                        // Process of aliment creation/update only if aliment name filled

                        // 3 first fields are definition of ingredient categories
                        Category previousCategory = null, currentCategory = null;
                        for (int iCategory = 0; iCategory <= 2; iCategory++) {
                            if (fileLine[iCategory] != null && !fileLine[iCategory].isEmpty()
                                    && !fileLine[iCategory].equals("-")) {
                                String categoryName = fileLine[iCategory];
                                currentCategory = getCategory(categoryName, previousCategory);
                                previousCategory = currentCategory;
                            }
                        }

                        // Ingredient can't be created without a category
                        if (currentCategory != null) {

                            // Getting ingredient information from data file
                            Ingredient food = new Ingredient(fileLine[IndexAlimentFile.IDX_NOM_ALIMENT],
                                    currentCategory, getDoubleValueFromField(fileLine[IndexAlimentFile.IDX_ENERGIE]),
                                    getDoubleValueFromField(fileLine[IndexAlimentFile.IDX_EAU]),
                                    getDoubleValueFromField(fileLine[IndexAlimentFile.IDX_PROTEINES]),
                                    getDoubleValueFromField(fileLine[IndexAlimentFile.IDX_GLUCIDES]),
                                    getDoubleValueFromField(fileLine[IndexAlimentFile.IDX_LIPIDES]),
                                    getDoubleValueFromField(fileLine[IndexAlimentFile.IDX_SUCRES]),
                                    getDoubleValueFromField(fileLine[IndexAlimentFile.IDX_AMIDON]),
                                    getDoubleValueFromField(fileLine[IndexAlimentFile.IDX_FIBRES_ALIMENTAIRES]),
                                    getDoubleValueFromField(fileLine[IndexAlimentFile.IDX_AG_SATURES]),
                                    getDoubleValueFromField(fileLine[IndexAlimentFile.IDX_AG_MONOINSATURES]),
                                    getDoubleValueFromField(fileLine[IndexAlimentFile.IDX_AG_POLYINSATURES]),
                                    getDoubleValueFromField(fileLine[IndexAlimentFile.IDX_SEL_CHLORURE_DE_SODIUM]),
                                    null, null, sysAdmin, true);

                            // Check if this ingredient already exists
                            List<Ingredient> searchResult = this.ingredientRepository
                                    .findByNameAndActiveIsTrueAndOwnerEmail(food.getName(), SYS_ADMIN_EMAIL);

                            if (searchResult != null && searchResult.size() > 0) {
                                // Only on ingredient is expected. If many results returned, first on will be
                                // use.
                                Ingredient foodToUpdate = searchResult.get(0);

                                if (!foodToUpdate.equals(food)) {
                                    // Update of this ingredient is necessary
                                    foodToUpdate.updateWithIngredientAttributes(food);
                                    ingredientList.add(foodToUpdate);
                                    ingredientsToUpdate++;
                                }
                            } else {
                                // new ingredient to create
                                ingredientList.add(food);
                                ingredientsToAdd++;
                            }
                        }
                    }
                }
                this.ingredientRepository.saveAll(ingredientList);
                System.out.println("Database refreshed:");
                System.out.println("   - Categories added : " + categoriesAdded);
                System.out.println("   - Ingredients added : " + ingredientsToAdd);
                System.out.println("   - Ingredients updated : " + ingredientsToUpdate);
                backupIsNeeded = true;
            }

        } catch (FileNotFoundException e) {
            System.out.println("No file available for database refreshing at " + fileLocation);
            // We do nothing John Snow, as this is for Dev DB fill up
        } catch (IOException e) {
            e.printStackTrace();
            // We do nothing John Snow, as this is for Dev DB fill up
        }

        // One food aliment file has been processed, this file is backed up with '.old' extension
        if (backupIsNeeded) {
            backupFile(fileName);
        }
    }

    
    
    
    
    
    
    /**
     * Load Glycemic file data tu update food table.
     * All food without glycemic index will be deleted at the end of this function.
     * Loaded file will be renamed at the end of the process.
     *
     * @param foodLocation the location of csv file to import
     */
    private void loadGlycemicFile(String fileName) {
        
        // Compute fileLocation
    	String fileLocation = "" + checkFolder(this.importFolder);
        fileLocation += fileLocation.charAt(fileLocation.length() - 1) == File.separatorChar  ? "" : File.separator;   // add a fileseparator char at end if needed
        fileLocation += fileName;
        
        boolean backupIsNeeded = false;
        CSVParser csvParser = new CSVParserBuilder().withSeparator(CSV_SEPARATOR).build();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(fileLocation)).withCSVParser(csvParser).build()) {

        	System.out.println("Glycemic index updating from " + fileLocation + " ..." );
             
        	
            List<String[]> fileLineList = reader.readAll();
            List<Ingredient> foodToUpdateList = new ArrayList<>();

            int readGlycemicIndex = 0;
            int ingredientUpdated = 0;

            for (String[] fileLine : fileLineList) {
                readGlycemicIndex++;
                List<Ingredient> possibleFoodsToUpdate = this.ingredientRepository.findByNameContainingAndActiveIsTrueAndOwnerEmail(fileLine[0], SYS_ADMIN_EMAIL);

                Double glycemicIndex = getDoubleValueFromField(fileLine[1]);
                for (Ingredient foodToUpdate : possibleFoodsToUpdate) {
                    foodToUpdate.setGlycemicIndex(glycemicIndex);
                    foodToUpdateList.add(foodToUpdate);
                    ingredientUpdated++;
                }
            }

            this.ingredientRepository.saveAll(foodToUpdateList);

            // Delete of ingredient without GlycemicIndex
            this.ingredientRepository.deleteIngredientWithoutGlycemicIndex();

            System.out.print("Glycemic index updated");
            System.out.println("   - Data read : " + readGlycemicIndex);
            System.out.println("   - Ingredients added : " + ingredientUpdated);

            backupIsNeeded = true;

        } catch (FileNotFoundException e) {
            // We do nothing John Snow, as this is for Dev DB fill up
            System.out.println("No file available for Glycemic Index refresh at " + fileLocation);
        } catch (IOException e) {
            // We do nothing John Snow, as this is for Dev DB fill up
            e.printStackTrace();
        }

        if (backupIsNeeded) {
            // Once Glycemic file has been processed, a backup is performed
            backupFile(fileName);
        }
    }

    
    
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
    	
    	System.out.println("Cors Allow requests from this client url : " + clientUrl);
    	
    	//System.out.println("Current workdir = " + System.getProperty("user.dir"));
    	System.out.println("Refreshing database from " + this.importFolder);
        loadFoodTable(this.alimentsFile);
        loadGlycemicFile(this.glycemicFile);
    }

}
