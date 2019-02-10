/*
 * Copyright (c) 2019 Michael Male.
 *
 * This file, Application.java, is part of FileSplitter.
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

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

public class Application {
    public static void main(String[] args) {
        SplitFile splitter = new SplitFile();
        Scanner in = new Scanner(System.in);
        String regex = null;
        System.out.println("Welcome");

        System.out.println("What file would you like to split?");
        splitter.setFileToSplit(new File(in.nextLine()));
        System.out.println("What would you like the split file to be named as?");
        splitter.setFileToSave(new File(in.nextLine()));

        System.out.println("Please enter a regular expression for the file to be split by");
        regex = in.nextLine();

        try {
            splitter.split(regex, in);
        } catch (IOException e) {
            System.err.println("An error occurred. Please see console output");
            e.printStackTrace();
        } catch (PatternSyntaxException e) {
            System.err.println("Regular expression was invalid.");
            e.printStackTrace();
        } catch (InputMismatchException e) {
            System.err.println("An input error occurred. Please see console output");
            e.printStackTrace();
        }

        in.close();



    }

}

