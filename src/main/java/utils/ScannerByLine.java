package utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class ScannerByLine {


    private final File filePath;
    private final static Charset ENCODING = StandardCharsets.UTF_8;


    public ScannerByLine(File fileName){
        //filePath = Paths.get(fileName);
        filePath = fileName;
    }


    public final HashMap<String,String> processLineByLine() throws IOException {
        HashMap<String,String> map = new HashMap<>();
        try (Scanner scanner =  new Scanner(filePath, ENCODING.name())){
            while (scanner.hasNextLine()){
                processLine(scanner.nextLine(),map);
            }
        }

        return map;
    }

    private void processLine(String line, HashMap<String,String> map ){
        try(Scanner scanner = new Scanner(line)){
            scanner.useDelimiter("=");
            if (scanner.hasNext()){
                String name = scanner.next();
                String value = scanner.next();
                map.put(name.trim(),value.trim());
                log("Name is : " + quote(name.trim()) + ", and Value is : " + quote(value.trim()));
            }
            else {
                log("Empty or invalid line. Unable to process.");
            }
        }
    }



    private static void log(Object object){
        System.out.println(object);
    }

    private String quote(String text){
        String QUOTE = "'";
        return QUOTE + text + QUOTE;
    }
}
