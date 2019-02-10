/*
 * Copyright (c) 2019 Michael Male.
 *
 * This file, SplitFile.java, is part of FileSplitter.
 *
 * FileSplitter is free software: you can redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * FileSplitter is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along with FileSplitter.
 * If not, see <https://www.gnu.org/licenses/>.
 */

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * SplitFile - Splits a file based on a regular expression
 *
 * @author Michael Male
 * @version 1.0 RELEASE
 * @see FileUtils
 */
class SplitFile {
    private File fileToSplit;
    private File fileToSave;

    /**
     * Default constructor for objects of class SplitFile() that initially sets its members to null
     */
    SplitFile() {
        this.fileToSplit = null;
        this.fileToSave = null;
    }

    /**
     * Sets the file to be split
     *
     * @param f File object representing the file that is to be split
     */
    void setFileToSplit(File f) {
        this.fileToSplit = f;
    }

    /**
     * Sets the file to save
     * @param f File object representing the file that is to be saved
     */
    void setFileToSave(File f) {
        this.fileToSave = f;
    }

    /**
     * Splits the file based on a regex
     * @param regex String representing a regular expression
     * @param keyboard  Scanner that takes in console input
     * @throws IOException If there is an issue with reading or writing to a file
     * @throws PatternSyntaxException   If there is an issue with parsing the regular expression, generally if it has
     * been formatted incorrectly
     * @throws InputMismatchException   If there is any issue with the console input, also thrown within the code if
     * the letter U or N were not input
     */
    void split(String regex, Scanner keyboard) throws IOException, PatternSyntaxException, InputMismatchException {
        String split = FileUtils.readFileToString(fileToSplit, "UTF-8"); // Places the file into a string with
        // UTF-8 encoding

        Scanner fileScanner = new Scanner(split);

        fileScanner.useDelimiter(Pattern.compile(regex)); // Uses the regular expression as its delimiter

        /*
        Asks the user whether they want only unique instances of strings to be stored, or whether they want it all
        processed.
         */
        System.out.println("Type 'U' for unique, 'N' for normal");
        String answer = keyboard.next();
        StringBuilder toGo = new StringBuilder();
        Collection<String> strings; // Polymorphic object that will be assigned a subclass within the switch statement

        switch (answer) {
            case "U": case "u":
                strings = new LinkedHashSet<>(); // preserves ordering of strings but can only hold unique strings
                break;
            case "N": case "n":
                strings = new LinkedList<>(); // preserves ordering of strings whilst holding all strings represented
                // in the file
                break;
            default:
                throw new InputMismatchException("Invalid option"); // InputMismatchException is thrown if either U
                // or N were not typed
        }


        while (fileScanner.hasNext()) strings.add(fileScanner.next()); // Adds all strings based onto the delimiter
        // into the Collection

        /*
        Gets the user's operating system, then enters a for loop that appends a newline (/n for everything except
        Windows operating systems)
         */
        String os = System.getProperty("os.name");
        String newLine;
        if (os.startsWith("Windows")) {
            newLine = "\r\n";
        } else {
            newLine = "\n";
        }

        for (String s : strings) {
            toGo.append(s);
            toGo.append(newLine);
        }


        fileScanner.close();

        FileUtils.writeStringToFile(fileToSave, toGo.toString(), "UTF-8");
    }
}
