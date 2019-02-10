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

class SplitFile {
    private File fileToSplit;
    private File fileToSave;

    SplitFile() {
        this.fileToSplit = null;
        this.fileToSave = null;
    }

    void setFileToSplit(File f) {
        this.fileToSplit = f;
    }

    void setFileToSave(File f) {
        this.fileToSave = f;
    }

    void split(String regex, Scanner keyboard) throws IOException, PatternSyntaxException, InputMismatchException {
        String split = FileUtils.readFileToString(fileToSplit, "UTF-8");

        Scanner fileScanner = new Scanner(split);

        fileScanner.useDelimiter(Pattern.compile(regex));

        System.out.println("Type 'U' for unique, 'N' for normal");
        String answer = keyboard.next();
        StringBuilder toGo = new StringBuilder();
        Collection<String> strings;

        switch (answer) {
            case "U": case "u":
                strings = new LinkedHashSet<>();
                break;
            case "N": case "n":
                strings = new LinkedList<>();
                break;
            default:
                throw new InputMismatchException("Invalid option");
        }


        while (fileScanner.hasNext()) strings.add(fileScanner.next());

        for (String s : strings) {
            toGo.append(s);
            toGo.append("\n");
        }


        fileScanner.close();

        FileUtils.writeStringToFile(fileToSave, toGo.toString(), "UTF-8");
    }
}
