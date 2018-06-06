package com.bcits.bfm.util;

import java.io.*;
import java.util.*;
import java.util.zip.*;

public class ZipUtils {

    private final List<File> fileList;

    public ZipUtils() {
        fileList = new ArrayList<>();
    }

  

    public void zipIt(File sourceFile, File zipFile) {
      
            byte[] buffer = new byte[1024];
            FileOutputStream fos = null;
            ZipOutputStream zos = null;

            try {

                String sourcePath = sourceFile.getPath();
                generateFileList(sourceFile);

                fos = new FileOutputStream(zipFile);
                zos = new ZipOutputStream(fos);

                System.out.println("Output to Zip : " + zipFile);
                FileInputStream in = null;

                for (File file : this.fileList) {
                    String path = file.getPath();
                    path = path.substring(sourcePath.length());
                    if (path.startsWith(File.separator)) {
                        path = path.substring(1);
                    }
                    String entryName = path + File.separator + file.getName();
                    System.out.println("File Added : " + path);
                    ZipEntry ze = new ZipEntry(path);

                    zos.putNextEntry(ze);
                    try {
                        in = new FileInputStream(file);
                        int len;
                        while ((len = in.read(buffer)) > 0) {
                            zos.write(buffer, 0, len);
                        }
                    } finally {
                        in.close();
                    }
                	
                	
                }

                zos.closeEntry();
                System.out.println("Folder successfully compressed");

            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
      
    }

    protected void generateFileList(File node) {

// add file only
        if (node.isFile()) {
            fileList.add(node);
      System.out.println("::::node::: is file:::"+node);
        }

        if (node.isDirectory()) {
            File[] subNote = node.listFiles();
            System.out.println("::::::::::node is dir"+node);
            for (File filename : subNote) {
                generateFileList(filename);
            }
        }
    }
}
