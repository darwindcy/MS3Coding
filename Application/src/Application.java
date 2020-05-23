// Programmer: Darwin Charles Yadav
// MS3 Jr. Software Engineer Coding challenge sample

import java.io.*;
import java.nio.file.Path;
import java.sql.*;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class Application {
    public static void main(String[] args) {
        Application application = new Application();
        application.run();
    }

    private void run() {
        String cd = System.getProperty("user.dir");
        Scanner s = new Scanner(System.in);
        File file = null;
        String inputFile = null;

        do {
            System.out.print("Enter the input file; Do not include the extension: ");
            inputFile = s.next();
            file = new File(cd + "/../" + inputFile + ".csv");
            System.out.println(file.toString());
            if (!file.isFile()) {
                System.out.println("Enter a valid filename and make sure it is in the project directory.");
            }
        } while (!file.isFile());

        String badFileName = inputFile + "-bad.csv";
        String logFileName = inputFile + ".log";
        File badFile = new File(cd + "/../" + badFileName);
        File logFile = new File(cd + "/../" + logFileName);

        int numReceived = 0;
        int numSuccessful = 0;
        int numFailed = 0;

        // filewriter for the bad filename
        FileWriter fr = null;
        // connection for database
        Connection c = null;
        // Reader for reading the CSV file
        CSVReader reader = null;

        try {
            reader = new CSVReader(new FileReader(file));
            String[] tempArray;
            try {
                fr = new FileWriter(badFile);
                c = createDB(cd, inputFile);
                createDBTable(c);

                // Since the first line of data is A,B,C,D,E,F,G,H,I,J read it here
                reader.readNext();

                while ((tempArray = reader.readNext()) != null) {
                    // counts the number of invalid data entry(here it is the empty/null value in field)
                    int emptyCount = 0;

                    for (String str :
                            tempArray) {
                        if (str.isEmpty()) {
                            emptyCount++;
                        }
                    }
                    if (emptyCount == 0) {
                        addDataToSQLiteFile(tempArray, c);
                        numSuccessful++;
                    } else {
                        addToBadFile(tempArray, fr);
                        numFailed++;
                    }
                    numReceived++;
                }
            } catch (IOException e) {
                System.err.println("File not available");
            } catch (CsvValidationException e) {
                e.printStackTrace();
            } finally {
                if (fr != null) {
                    fr.flush();
                    fr.close();
                }
            }

        } catch (IOException e) {
            System.err.println("File not Found");
        } finally {
            try {
                if (c != null)
                    c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter f = new FileWriter(logFile);
            f.append("Total data received = " + numReceived + "\n");
            f.append("Total data Successful = " + numSuccessful + "\n");
            f.append("Total data Failed = " + numFailed + "\n");
            f.flush();
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Log file error");
        }
        System.out.println("Total data received = " + numReceived);
        System.out.println("Total data Successful = " + numSuccessful);
        System.out.println("Total data Failed = " + numFailed);
        s.close();
    }

    private void createDBTable(Connection c) {
        Statement stmt = null;

        try {
            stmt = c.createStatement();
            String sqlstatement = "CREATE TABLE IF NOT EXISTS RECORD " +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " FNAME TEXT NOT NULL, " +
                    " LNAME TEXT NOT NULL, " +
                    " EMAIL TEXT NOT NULL, " +
                    " GENDER TEXT NOT NULL, " +
                    " IMAGE TEXT NOT NULL, " +
                    " COMPANY TEXT NOT NULL, " +
                    " MONEY TEXT, " +
                    " BOOL1 BOOLEAN, " +
                    " BOOL2 BOOLEAN, " +
                    " CITY TEXT )";
            stmt.executeUpdate(sqlstatement);
            System.out.println("Table created/present there");
        } catch (SQLException e) {
            System.out.println("Table cannot be created");
        }

    }

    private void addDataToSQLiteFile(String[] tempArray, Connection c) {
        final String INSERT_SQL = "INSERT INTO RECORD(FNAME, LNAME, EMAIL, GENDER, IMAGE, COMPANY, MONEY, BOOL1, BOOL2, CITY) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            c.setAutoCommit(false);
            ps = c.prepareStatement(INSERT_SQL);

            ps.setString(1, tempArray[0]);
            ps.setString(2, tempArray[1]);
            ps.setString(3, tempArray[2]);
            ps.setString(4, tempArray[3]);
            ps.setString(5, tempArray[4]);
            ps.setString(6, tempArray[5]);
            ps.setString(7, tempArray[6]);
            ps.setBoolean(8, Boolean.parseBoolean(tempArray[7]));
            ps.setBoolean(9, Boolean.parseBoolean(tempArray[8]));
            ps.setString(10, tempArray[9]);

            ps.executeUpdate();

            c.commit();
            System.out.println("Records added successfully");
        } catch (Exception e) {
            System.out.println("Error while writing record to database");
        }

    }

    private Connection createDB(String path, String fileName) {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            File databaseFile = new File(path + "/../" + fileName + ".db/");
            if (databaseFile.exists()) {
                databaseFile.delete();
            }
            c = DriverManager.getConnection("jdbc:sqlite:" + path + "/../" + fileName + ".db/");
        } catch (Exception e) {
            System.err.println("No connection file found Error");
            System.exit(0);
        }
        System.out.println("Database created sucessfully");
        return c;
    }


    private void addToBadFile(String[] dataArray, FileWriter fileWriter) {
        try {
            for (int i = 0; i < dataArray.length; i++) {
                fileWriter.append(dataArray[i]);
                if (i != dataArray.length - 1) {
                    fileWriter.append(",");
                }
            }
            fileWriter.append("\n");
        } catch (IOException e) {
            System.err.println("Cannot write this data to file");
        }
    }
}
