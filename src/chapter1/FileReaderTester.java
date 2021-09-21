package chapter1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderTester {
    protected void setUp(){
        try{
            input = new FileReader("data.txt");
        }catch(FileNotFoundException e){
            throw new RuntimeException("Can not open file");
        }
    }

    protected void tearDown(){
        try{
            input.close();
        }catch (IOException e){
            throw new RuntimeException("Error during closing file");
        }
    }
}
