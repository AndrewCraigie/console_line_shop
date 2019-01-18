package util;

import java.io.File;
import java.nio.file.FileSystems;

public class FileUtils {

    public static String stockStorePath(){
        String projectPath = System.getProperty("user.dir");
        String separator = FileSystems.getDefault().getSeparator();
        String testDatFileName = "stock_store.dat";
        String dataFilePath1 = projectPath + separator + testDatFileName;
        return dataFilePath1;
    }

    public static String offersStorePath(){
        String projectPath = System.getProperty("user.dir");
        String separator = FileSystems.getDefault().getSeparator();
        String testDatFileName = "offers_store.dat";
        String dataFilePath1 = projectPath + separator + testDatFileName;
        return dataFilePath1;
    }

    public static boolean fileExists(String filePath){

        File file = new File(filePath);
        return file.exists();

    }
}
