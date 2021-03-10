package com.foodforme.vendore.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class downloadPdfContent {
    public void downloadPdfContent(String urlToDownload) {

        try {

            String fileName = "xyz";
            String fileExtension = ".pdf";

//           download pdf file.

            URL url = new URL(urlToDownload);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            String PATH = Environment.getExternalStorageDirectory() + "/mydownload/";
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, fileName + fileExtension);
            FileOutputStream fos = new FileOutputStream(outputFile);
            InputStream is = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();

            System.out.println("--pdf downloaded--ok--" + urlToDownload);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
