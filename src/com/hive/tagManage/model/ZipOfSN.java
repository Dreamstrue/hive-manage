package com.hive.tagManage.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.springframework.web.servlet.view.AbstractView;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import com.hive.tagManage.entity.SNBase;
import com.hive.util.FileUtil;
import com.hive.util.ZipHelper;
public class ZipOfSN extends AbstractView {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Override
	protected void renderMergedOutputModel(Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String zipPatth = map.get("sourceDirectory")+File.separator+"zip";
		String filePath = zipPatth+File.separator+map.get("zipFileName")+".zip";
		FileUtil.makeDir(zipPatth);
		ZipHelper.uf_CompressDirectory(map.get("sourceDirectory")+File.separator+"excel", filePath);
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setHeader("Content-disposition", "attachment;filename="+encodeFilename(file.getName(),request));
	    OutputStream os = response.getOutputStream();
	    IOUtils.copy(fis, os);
	    os.flush();
	    os.close();
	    fis.close();
	    boolean flag = FileUtil.removeDir(map.get("sourceDirectory").toString());
	    if(!flag){
	    	System.out.println(map.get("sourceDirectory").toString()+"目录删除失败！");
	    }else{
	    	System.out.println(map.get("sourceDirectory").toString()+"目录删除成功！");
	    }
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
	
	public void buildExcelDocument(Map<String, Object> map)throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		//生成批次
		String batch = map.get("batch").toString();
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
		String filePath = map.get("sourceDirectory").toString();
		if(!new File(filePath).exists()){
			System.out.println(filePath+"不存在，所以我们创建了一个");
			FileUtil.makeDir(filePath);
		}
		String filename = filePath+File.separator+"质宝通 第"+batch+"批("+entityName+")的SN信息.xls";
		
		OutputStream outputStream = new FileOutputStream(filename);
		workbook.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}
	
	private HSSFCell getCell(HSSFSheet sheet, int row, int col){
	    HSSFRow sheetRow = sheet.getRow(row);
	    if (sheetRow == null) {
	      sheetRow = sheet.createRow(row);
	    }
	    HSSFCell cell = sheetRow.getCell(col);
	    if (cell == null) {
	      cell = sheetRow.createCell(col);
	    }
	    return cell;
	}
    private void setText(HSSFCell cell, String text){
	    cell.setCellType(1);
	    cell.setCellValue(text);
    }
}
