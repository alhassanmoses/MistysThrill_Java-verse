package com.mistyinc.mistysthrill.util;

import java.io.*;

public class IOUtil {
    public static void read(String[] data, String filename) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                data[count++] = line;
            }
        } catch (UnsupportedEncodingException e) {  
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
