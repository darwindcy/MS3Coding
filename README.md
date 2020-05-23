# MS3Coding

Purpose:
This repository holds the files of Darwin Charles Yadav's application of MS3 Jr. Software Engineer coding challenge

Requirements:

Important:

The application takes about 25 seconds to run for the 5000+ data

This application requires 3 opensource libraries

These are attached in the application within the folder Dependencies.

For the effective running of this application, include these libraries.
1. opencsv
2. sqlite-jdbc
3. commonns-lang3

Requirements: 
The input file should be one directory above the application.
The output files are also present one directory above the application.

Overview of approach, design choices, and assumptions:

Assumptions: 
5th Column - String object of image filename(Interpreted as string in the application)

7th Column - String object to include the '$' sign in the database

8th, 9th Column - Random Booleans named bool1, bool2 for convenience.

Initially, the approach was to create a class of record that creates record objects
from the csv file. It later occurred that a this was unnecessary. The class is still available for access.

The application was first designed using scanner and filereader to read data and then split each line
with ','. It worked for most part but the output was incorrect since the image data has ',' within them which is the character used to split csv file data.

In the end, opencsv was used rather than custsom writing functions to parse data since it was simpler.

After a file is opened, 
Opencsv allows splitting each row of data into a string array.

The string array is checked for null/empty data and a row with 0 empty/null data is stored in the database.
A database is created at the beginning when a file is about to be read. 

Every creation of database searches for an existing database with the specified filename and deletes it.
to avoid appending data to the old file.

Conversely, the rows with 1+ null/empty data is stored in <filename-bad>.csv file.
For each row of data, a tracker is used to count the total number of records, valid, and invalid records which
is stored in the log file at the end.
