package com.kashuo.kcp.utils;

import com.csvreader.CsvWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CSVExportUtils {

    private static final Logger logger = LoggerFactory.getLogger(CSVExportUtils.class);

    /** CSV文件列分隔符 */
    private static final String CSV_COLUMN_SEPARATOR = ",";

    /** CSV文件列分隔符 */
    private static final String CSV_RN = "\r\n";

    /** CSV文件列分隔符 */
    private static final String CSV_TT = "\t";

    /**
     *
     * @param dataList 集合数据
     * @param colNames 表头部数据
     * @param mapKey 查找的对应数据
     */
    public static boolean doExport(List<Map<String, String>> dataList, String colNames, String mapKey, String remark,  OutputStream os) {
        try {
            StringBuffer buf = new StringBuffer();

            String[] colNamesArr = colNames.split(CSV_COLUMN_SEPARATOR);
            String[] mapKeyArr = mapKey.split(CSV_COLUMN_SEPARATOR);
            String[] remarkArr = remark.split(CSV_COLUMN_SEPARATOR);
            // 完成数据csv文件的封装

            // 输出列头
            for (int i = 0; i < colNamesArr.length; i++) {
                buf.append(colNamesArr[i]).append(CSV_COLUMN_SEPARATOR);
            }
            buf.append(CSV_RN);

            if (null != dataList) { // 输出数据
                for (int i = 0; i < dataList.size(); i++) {
                    for (int j = 0; j < mapKeyArr.length; j++) {
                        buf.append(dataList.get(i).get(mapKeyArr[j])).append(CSV_TT).append(CSV_COLUMN_SEPARATOR);
                    }
                    buf.append(CSV_RN);
                }
            }
            // 写出响应
            os.write(buf.toString().getBytes("GBK"));
            os.flush();
            return true;
        } catch (Exception e) {
            logger.error("导出CSV文件报错...", e);
        }
        return false;
    }

    /**
     * @throws UnsupportedEncodingException
     */
    public static void responseSetProperties(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        // 设置文件后缀
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fn = fileName + sdf.format(new Date()).toString() + ".csv";
        // 读取字符编码
        String utf = "UTF-8";

        // 设置响应
        response.setContentType("application/ms-txt.numberformat:@");
        response.setCharacterEncoding(utf);
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "max-age=30");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fn, utf));
    }

    public static void export2Csv(String fileName, String[] headers, List<List<String>> dataList){
        // before we open the file check to see if it already exists
        boolean alreadyExists = new File(fileName).exists();

        try {
            // use FileWriter constructor that specifies open for appending
            CsvWriter csvOutput = new CsvWriter(new FileWriter(fileName, true), ',');

            // if the file didn't already exist then we need to write out the header line
            if (!alreadyExists)
            {
                for(String header : headers){
                    csvOutput.write(header);
                }
                csvOutput.endRecord();
            }
            // else assume that the file already has the correct header line

            for(List<String> datas : dataList){
                if(!datas.isEmpty()){
                    for(String data : datas){
                        csvOutput.write(data);
                    }
                }
                csvOutput.endRecord();
            }
            csvOutput.close();
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String outputFile = "users.csv";
        String[] headers = {"a","b","c"};
        List<List<String>> dataList = new ArrayList<>();
        for(int i=0;i<10;i++){
            List<String> datas = new ArrayList<>();
            for(int j=0;j<4;j++){
                datas.add(i+"_"+j);
            }
            dataList.add(datas);
        }

        export2Csv(outputFile, headers, dataList);

    }

}

