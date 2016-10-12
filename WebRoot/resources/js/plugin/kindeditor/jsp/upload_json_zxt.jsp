<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.io.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.json.simple.*" %>
<%@ page import="com.hive.util.FileUtil" %>
<%@ page import="com.hive.util.PropertiesFileUtil" %>
<%

/**
 * KindEditor JSP
 * 
 * 本JSP程序是演示程序，建议不要直接在实际项目中使用。
 * 如果您确定直接使用本程序，使用之前请仔细确认相关安全设置。
 * 
 */
PropertiesFileUtil  propertiesFileUtil = new PropertiesFileUtil();
String servicePath = propertiesFileUtil.findValue("zxt_service_path");  //前台发布项目的服务器路径
//   /opt/tomcat_8081/apache-tomcat-6.0.37/webapps/ROOT/  ----linux下tomcat项目层级目录
//	 D\:/apache-tomcat-7.0.27/webapps/zyzlcxw/ ----windows下tomcat项目层级目录


//文件保存目录路径
String savePath = pageContext.getServletContext().getRealPath("/")+"upload"+File.separator;//本项目的后台存放路径(服务器如tomcat下)



//文件保存目录URL
String saveUrl  = request.getContextPath()+"/upload/";   // 


String otherPath = servicePath+"upload"+File.separator; //本项目的前台存放路径

//System.out.println("otherPath:>>>>"+otherPath);
//System.out.println("savePath:"+savePath);
//System.out.println("saveUrl:"+saveUrl);

//定义允许上传的文件扩展名
HashMap<String, String> extMap = new HashMap<String, String>();
extMap.put("image", "gif,jpg,jpeg,png,bmp");
extMap.put("flash", "swf,flv");
/* extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb,mp4"); */
extMap.put("media", "mp4");  //这里暂时只允许传mp4格式的视频
extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

//最大文件大小
long maxSize = 10*1024*1024;

response.setContentType("text/html; charset=UTF-8");

if(!ServletFileUpload.isMultipartContent(request)){
	out.println(getError("请选择文件。"));
	return;
}
//检查目录
File uploadDir = new File(savePath);
if(!uploadDir.isDirectory()){
	uploadDir.mkdir();
	/* out.println(getError("上传目录不存在。"));
	return; */
}
//检查目录写权限
if(!uploadDir.canWrite()){
	out.println(getError("上传目录没有写权限。"));
	return;
}

//待复制的目标路径
File otherDirFile = new File(otherPath);
if (!otherDirFile.exists()) {
	otherDirFile.mkdirs();
}

String dirName = request.getParameter("dir");
if (dirName == null) {
	dirName = "image";
}
if(!extMap.containsKey(dirName)){
	out.println(getError("目录名不正确。"));
	return;
}
//创建文件夹
savePath += dirName + File.separator;
saveUrl += dirName + "/";
otherPath += dirName + File.separator;
File saveDirFile = new File(savePath);
if (!saveDirFile.exists()) {
	saveDirFile.mkdirs();
}
//目标文件夹不存在，就创建
File otherDirFile1 = new File(otherPath);
if (!otherDirFile1.exists()) {
	otherDirFile1.mkdirs();
}

SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
String ymd = sdf.format(new Date());
savePath += ymd + File.separator;
saveUrl += ymd + "/";
otherPath += ymd + File.separator;
File dirFile = new File(savePath);
if (!dirFile.exists()) {
	dirFile.mkdirs();
}

File otherDirFile2 = new File(otherPath);
if (!otherDirFile2.exists()) {
	otherDirFile2.mkdirs();
}

FileItemFactory factory = new DiskFileItemFactory();
ServletFileUpload upload = new ServletFileUpload(factory);
upload.setHeaderEncoding("UTF-8");
List items = upload.parseRequest(request);
Iterator itr = items.iterator();
while (itr.hasNext()) {
	FileItem item = (FileItem) itr.next();
	String fileName = item.getName();
	long fileSize = item.getSize();
	if (!item.isFormField()) {
		//检查文件大小
		if(item.getSize() > maxSize){
			out.println(getError("上传文件大小超过限制。"));
			return;
		}
		//检查扩展名
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
			out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
			return;
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
		try{
			File uploadedFile = new File(savePath, newFileName);
			item.write(uploadedFile);
			//复制文件(项目中需要自己实现的方法)
			FileUtil util = new FileUtil();
			util.copyTo(savePath+newFileName, otherPath);
			
			
		}catch(Exception e){
			out.println(getError("上传文件失败"));
			return;
		}

		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		obj.put("url", saveUrl + newFileName);
		out.println(obj.toJSONString());
	}
}
%>
<%!
private String getError(String message) {
	JSONObject obj = new JSONObject();
	obj.put("error", 1);
	obj.put("message", message);
	return obj.toJSONString();
}
%>