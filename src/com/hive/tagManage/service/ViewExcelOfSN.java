package com.hive.tagManage.service;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import com.hive.common.SystemCommon_Constant;
import com.hive.tagManage.entity.SNBase;
public class ViewExcelOfSN extends AbstractExcelView {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Override
	protected void buildExcelDocument(Map<String, Object> map,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//生成批次
		String batch = map.get("batch").toString();
		//SN类别
//		String rank = map.get("rank").toString();
//		if(rank.equals("1")) {
//			rank="普通";
//		}
//		if(rank.equals("2")) {
//			rank="优等";
//		}
//		if(rank.equals("3")) {
//			rank="豪华";
//		}

		//批次数量
		String count = map.get("count").toString();
		String entityName= map.get("entityName").toString();
//		String productName= map.get("productName").toString();
		
		//获取导出的数据信息集合
		List<SNBase> snList = (List<SNBase>)map.get("snList");
		
		HSSFSheet sheet = workbook.createSheet("第"+batch+"批次");
		sheet.setDefaultColumnWidth(18);
		sheet.setColumnWidth(3, 23000); 
		
		//first row (0-based)
		//first column  (0-based) 
		//last row (0-based)
		//last column  (0-based)
		sheet.addMergedRegion(new Region(0,(short)0,0,(short)3));   
		
		//1.生成字体对象
	    HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 18);
		font.setFontName("新宋体");
		font.setColor(HSSFColor.BLUE.index);
		font.setBoldweight((short) 0.8);
		
	    //2.生成样式对象
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中 
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(font); //调用字体样式对象
		
		//增加表格边框 的样式 例子
        style.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
	    style.setBorderLeft(HSSFCellStyle.BORDER_DOUBLE);
	    style.setTopBorderColor(HSSFColor.GOLD.index);
	    style.setLeftBorderColor(HSSFColor.PLUM.index);
	    
		HSSFCell cell = getCell(sheet, 0, 0);
		setText(cell, "质宝通 第"+batch+"批("+entityName+")的SN信息");
		//3.单元格应用样式
		cell.setCellStyle(style);
		
		HSSFCellStyle dateStyle = workbook.createCellStyle();
		cell = getCell(sheet, 1, 0);
		cell.setCellValue("日期："+sdf.format(new Date()));
//		cell = getCell(sheet, 1, 1);
//		cell.setCellValue("类别："+rank);
		cell = getCell(sheet, 1, 1);
		cell.setCellValue("数量："+count);
		getCell(sheet, 2, 0).setCellValue("顺序号");
		getCell(sheet, 2, 1).setCellValue("SN");
//		getCell(sheet, 2, 2).setCellValue("父节点SN");
		getCell(sheet, 2, 2).setCellValue("二维码内容");
		//HSSFRow sheetRow = null;
		//SNBase sn = null;
		//for(short j=3;j<=snList.size()+3;j++) {
		for(short i = 0; i < snList.size(); i++) {
			HSSFRow sheetRow = sheet.createRow(i+3);
			SNBase sn = snList.get(i);
			String runningNum = batch+(String.format("%07d", sn.getSequenceNum()));
			String twodimensioncode = sn.getQueryPath();
			sheetRow.createCell(0).setCellValue(runningNum); //标签编号
			sheetRow.createCell(1).setCellValue(sn.getSn());	//sn码
//			sheetRow.createCell(2).setCellValue(sn.getPsn());	//父节点sn码
			sheetRow.createCell(2).setCellValue(twodimensioncode);	//sn的查询路径
		}
		//}
		String filename = "质宝通 第"+batch+"批("+entityName+")的SN信息.xls";
		filename = encodeFilename(filename,request);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=" + filename);
		
		OutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		outputStream.flush();
		outputStream.close();
		
	}
	
	/**  
     * 设置下载文件中文件的名称  
     *   
     * @param filename  
     * @param request  
     * @return  
     */    
	public static String encodeFilename(String filename, HttpServletRequest request) {    
		/**  
		 * 获取客户端浏览器和操作系统信息  
		 * 在IE浏览器中得到的是：User-Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Maxthon; Alexa Toolbar)  
		 * 在Firefox中得到的是：User-Agent=Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.7.10) Gecko/20050717 Firefox/1.0.6  
		 */    
		String agent = request.getHeader("USER-AGENT");    
		try {    
			if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {    
				String newFileName = URLEncoder.encode(filename, "UTF-8");    
				newFileName = StringUtils.replace(newFileName, "+", "%20");    
				if (newFileName.length() > 150) {    
					newFileName = new String(filename.getBytes("GB2312"), "ISO8859-1");    
					newFileName = StringUtils.replace(newFileName, " ", "%20");    
				}    
				return newFileName;    
			}    
			if ((agent != null) && (-1 != agent.indexOf("Mozilla"))) {  
				return MimeUtility.encodeText(filename, "UTF-8", "B");    
			}
			return filename;    
      } catch (Exception ex) {    
        return filename;    
      }    
    }   
}
