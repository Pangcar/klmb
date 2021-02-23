package com.auxiliary;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by huangzheng on 2016/11/29.
 */
public class ExcelUtils {
    /**
     * 解决思路：采用Apache的POI的API来操作Excel，读取内容后保存到List中，再将List转Json（推荐Linked，增删快，与Excel表顺序保持一致）
     * <p>
     * Sheet表1  ————>    List1<Map<列头，列值>>
     * Sheet表2  ————>    List2<Map<列头，列值>>
     * <p>
     * 步骤1：根据Excel版本类型创建对于的Workbook以及CellSytle
     * 步骤2：遍历每一个表中的每一行的每一列
     * 步骤3：一个sheet表就是一个Json，多表就多Json，对应一个 List
     * 一个sheet表的一行数据就是一个 Map
     * 一行中的一列，就把当前列头为key，列值为value存到该列的Map中
     *
     * @param file SSM框架下用户上传的Excel文件
     * @return Map  一个线性HashMap，以Excel的sheet表顺序，并以sheet表明作为key，sheet表转换json后的字符串作为value
     * @throws IOException
     */
    public static LinkedHashMap<String, String> excel2json(MultipartFile file) throws IOException {

        System.out.println("excel2json方法执行....");


        // 返回的map
        LinkedHashMap<String, String> excelMap = new LinkedHashMap<>();

        // Excel列的样式，主要是为了解决Excel数字科学计数的问题
        CellStyle cellStyle;
        // 根据Excel构成的对象
        Workbook wb;
        // 如果是2007及以上版本，则使用想要的Workbook以及CellStyle
        if (file.getOriginalFilename().endsWith("xlsx")) {
            System.out.println("是2007及以上版本  xlsx");
            wb = new XSSFWorkbook(file.getInputStream());
            XSSFDataFormat dataFormat = (XSSFDataFormat) wb.createDataFormat();
            cellStyle = wb.createCellStyle();
            // 设置Excel列的样式为文本
            cellStyle.setDataFormat(dataFormat.getFormat("@"));
        } else {
            System.out.println("是2007以下版本  xls");
            POIFSFileSystem fs = new POIFSFileSystem(file.getInputStream());
            wb = new HSSFWorkbook(fs);
            HSSFDataFormat dataFormat = (HSSFDataFormat) wb.createDataFormat();
            cellStyle = wb.createCellStyle();
            // 设置Excel列的样式为文本
            cellStyle.setDataFormat(dataFormat.getFormat("@"));
        }

        // sheet表个数
        int sheetsCounts = wb.getNumberOfSheets();
        // 遍历每一个sheet
        for (int i = 0; i < sheetsCounts; i++) {
            Sheet sheet = wb.getSheetAt(i);
            System.out.println("第" + i + "个sheet:" + sheet.toString());

            // 一个sheet表对于一个List
            List list = new LinkedList();

            // 将第一行的列值作为正个json的key
            String[] cellNames;
            // 取第一行列的值作为key
            Row fisrtRow = sheet.getRow(0);
            // 如果第一行就为空，则是空sheet表，该表跳过
            if (null == fisrtRow) {
                continue;
            }
            // 得到第一行有多少列
            int curCellNum = fisrtRow.getLastCellNum();
            System.out.println("第一行的列数：" + curCellNum);
            // 根据第一行的列数来生成列头数组
            cellNames = new String[curCellNum];
            // 单独处理第一行，取出第一行的每个列值放在数组中，就得到了整张表的JSON的key
            for (int m = 0; m < curCellNum; m++) {
                Cell cell = fisrtRow.getCell(m);
                // 设置该列的样式是字符串
                cell.setCellStyle(cellStyle);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                // 取得该列的字符串值
                cellNames[m] = cell.getStringCellValue();
            }
            for (String s : cellNames) {
                System.out.print("得到第" + i + " 张sheet表的列头： " + s + ",");
            }
            System.out.println();

            // 从第二行起遍历每一行
            int rowNum = sheet.getLastRowNum();
            System.out.println("总共有 " + rowNum + " 行");
            for (int j = 1; j <= rowNum; j++) {
                // 一行数据对于一个Map
                LinkedHashMap rowMap = new LinkedHashMap();
                // 取得某一行
                Row row = sheet.getRow(j);
                int cellNum = row.getLastCellNum();
                // 遍历每一列
                for (int k = 0; k < cellNum; k++) {
                    Cell cell = row.getCell(k);

                    cell.setCellStyle(cellStyle);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    // 保存该单元格的数据到该行中
                    rowMap.put(cellNames[k], cell.getStringCellValue());
                }
                // 保存该行的数据到该表的List中
                list.add(rowMap);
            }
            // 将该sheet表的表名为key，List转为json后的字符串为Value进行存储
            excelMap.put(sheet.getSheetName(), JSON.toJSONString(list, false));
        }

        System.out.println("excel2json方法结束....");

        return excelMap;
    }


    /**
     * ！！！！！excel不设置单元格，列数以列头为准！！！！！
     * @param path
     * @return
     * @throws IOException
     */
    public static LinkedHashMap<String, String> excel2json(String path,int cellname) throws IOException {

        System.out.println("excel2json方法执行....");
        long start = System.currentTimeMillis();
        boolean isXlsx = true;
        if (path.indexOf("xlsx") >= 0) {
            isXlsx = true;
        } else {
            isXlsx = false;
        }

        ClassPathResource classPathResource = new ClassPathResource(path);
        InputStream stream = classPathResource.getInputStream();


        // 返回的map
        LinkedHashMap<String, String> excelMap = new LinkedHashMap<>();

        // Excel列的样式，主要是为了解决Excel数字科学计数的问题
        CellStyle cellStyle;
        // 根据Excel构成的对象
        Workbook wb;
        // 如果是2007及以上版本，则使用想要的Workbook以及CellStyle
        if (isXlsx) {
            System.out.println("是2007及以上版本  xlsx");
            wb = new XSSFWorkbook(stream);
            XSSFDataFormat dataFormat = (XSSFDataFormat) wb.createDataFormat();
            cellStyle = wb.createCellStyle();
            // 设置Excel列的样式为文本
            cellStyle.setDataFormat(dataFormat.getFormat("@"));
        } else {
            System.out.println("是2007以下版本  xls");
            POIFSFileSystem fs = new POIFSFileSystem(stream);
            wb = new HSSFWorkbook(fs);
            HSSFDataFormat dataFormat = (HSSFDataFormat) wb.createDataFormat();
            cellStyle = wb.createCellStyle();
            // 设置Excel列的样式为文本
            cellStyle.setDataFormat(dataFormat.getFormat("@"));
        }

        // sheet表个数
        int sheetsCounts = wb.getNumberOfSheets();
        // 遍历每一个sheet
        for (int i = 0; i < sheetsCounts; i++) {
            Sheet sheet = wb.getSheetAt(i);
//            System.out.println("第" + i + "个sheet:" + sheet.toString());

            // 一个sheet表对于一个List
            List list = new LinkedList();

            // 将第一行的列值作为正个json的key
            String[] cellNames;
            // 取第一行列的值作为key
            Row fisrtRow = sheet.getRow(0);
            // 如果第一行就为空，则是空sheet表，该表跳过
            if (null == fisrtRow) {
                continue;
            }
            // 得到第一行有多少列
            int curCellNum = fisrtRow.getLastCellNum();
//            System.out.println("第一行的列数：" + curCellNum);
            // 根据第一行的列数来生成列头数组
            cellNames = new String[curCellNum];
            // 单独处理第一行，取出第一行的每个列值放在数组中，就得到了整张表的JSON的key
            for (int m = 0; m < curCellNum; m++) {
                Cell cell = fisrtRow.getCell(m);
                // 设置该列的样式是字符串
                cell.setCellStyle(cellStyle);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                //-----------------------------------------------------------------
                // 取得该列的字符串值
                //返回数据时，json 数据内，key的样式；
                // 【一般上传的excel中列名多为中文，为防止在机顶盒中使用中文变量名出现兼容问题所以返回数据中列名转义为cell+（列数）】
                //-----------------------------------------------------------------
                if(cellname==0){
                    cellNames[m] = "cell"+m;
                }else{
                    cellNames[m] = cell.getStringCellValue();
                }

            }
//            for (String s : cellNames) {
//                System.out.print("得到第" + i + " 张sheet表的列头： " + s + ",");
//            }

            // 从第二行起遍历每一行
            int rowNum = sheet.getLastRowNum();
//            System.out.println("总共有 " + rowNum + " 行");
            for (int j = 1; j <= rowNum; j++) {
                // 一行数据对于一个Map
                LinkedHashMap rowMap = new LinkedHashMap();
                // 取得某一行
                Row row = sheet.getRow(j);
//                int cellNum = row.getLastCellNum();
                int cellNum = row.getPhysicalNumberOfCells();
//                System.out.println("第 " + j + " 行,共 "+cellNum+"列");
                // 遍历每一列
                for (int k = 0; k < curCellNum; k++) {//每行的列数，参照第一行
                    Cell cell = row.getCell(k);
//                    System.out.println("cell="+cell);
//                    System.out.println("cell.getCellType()="+cell.getCellType());

                    if(cell==null){
                        // 保存该单元格的数据到该行中
                        rowMap.put(cellNames[k], "");
                    }else{
                        cell.setCellStyle(cellStyle);
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        // 保存该单元格的数据到该行中
                        rowMap.put(cellNames[k], cell.getStringCellValue());
//                        System.out.println("第 " + j + " 行,第 "+k+"列 "+cell.getStringCellValue());

                    }


                }
                // 保存该行的数据到该表的List中
                list.add(rowMap);
            }
            // 将该sheet表的表名为key，List转为json后的字符串为Value进行存储
            excelMap.put(sheet.getSheetName(), JSON.toJSONString(list, false));
        }

        System.out.println("excel2json方法结束...."+ (System.currentTimeMillis()-start));

        return excelMap;
    }

    private ExcelUtils(){};
    private static int sheetNum = 0;//要解析的sheet下标
    private static List<String> columns;//要解析excel中的列名
    /**
     * poi读取excle
     * @return
     */
    public static String readExcel(String file){
        StringBuilder retJson = new StringBuilder();
        InputStream inStream = null;
        ClassPathResource classPathResource = new ClassPathResource(file);

        try {
            inStream = classPathResource.getInputStream();
//            inStream = new FileInputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook(inStream);
            HSSFSheet sheet = workbook.getSheetAt(sheetNum);//获得表
            int lastRowNum = sheet.getLastRowNum();//最后一行
            retJson.append("[");
            for(int i = 0 ;i < lastRowNum;i++){
                HSSFRow row = sheet.getRow(i);//获得行
                String rowJson = readExcelRow(row);
                retJson.append(rowJson);
                if(i<lastRowNum-1)
                    retJson.append(",");
            }
            retJson.append("]");
        } catch (Exception e) {
            try {
                inStream = classPathResource.getInputStream();
//                inStream = new FileInputStream(file);
                XSSFWorkbook workbook = new XSSFWorkbook(inStream);
                XSSFSheet sheet = workbook.getSheetAt(sheetNum);
                int lastRowNum = sheet.getLastRowNum();//最后一行
                retJson.append("[");
                for(int i = 0 ;i < lastRowNum;i++){
                    XSSFRow row = sheet.getRow(i);//获得行
                    String rowJson = readExcelRow(row);
                    retJson.append(rowJson);
                    if(i<lastRowNum-1)
                        retJson.append(",");
                }
                retJson.append("]");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }finally{
            close(null,inStream);
        }
        return retJson.toString();
    }


    /**
     * 读取行值
     * @return
     */
    private static String readExcelRow(HSSFRow row){
        StringBuilder rowJson = new StringBuilder();
        int lastCellNum = ExcelUtils.columns.size();//最后一个单元格
        rowJson.append("{");
        for(int i = 0 ;i<lastCellNum;i++){
            HSSFCell cell = row.getCell(i);

            String cellVal = readCellValue(cell);
            rowJson.append(toJsonItem(columns.get(i), cellVal));
            if(i<lastCellNum-1)
                rowJson.append(",");
        }
        rowJson.append("}");
        return rowJson.toString();
    }
    /**
     * 读取行值
     * @return
     */
    private static String readExcelRow(XSSFRow row){
        StringBuilder rowJson = new StringBuilder();
        System.out.println("excel2json方法执行...."+ExcelUtils.columns);
//        if(ExcelUtils.columns==null)
//            return "";

        int lastCellNum = ExcelUtils.columns.size();//最后一个单元格

        rowJson.append("{");
        for(int i = 0 ;i<lastCellNum;i++){
            XSSFCell cell = row.getCell(i);
            String cellVal = readCellValue(cell);
            rowJson.append(toJsonItem(columns.get(i), cellVal));
            if(i<lastCellNum-1)
                rowJson.append(",");
        }
        rowJson.append("}");
        return rowJson.toString();
    }

    /**
     * 读取单元格的值
     * @param hssfCell
     * @return
     */
    @SuppressWarnings("static-access")
    private static String readCellValue(HSSFCell hssfCell) {

        if(hssfCell==null)
            return "";
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            hssfCell.setCellType(1);//设置为String
            String str_temp = String.valueOf(hssfCell.getRichStringCellValue());//得到值
            return str_temp;
        }else if(hssfCell.getCellType() == hssfCell.CELL_TYPE_FORMULA){
            int val = hssfCell.getCachedFormulaResultType();
            return val+"";
        } else {
            return String.valueOf(hssfCell.getRichStringCellValue());
        }
    }
    /**
     * 读取单元格的值
     * @param
     * @return
     */
    @SuppressWarnings("static-access")
    private static String readCellValue(XSSFCell sssfCell) {
        if (sssfCell.getCellType() == sssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(sssfCell.getBooleanCellValue());
        } else if (sssfCell.getCellType() == sssfCell.CELL_TYPE_NUMERIC) {
            sssfCell.setCellType(1);//设置为String
            String str_temp = String.valueOf(sssfCell.getRichStringCellValue());//得到值
            return str_temp;
        }else if(sssfCell.getCellType() == sssfCell.CELL_TYPE_FORMULA){
            int val = sssfCell.getCachedFormulaResultType();
            return val+"";
        }else {
            return String.valueOf(sssfCell.getRichStringCellValue());
        }
    }
    /**
     * 转换为json对
     * @return
     */
    private static String toJsonItem(String name,String val){
        return "\""+name+"\":\""+val+"\"";
    }
    /**
     * 关闭io流
     * @param
     * @param
     */
    private static void close(OutputStream out, InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                System.out.println("InputStream关闭失败");
                e.printStackTrace();
            }
        }
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                System.out.println("OutputStream关闭失败");
                e.printStackTrace();
            }
        }
    }
    public static List<String> getColumns() {
        return ExcelUtils.columns;
    }
    public static void setColumns(List<String> columns) {
        ExcelUtils.columns = columns;
    }
    public static int getSheetNum() {
        return sheetNum;
    }
    public static void setSheetNum(int sheetNum) {
        ExcelUtils.sheetNum = sheetNum;
    }

}