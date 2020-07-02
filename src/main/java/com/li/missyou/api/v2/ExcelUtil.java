package com.li.missyou.api.v2;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

public class ExcelUtil {

    public static Workbook createWorkBook(List<Map<String, Object>> listMap, String keys[], String columnNames[]) {
        // 创建Excel 工作簿
        Workbook workbook = new HSSFWorkbook();
        // 创建第一个sheet, 并命名
        Sheet sheet = workbook.createSheet("Sheet1");
        // 手动设置列宽, 第一个参数表示要为第几列, 第二个参数表示列宽, n为列高的像素数
        for (int i = 0; i < keys.length; i++) {
            sheet.setColumnWidth((short) i, (short) (35 * 100));
        }

        // 创建第一行
        Row row = sheet.createRow((short) 0);
        // 设置列名
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
        }

        //设置每行每列的值
        for (short i = 0; i < listMap.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow((short) i + 1);
            // 在row行上创建一个方格
            for(short j = 0; j < keys.length; j++){
                Cell cell = row1.createCell(j);
                cell.setCellValue(listMap.get(i).get(keys[j]) == null?" " : listMap.get(i).get(keys[j]).toString());
                /**cell.setCellStyle(cs2);*/
            }
        }

        return workbook;
    }

    public static List<Map<String, String>> getExcelData(HttpServletRequest request, String filePro, String keys[]) {
        List<Map<String,String>> data = new ArrayList<>();
        try {
            if(!CommUtil.isNotNull(filePro)){
                filePro = "file";
            }
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = (MultipartFile) multipartRequest.getFile(filePro);
            //获得文件名
            String fileName = file.getOriginalFilename();
            Workbook wb = null;
            if(fileName.endsWith("xlsx")){
                wb = new XSSFWorkbook(file.getInputStream());
            }else{
                wb = new HSSFWorkbook(file.getInputStream());
            }
            //获得第一个表单
            Sheet sheet = wb.getSheetAt(0);
            //获得第一个表单的迭代器
            Iterator<Row> rows = sheet.rowIterator();
            int i = 0;
            while (rows.hasNext()) {
                i ++;
                Map<String,String> rowMap = new HashMap<>();
                //获得行数据
                Row row = rows.next();
                //跳过头部
                if(row.getRowNum() == 0){
                    continue;
                }
                Iterator<Cell> cells = row.cellIterator();
                //获得行的迭代器
                int j = 0,k = 0;
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    //类型判断
                    String key = "";
                    //防止越界
                    if(keys.length > cell.getColumnIndex()){
                        key = keys[cell.getColumnIndex()];
                    }
                    if(CommUtil.isNotNull(key)){
                        String value = formatCell(cell);
                        rowMap.put(key, value);
                        if(!CommUtil.isNotNull(value)){
                            j ++; //记录空值得数量
                        }
                        k ++; //记录多少列
                    }
                }
                //如果i=j，说明一行都是空的
                if(j == k){
                    break;
                }else{
                    data.add(rowMap);
                }
                if(i > 50001){
                    System.out.println("\n============导入数据大于五万条，立即停止===============");
                    break;
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }

    public static String formatCell(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {

            //数值格式
            case HSSFCell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    //如果是日期格式
                    return CommUtil.formatShortDate(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                }else{
                    //字符时
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    return cell.getStringCellValue();
                }

                //字符串
            case HSSFCell.CELL_TYPE_STRING:
                return cell.getStringCellValue();

            // 公式
            case HSSFCell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();

            // 空白
            case HSSFCell.CELL_TYPE_BLANK:
                return "";

            // 布尔取值
            case HSSFCell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() + "";

            //错误类型
            case HSSFCell.CELL_TYPE_ERROR:
                break;
        }
        return "";
    }
}
