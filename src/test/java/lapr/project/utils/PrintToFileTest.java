package lapr.project.utils;




import org.junit.jupiter.api.Test;

import java.io.IOException;




public class PrintToFileTest {

    @Test
    public void print() throws IOException {
        PrintToFile.print("test","test.txt");
    }
    @Test
    public void printB() throws IOException {
        PrintToFile.printB(new StringBuilder("aa"),"test2.txt");
    }


}