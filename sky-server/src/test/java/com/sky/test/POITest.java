package com.sky.test;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class POITest {
    /**
     * 通过POI创建Excel文件并且写入文件内容
     */
    public static void write() throws Exception {
        // 在内存中创建一个Excel文件
        XSSFWorkbook excel = new XSSFWorkbook();
        // 在Excel文件中创建一个sheet页
        XSSFSheet sheet = excel.createSheet("info");
        // 在Sheet中创建行对象, rownum编号从0开始
        XSSFRow row = sheet.createRow(1);
        // 创建单元格并写入文件内容
        row.createCell(1).setCellValue("姓名");
        row.createCell(2).setCellValue("城市");

        // 创建一个新行
        row = sheet.createRow(2);
        row.createCell(1).setCellValue("张三");
        row.createCell(2).setCellValue("北京");

        row = sheet.createRow(3);
        row.createCell(1).setCellValue("李四");
        row.createCell(2).setCellValue("南京");

        // 通过输出流将内存中的Excel文件写入到磁盘
        FileOutputStream out = new FileOutputStream(new File("C:\\Users\\Pluminary\\Desktop\\itcast.xlsx"));
        excel.write(out);

        // 关闭资源
        out.close();
        excel.close();
    }

    public static void main(String[] args) throws Exception {
        write();
    }
}
