package lapr.project.utils;




import org.junit.jupiter.api.Test;

import java.io.IOException;




public class PrintToFileTest {

    @Test
    public void print() throws IOException {
        PrintToFile.print("test","test.txt");
    }


}