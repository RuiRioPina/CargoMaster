package lapr.project.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PrintToFile {
    private PrintToFile() {

    }
    public static void print(String message, String file)
            throws IOException {
        String str = message;
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(str);

        writer.close();
    }

    public static void printB(StringBuilder message, String file)
            throws IOException {
        StringBuilder str = message;
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(String.valueOf(str));

        writer.close();
    }
}
