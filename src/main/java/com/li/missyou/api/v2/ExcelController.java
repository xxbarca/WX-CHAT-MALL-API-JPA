package com.li.missyou.api.v2;

import com.li.missyou.util.CommonUtil;
import javafx.stage.StageStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public void importExcel(@RequestBody List<Map<String, Object>> list) {

        System.out.println(list);

//        Map<String, Object> map = new HashMap<>();
//        String keys[] = {"name","age","sex"};
//        try {
//            List<Map<String,String>> listData = ExcelUtil.getExcelData(request, "file", keys);
//            if(listData.size() == 0){
//                map.put("status",-1);
//                map.put("message","上传失败，上传数据必须大于一条");
//                return map;
//            }
//            for (Map<String, String> dataMap : listData) {
//                System.out.println(keys[0] + ":" + dataMap.get(keys[0]));
//                System.out.println(keys[1] + ":" + dataMap.get(keys[1]));
//                System.out.println(keys[2] + ":" + dataMap.get(keys[2]));
//            }
//            map.put("listData", listData);
//            map.put("code", 1);
//            map.put("message", "导入成功");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return map;
    }

    /**
     * 数据导出
     * */
    @RequestMapping(value = "/excelExport", method = RequestMethod.POST)
    public Map<String, Object> excelExport(HttpServletRequest request, HttpServletResponse response, @RequestBody List<Map<String, Object>> excelDTOs) throws IOException {
        Map<String, Object> data = new HashMap<>();
        String fileName = CommUtil.formatTime("yyyyMMddHHmmss", new Date()) + ".xls";
        String columnNames[] = {"姓名","年龄","性别"};
        String keys[] = {"name", "age", "sex"};
        try {
            //创建Workbook
            Workbook wb = ExcelUtil.createWorkBook(excelDTOs, keys, columnNames);
            //保存路径
            String savePath = request.getServletContext().getRealPath("/") + File.separator + fileName;
            // 创建文件流
            OutputStream stream = new FileOutputStream(savePath);
            // 写入数据
            wb.write(stream);
            stream.flush();
            // 关闭文件流
            stream.close();

            //返回结果
            data.put("code", 1);
            String downloadUrl = request.getScheme() + "://"+request.getServerName() + ":" +
                    request.getServerPort() + "/" + fileName;
            data.put("download", downloadUrl);
            data.put("message", "文件流输出成功");

            System.out.println("\n数据导出成功，下载路劲：" + downloadUrl);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            data.put("code", -1);
            data.put("message", "下载出错");
            return data;
        }
        return data;
    }

}
