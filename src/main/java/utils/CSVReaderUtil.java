package utils;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import data.TestData;

public class CSVReaderUtil {
	public static List<TestData> readUsersFromCSV(String fileName) {
        List<TestData> users = new ArrayList<>();
        
        // Get the file from the resources folder
        URL resource = CSVReaderUtil.class.getClassLoader().getResource(fileName);
        
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        }

        try (CSVReader reader = new CSVReader(new FileReader(resource.getFile()))) {
            List<String[]> lines = reader.readAll();

            for (String[] line : lines.subList(1, lines.size())) { // Skip header
                int id = Integer.parseInt(line[0]);
                String name = line[1];
                String emailId = line[2];

                users.add(new TestData(id, name, emailId));
            }

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return users;
    }

}
