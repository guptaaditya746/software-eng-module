package de.buw.se.cli;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class DataStoreCsv {

    private static final String FILE_NAME = "src/main/resources/data.csv";

    /**
     * Read filenames from the CSV file
     *
     * @return list of files
     */
    public static List<String> listFiles() {
        List<String> listOfFiles = new ArrayList<>();
        // open CSV file and read authors of each entry
        try (Reader reader = Files.newBufferedReader(Paths.get(FILE_NAME)); CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .build())) {
            for (CSVRecord csvRecord : csvParser) {
                String filename = csvRecord.get("filename");
                listOfFiles.add(filename);
            }
        } catch (IOException e) {

        }
        return listOfFiles;
    }

    /**
     * Add a new file to the CSV
     *
     * @param fileOwner owner of the file
     * @param title title of the file
     * @param category category of the file
     * @param filename name of the file
     */
    public static void addFile(String fileOwner, String title, String category, String filename) {
        // add a new record to the CSV file
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_NAME),
                StandardOpenOption.APPEND, StandardOpenOption.CREATE); CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.builder()
                        .build())) {
            csvPrinter.printRecord(fileOwner, title, category, filename);
            csvPrinter.flush();
        } catch (IOException e) {

        }
    }

    /**
     * Delete a file record from the CSV
     *
     * @param filename name of the file to delete
     * @return true if deletion was successful, false otherwise
     */
    public static boolean deleteFile(String filename) {
        try {
            // Read all records
            List<CSVRecord> records;
            try (Reader reader = Files.newBufferedReader(Paths.get(FILE_NAME)); CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.builder()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setIgnoreHeaderCase(true)
                    .setTrim(true)
                    .build())) {
                records = csvParser.getRecords();
            }

            // Find the record with the given filename and remove it
            boolean found = false;
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_NAME), StandardOpenOption.TRUNCATE_EXISTING); CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.builder()
                    .setHeader("fileOwner", "title", "category", "filename")
                    .build())) {
                for (CSVRecord record : records) {
                    if (!record.get("filename").equals(filename)) {
                        csvPrinter.printRecord(record.toMap().values());
                    } else {
                        found = true;
                    }
                }
                csvPrinter.flush();
            }

            return found;
        } catch (IOException e) {
            return false;
        }
    }
}

// package de.buw.se.cli;
// import java.io.BufferedWriter;
// import java.io.IOException;
// import java.io.Reader;
// import java.nio.file.Files;
// import java.nio.file.Paths;
// import java.nio.file.StandardOpenOption;
// import java.util.ArrayList;
// import java.util.List;
// import org.apache.commons.csv.CSVFormat;
// import org.apache.commons.csv.CSVParser;
// import org.apache.commons.csv.CSVPrinter;
// import org.apache.commons.csv.CSVRecord;
// public class DataStoreCsv {
//   private static final String FILE_NAME = "src/main/resources/data.csv";
//   /**
//    * Read filenames from the CSV file
//    * 
//    * @return list of files
//    */
//   public static List<String> listFiles() {
//       List<String> listOfFiles = new ArrayList<>();
//       // open CSV file and read authors of each entry
//       try (Reader reader = Files.newBufferedReader(Paths.get(FILE_NAME));
//            @SuppressWarnings("deprecation")
//            CSVParser csvParser = new CSVParser(reader,
//                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
//           for (CSVRecord csvRecord : csvParser) {
//               String filename = csvRecord.get("filename");
//               listOfFiles.add(filename);
//           }
//       } catch (IOException e) {
//           e.printStackTrace();
//       }
//       return listOfFiles;
//   }
//   /**
//    * Add a new file to the CSV
//    * 
//    * @param fileOwner owner of the file
//    * @param title title of the file
//    * @param category category of the file
//    * @param filename name of the file
//    */
//   public static void addFile(String fileOwner, String title, String category,String filename) {
//       // add a new record to the CSV file
//       try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_NAME),
//               StandardOpenOption.APPEND, StandardOpenOption.CREATE);
//            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);) {
//           csvPrinter.printRecord(fileOwner, title, category, filename);
//           csvPrinter.flush();
//       } catch (IOException e) {
//           e.printStackTrace();
//       }
//   }
//       /**
//      * Delete a file record from the CSV
//      * 
//      * @param filename name of the file to delete
//      * @return true if deletion was successful, false otherwise
//      */
//     public static boolean deleteFile(String filename) {
//         try {
//             // Read all records
//             List<CSVRecord> records;
//             try (Reader reader = Files.newBufferedReader(Paths.get(FILE_NAME));
//                     CSVParser csvParser = new CSVParser(reader,
//                             CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
//                 records = csvParser.getRecords();
//             }
//             // Find the record with the given filename and remove it
//             boolean found = false;
//             try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_NAME), StandardOpenOption.TRUNCATE_EXISTING);
//                     CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("fileOwner", "title", "category", "filename"))) {
//                 for (CSVRecord record : records) {
//                     if (!record.get("filename").equals(filename)) {
//                         csvPrinter.printRecord(record);
//                     } else {
//                         found = true;
//                     }
//                 }
//                 csvPrinter.flush();
//             }
//             return found;
//         } catch (IOException e) {
//             e.printStackTrace();
//             return false;
//         }
//     }

  
// }  
