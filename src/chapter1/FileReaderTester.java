package chapter1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderTester {
    protected void setUp(){
        try{
            _input = new FileReader("data.txt");
        }catch(FileNotFoundException e){
            throw new RuntimeException("Can not open file");
        }
    }

    protected void tearDown(){
        try{
            _input.close();
        }catch (IOException e){
            throw new RuntimeException("Error during closing file");
        }
    }

    public void testRead() throws IOException{
        char ch = '&';
        for (int i=0; i < 4; i++){
            ch = (char) _input.read();
            assert('d' == ch);
        }
    }
}
