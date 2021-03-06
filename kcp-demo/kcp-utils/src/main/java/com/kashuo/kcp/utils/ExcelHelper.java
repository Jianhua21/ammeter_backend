package com.kashuo.kcp.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * excel 操作类
 */
public class ExcelHelper {


    private static Logger logger = LoggerFactory.getLogger(ExcelHelper.class);


    /**
     * 描述：读取Excel
     *
     * @param file File对象
     * @return 按sheet(工作表)来存放到Map, SheetName(工作表)名为key
     */
    public static Map readExcel(File file) {
        return readExcel(file, null, null, false, null);
    }

    /**
     * 描述：读取Excel
     *
     * @param file       File对象
     * @param fieldsName List中Map的键名，可为空，默认为列数索引（首列索引为0）
     * @return 按sheet(工作表)来存放到Map, SheetName(工作表)名为key
     */
    public static Map readExcel(File file, String[] fieldsName) {
        return readExcel(file, null, fieldsName, false, null);
    }


    /**
     * 描述：读取Excel
     *
     * @param file       File对象
     * @param fieldsName List中Map的键名，可为空，默认为列数索引（首列索引为0）
     * @param hasTitle   excel中是否有标题（如果有标题则去掉第一列数据）
     * @return 按sheet(工作表)来存放到Map, SheetName(工作表)名为key
     */
    public static Map readExcel(File file, String[] fieldsName, boolean hasTitle) {
        return readExcel(file, null, fieldsName, hasTitle, null);
    }


    /**
     * 描述：读取Excel
     *
     * @param file       File对象
     * @param columnNum  指定列（首列索引为0）
     * @param fieldsName List中Map的键名，可为空，默认为列数索引（首列索引为0）
     * @param hasTitle   excel中是否有标题（如果有标题则去掉第一列数据）
     * @return 按sheet(工作表)来存放到Map, SheetName(工作表)名为key
     */
    public static Map readExcel(File file, int[] columnNum, String[] fieldsName, boolean hasTitle) {
        return readExcel(file, columnNum, fieldsName, hasTitle, null);
    }


    /**
     * 描述：读取Excel
     *
     * @param file         File对象
     * @param columnNum    指定列（首列索引为0）
     * @param fieldsName   List中Map的键名，可为空，默认为列数索引（首列索引为0）
     * @param hasTitle     excel中是否有标题（如果有标题则去掉第一列数据）
     * @param dafaultValue 默认值（和fieldsName对应的值为空时，取默认值）
     * @return 按sheet(工作表)来存放到Map, SheetName(工作表)名为key
     */
    public static Map readExcel(File file, int[] columnNum, String[] fieldsName, boolean hasTitle, Map dafaultValue) {
        if (file.isFile()) {
            Map map = null;
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file);
                map = readExcel(inputStream, columnNum, fieldsName, hasTitle, dafaultValue);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }

            return map;
        }
        return null;
    }


    /**
     * 描述：读取Excel
     *
     * @param inputStream 输入流
     * @return 按sheet(工作表)来存放到Map, SheetName(工作表)名为key
     */
    public static Map readExcel(FileInputStream inputStream) throws IOException {
        return readExcel(inputStream, null, null, false, null);
    }


    /**
     * 描述：读取Excel
     *
     * @param inputStream 输入流
     * @param fieldsName  List中Map的键名，可为空，默认为列数索引（首列索引为0）
     * @return 按sheet(工作表)来存放到Map, SheetName(工作表)名为key
     */
    public static Map readExcel(FileInputStream inputStream, String[] fieldsName) throws IOException {
        return readExcel(inputStream, null, fieldsName, false, null);
    }


    /**
     * 描述：读取Excel
     *
     * @param inputStream 输入流
     * @param fieldsName  List中Map的键名，可为空，默认为列数索引（首列索引为0）
     * @param hasTitle    excel中是否有标题（如果有标题则去掉第一列数据）
     * @return 按sheet(工作表)来存放到Map, SheetName(工作表)名为key
     */
    public static Map readExcel(FileInputStream inputStream, String[] fieldsName, boolean hasTitle) throws IOException {
        return readExcel(inputStream, null, fieldsName, hasTitle, null);
    }


    /**
     * 描述：读取Excel
     *
     * @param inputStream 输入流
     * @param columnNum   指定列（首列索引为0）
     * @param fieldsName  List中Map的键名，可为空，默认为列数索引（首列索引为0）
     * @param hasTitle    excel中是否有标题（如果有标题则去掉第一列数据）
     * @return 按sheet(工作表)来存放到Map, SheetName(工作表)名为key
     */
    public static Map readExcel(FileInputStream inputStream, int[] columnNum, String[] fieldsName, boolean hasTitle) throws IOException {
        return readExcel(inputStream, columnNum, fieldsName, hasTitle, null);
    }


    /**
     * 描述：读取Excel
     *
     * @param inputStream  输入流
     * @param columnNum    指定列（首列索引为0）
     * @param fieldsName   List中Map的键名，可为空，默认为列数索引（首列索引为0）
     * @param hasTitle     excel中是否有标题（如果有标题则去掉第一列数据）
     * @param dafaultValue 默认值（和fieldsName对应的值为空时，取默认值）
     * @return 按sheet(工作表)来存放到Map, SheetName(工作表)名为key
     */
    public static Map readExcel(FileInputStream inputStream, int[] columnNum, String[] fieldsName, boolean hasTitle, Map dafaultValue) throws IOException {
        if (inputStream == null) {
            return null;
        }

        Map sheetMap = new HashMap();
        //			excel工作布，即一个excel文件
        HSSFWorkbook wb = new HSSFWorkbook(inputStream);


        //			工作布中工作表数
        int sheetNum = wb.getNumberOfSheets();

        for (int i = 0; i < sheetNum; i++) {
            List list = new ArrayList();

            HSSFSheet childSheet = wb.getSheetAt(i);
            int rowNum = childSheet.getLastRowNum();

            for (int j = 0; j < rowNum; j++) {
                if (hasTitle) {
                    continue;
                }

                Map map = new HashMap<>();
                HSSFRow row = childSheet.getRow(j);
                if (row != null) {
                    int cellNum = row.getLastCellNum();


                    //取指定列
                    if (columnNum != null && columnNum.length > 0) {
                        for (int k = 0; k < columnNum.length; k++) {
                            HSSFCell cell = row.getCell(columnNum[k]);
                            if (cell != null) {
                                String key = (fieldsName != null && fieldsName.length >= k) ? fieldsName[k] : String.valueOf(k);
                                String value = cell.toString();
                                if (StringUtil.isEmpty(value) && dafaultValue != null && !dafaultValue.isEmpty()) {
                                    value = (String) dafaultValue.get(key);
                                }
                                map.put(key, value);
                            }
                        }
                    }
                    //按顺序取列
                    else {
                        for (int k = 0; k < cellNum; k++) {
                            HSSFCell cell = row.getCell(k);
                            if (cell != null) {
                                String key = (fieldsName != null && fieldsName.length >= k) ? fieldsName[k] : String.valueOf(k);
                                String value = cell.toString();
                                if (StringUtil.isEmpty(value) && dafaultValue != null && !dafaultValue.isEmpty()) {
                                    value = (String) dafaultValue.get(key);
                                }
                                map.put(key, value);
                            }
                        }
                    }

                }
                list.add(map);
            }
            sheetMap.put(childSheet.getSheetName(), list);
        }

        return sheetMap;
    }


    /**
     * 读取Excel文件的内容,以ArrayList的方式返回每一行，具体
     * 每一行的内容也是一个ArrayList
     *
     * @param file Excel文件
     */
    public static ArrayList readExcelToList(File file) {
        InputStream inputStream = null;
        try {
            // 创建对Excel工作簿文件的引用
            inputStream = new FileInputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);


            //int numOfSheet = workbook.getNumberOfSheets();
            HSSFSheet sheet = workbook.getSheetAt(0);
            int start = sheet.getFirstRowNum();
            int end = sheet.getLastRowNum();

            ArrayList sheetList = new ArrayList();
            for (int i = start; i <= end; i++) {
                HSSFRow row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                short startCell = row.getFirstCellNum();
                short endCell = row.getLastCellNum();

                ArrayList rowList = new ArrayList();
                for (int j = startCell; j < endCell; j++) {
                    HSSFCell cell = row.getCell(j);
                    rowList.add(getCellValue(cell));
                }
                sheetList.add(rowList);
            }
            return sheetList;
        } catch (Exception ex) {
            logger.error("", ex);
        } finally

        {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    inputStream = null;
                } catch (Exception ex) {
                    logger.debug(ex.getMessage());
                }
            }
        }
        return null;
    }


    /**
     * 描述：获取所有的图片
     *
     * @param file File对象
     */
    public static void readAllPictures(File file, String outDirectory) {
        if (!file.isFile()) {
            return;
        }

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            readAllPictures(inputStream, outDirectory);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }


    /**
     * 描述：获取所有的图片
     *
     * @param outDirectory 输出目录
     */
    public static void readAllPictures(FileInputStream inputStream, String outDirectory) throws IOException {
        if (inputStream == null) {
            return;
        }
        HSSFWorkbook wb = new HSSFWorkbook(inputStream);
        List list = wb.getAllPictures();
        int i = 1;
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            HSSFPictureData pictureData = (HSSFPictureData) iter.next();
            int format = pictureData.getFormat();
            String suffix = "";
            switch (format) {
                case HSSFPictureData.MSOBI_PNG:
                    suffix = ".png";
                    break;

                default:
                    suffix = ".jpg";
                    break;
            }
            if (StringUtil.isNotEmpty(outDirectory)) {
                if (!FileHelper.isDirectory(outDirectory)) {
                    FileHelper.createDirectory(outDirectory);
                }
                outDirectory = FileHelper.separatorsToSystem(outDirectory);
                if (outDirectory.endsWith(File.separator)) {
                    FileHelper.writeToFile(outDirectory + "picture_" + i + suffix, pictureData.getData());
                } else {
                    FileHelper.writeToFile(outDirectory + File.separator + "picture_" + i + suffix, pictureData.getData());
                }
            } else {
                FileHelper.writeToFile("./xlsPicture/" + "picture_" + i + suffix, pictureData.getData());
            }
            i++;
        }
    }


    /**
     * 描述：生成Excel
     *
     * @param list          数据
     * @param titles        标题
     * @param mappingFileds 标题对应字段名
     * @param columnWidth   列宽
     * @param hasIndex      是否有序号
     * @return
     */
    public static HSSFWorkbook createExcel(List list, String[] titles, String[] mappingFileds, int[] columnWidth, boolean hasIndex) {
        return createExcel(null, list, titles, mappingFileds, columnWidth, null, null, null, null, null, null, hasIndex, "序号");
    }

    public static HSSFWorkbook createExcel(List list, String[] titles, String[] mappingFileds, int[] columnWidth, boolean hasIndex, String titleIndex) {
        return createExcel(null, list, titles, mappingFileds, columnWidth, null, null, null, null, null, null, hasIndex, titleIndex);
    }

    /**
     * 描述：生成Excel
     *
     * @param workbook      工作薄
     * @param list          数据
     * @param titles        标题
     * @param mappingFileds 标题对应字段名
     * @param columnWidth   列宽
     * @param hasIndex      是否有序号
     */
    public static HSSFWorkbook createExcel(HSSFWorkbook workbook, List list, String[] titles, String[] mappingFileds, int[] columnWidth, boolean hasIndex) {
        return createExcel(workbook, list, titles, mappingFileds, columnWidth, null, null, null, null, null, null, hasIndex, "序号");
    }


    /**
     * 描述：生成Excel
     *
     * @param workbook      工作薄
     * @param list          数据
     * @param titles        标题
     * @param mappingFileds 标题对应字段名
     * @param columnWidth   列宽
     * @param titleStyle    标题样式（为空时字体默认粗体）
     * @param rows          指定行
     * @param rowsStyle     指定行样式
     * @param column        指定列
     * @param columnsStyle  指定列样式
     * @param otherStyle    其它样式
     * @param hasIndex      是否有序号
     * @param titleIndex    序号标题
     */
    public static HSSFWorkbook createExcel(HSSFWorkbook workbook, List list, String[] titles, String[] mappingFileds, int[] columnWidth, HSSFCellStyle titleStyle, int[] rows,
                                           HSSFCellStyle[] rowsStyle, int[] column, HSSFCellStyle[] columnsStyle, HSSFCellStyle otherStyle, boolean hasIndex, String titleIndex) {
        if (hasIndex) {
            titles = arrayInsert(titles, 0, titleIndex);
            columnWidth = arrayInsert(columnWidth, 0, 50);
        }
        //如果workbook（工作薄）为空时，创建工作薄
        if (workbook == null) {
            workbook = new HSSFWorkbook();
        }

        //创建工作页
        HSSFSheet sheet = workbook.createSheet();

        int rownum = 0;

        //生成标题
        if (titles != null && titles.length > 0) {
            //				创建行
            HSSFRow row = sheet.createRow(rownum);
            row.setHeightInPoints(20);

            //是否有2级标题
            if (ArrayUtils.contains(titles, ":")) {
                for (int i = 0; i < titles.length; i++) {

                    if (columnWidth != null && columnWidth.length > 0 && i < columnWidth.length) {
                        //设置单元格宽度
                        sheet.setColumnWidth(i, columnWidth[i]);
                    }

                    String title = titles[i];


                    //两级标题的格式   ptitle:ctitle1,ctitle2
                    if (title.contains(":")) {
                        String[] groupTitles = title.split(":");
                        if (groupTitles.length != 2) {
                            logger.error("标题格式错误。");
                            return null;
                        }

                        String pTitle = groupTitles[0];
                        String cTitle = groupTitles[1];

                        if (cTitle.contains(",")) {
                            rownum++;

                            String[] cTitles = cTitle.split(",");

                            HSSFCell cell = row.createCell(i);
                            cell.setCellValue(pTitle);

                            HSSFRow row2 = sheet.createRow(rownum);
                            for (int j = 0; j < cTitles.length; j++) {
                                HSSFCell ccell = row2.createCell(i);
                                ccell.setCellValue(pTitle);
                                setTitleStyle(workbook, ccell, titleStyle);
                                workbook.setRepeatingRowsAndColumns(workbook.getSheetIndex(sheet), 0, 0, i, i + j + 1);
                            }

                        } else {
                            logger.error("标题格式错误。");
                            return null;
                        }
                    } else {
                        HSSFCell cell = row.createCell(i);
                        workbook.setRepeatingRowsAndColumns(workbook.getSheetIndex(sheet), i, i, 0, 1);
                        cell.setCellValue(title);
                        setTitleStyle(workbook, cell, titleStyle);

                    }

                }
            } else {
                for (int i = 0; i < titles.length; i++) {
                    String title = titles[i];
                    HSSFCell cell = row.createCell(i);
                    cell.setCellValue(title);
                    setTitleStyle(workbook, cell, titleStyle);
                }
            }

            rownum++;
        }

        HSSFCellStyle cellStyle = null;
        //生成数据
        for (Object aList : list) {
            Map data = (Map) aList;

            // 创建行
            HSSFRow row = sheet.createRow(rownum);

            if (hasIndex) {
                HSSFCell cell = row.createCell(0);
                int cellValue = rownum;
                if (ArrayUtils.contains(titles, ":")) {
                    cellValue = rownum - 1;
                }
                cell.setCellValue(cellValue);
                cellStyle = cellStyle == null ? workbook.createCellStyle() : cellStyle;// 建立新的cell样式
                cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                cell.setCellStyle(cellStyle);
            }
            for (int k = 0; k < mappingFileds.length; k++) {
                HSSFCell cell;
                if (hasIndex) {
                    cell = row.createCell(k + 1);
                } else {
                    cell = row.createCell(k);
                }
                Object obj = data.get(mappingFileds[k]);
                if (obj instanceof BigDecimal) {
                    cell.setCellValue(((BigDecimal) obj).doubleValue());
                } else {
                    cell.setCellValue(obj == null ? "" : (obj + ""));
                }
                row.setHeightInPoints(17);
                //设置其它样式
                if (otherStyle != null) {
                    cell.setCellStyle(otherStyle);
                }
            }
            rownum++;
        }


        for (int i = 0; titles != null && i < titles.length; i++) {
            try{
                sheet.autoSizeColumn(i);
            }catch(Exception e){

            }
        }
        //设置行样式
        if (rows != null) {
            if (rowsStyle != null) {
                System.out.println();
            }
        }

        //设置列样式
        if (columnsStyle != null) {
            System.out.println();
        }

        return workbook;
    }


    /**
     * 描述：导出工作簿
     *
     * @param wb       要导出的excel工作簿
     * @param fileName 指定导出文件名
     */
    public static void export(HttpServletRequest request, HttpServletResponse response, HSSFWorkbook wb, String fileName) {
        if (wb == null) {
            logger.error("excel工作簿不能为空！");
            return;
        }
        response.setContentType("application/vnd.ms-excel");
        if (StringUtil.isNotEmpty(fileName) && !fileName.endsWith(".xls")) {
            fileName += ".xls";
        }

        ServletOutputStream servletOS;
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

            servletOS = response.getOutputStream();

            wb.write(servletOS);
            servletOS.close();
            servletOS.flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }


    /**
     * 描述：设置title单元格样式
     *
     * @param workbook   工作目录
     * @param cell       单元格
     * @param titleStyle title样式
     */
    private static void setTitleStyle(HSSFWorkbook workbook, HSSFCell cell, HSSFCellStyle titleStyle) {
        if (titleStyle == null) {
            HSSFCellStyle cellStyle = workbook.createCellStyle();// 建立新的cell样式
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            HSSFFont bFont = workbook.createFont();
            bFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            bFont.setFontHeightInPoints((byte) 11);
            cellStyle.setFont(bFont);
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            cell.setCellStyle(cellStyle);
        } else {
            cell.setCellStyle(titleStyle);
        }
    }


    private static String[] arrayInsert(String[] sArr, int index, String s) {
        if (sArr == null) {
            return null;
        }
        String[] arrTemp = new String[sArr.length + 1];
        int j = 0;
        for (int i = 0; i < arrTemp.length; i++) {
            if (i == index) {
                arrTemp[i] = s;
            } else {
                arrTemp[i] = sArr[j];
                j++;
            }
        }
        return arrTemp;
    }


    private static int[] arrayInsert(int[] iArr, int index, int v) {
        if (iArr == null) {
            return null;
        }
        int[] arrTemp = new int[iArr.length + 1];
        int j = 0;
        for (int i = 0; i < arrTemp.length; i++) {
            if (i == index) {
                arrTemp[i] = v;
            } else {
                arrTemp[i] = iArr[j];
                j++;
            }
        }
        return arrTemp;
    }


    /**
     * 描述：获取单元格中的值
     *
     * @param cell EXCEL 单元格
     */
    private static String getCellValue(HSSFCell cell) {
        String value = null;
        if (cell != null) {
            int type = cell.getCellType();
            switch (type) {
                case HSSFCell.CELL_TYPE_NUMERIC:
                    value = new DataFormatter().formatCellValue(cell);
                    break;

                case HSSFCell.CELL_TYPE_STRING:
                    value = cell.getRichStringCellValue().getString();
                    break;
            }
        }
        return value;
    }


    public static void main(String[] args) {

        // 威富通移动支付信息更新语句生成
        // 若提供文件为'xlsx'格式需手动另存为'xls'格式读取
        String localpath = "/Users/Mr.ZHAO/Downloads/威富通11.18（后半部分）.xls";
        ArrayList<List<String>> list = readExcelToList(new File(localpath));
        System.out.println(list.remove(0)); // 标题列
        System.out.println(list);

        // 生成示例：
        // update store_pos a, merchant_store b set a.wechat_channel = 'wft', a.alipay_channel = 'wft',
        // wechat_percent = 0.25, alipay_percent = 0.25, alipay_merchant_no = '888002388880086',
        // wechat_merchant_no = '888002388880086', alipay_terminal_no = '00000001', wechat_terminal_no = '00000001'
        // where a.store_id = b.id and sn_code = '0820016285' and store_code = '001000160000021002';

        System.out.println("\n\n------------------------ SQL START ------------------------");
        for (List<String> columns : list) {
            StringBuilder builder = new StringBuilder();
            builder.append("update store_pos a, merchant_store b set a.wechat_channel = 'wft', a.alipay_channel = 'wft', ");
            String sn_code = columns.get(0).trim();
            String store_code = columns.get(2).trim();
            String merchant_no = columns.get(3).trim();
            String terminal_no = columns.get(4).trim();
            String wechat_percent = columns.get(5).replace("%", "").trim();
            String alipay_percent = columns.get(6).replace("%", "").trim();
            builder.append(String.format("wechat_percent = %s, alipay_percent = %s, ", wechat_percent, alipay_percent));
            builder.append(String.format("alipay_merchant_no = '%s', wechat_merchant_no = '%s', ", merchant_no, merchant_no));
            builder.append(String.format("alipay_terminal_no = '%s', wechat_terminal_no = '%s' ", terminal_no, terminal_no));
            builder.append(String.format("where a.store_id = b.id and sn_code = '%s' and store_code = '%s';", sn_code, store_code));
            System.out.println(builder.toString());
        }
        System.out.println("------------------------ SQL END   ------------------------\n\n");

    }


}
