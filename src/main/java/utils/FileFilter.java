package utils;

import java.io.File;

public class FileFilter {

    public static File[] finder(String dirName){
        File dir = new File(dirName);

        return dir.listFiles((dir1, filename) -> filename.endsWith(".test"));

    }

    public static String[] dissectParams(String valueList){

        if(valueList.contains("|"))
            return valueList.split("|");
        else
            return new String[]{valueList};
    }



}

