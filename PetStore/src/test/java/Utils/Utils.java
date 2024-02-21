package Utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Utils {
    public static String getNowDate(String mask){
        DateFormat dateFormat = new SimpleDateFormat(mask);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static int getRandomNumber(int digts){
        return Integer.parseInt(RandomStringUtils.randomNumeric(digts));
    }

    public static Iterator<Object []> csvProvider(String csvNamePath){
        String line = "";
        String cvsSplitBy = ";";
        List<Object []> testCases = new ArrayList<>();
        String[] data= null;

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(csvNamePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                if (!((line = br.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            data= line.split(cvsSplitBy);
            testCases.add(data);
        }

        return testCases.iterator();
    }

}
