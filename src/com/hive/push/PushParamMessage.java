package com.hive.push;

/**
 * @Description 推送消息中的消息内容参数message（用于简化推送消息时message参数的构造）
 * @Created 2014-6-10
 * @author Ryu Zheng
 * 
 *         ====================================================================
 *         ============ 参数名称 数据类型 说明</br>
 * 
 *         title string 通知标题，可以为空；如果为空则设为appid对应的应用名；</br> description string
 *         通知文本内容，不能为空；</br> open_type int 点击通知后的行为 1: 表示打开Url 2: 表示打开应用
 *         i.如果pkg_content有定义，则按照自定义行为打开应用 ii.如果pkg_content无定义，则启动app的launcher
 *         activity url string 只有open_type为1时才有效，为需要打开的url地址； user_confirm int
 *         只有open_type为1时才有效 1: 表示打开url地址时需要经过用户允许 0：默认值，表示直接打开url地址不需要用户允许
 *         pkg_content string 只有open_type为2时才有效,
 *         Android端SDK会把pkg_content字符串转换成Android
 *         Intent,通过该Intent打开对应app组件，所以pkg_content字符串格式必须遵循Intent
 *         uri格式，最简单的方法可以通过Intent方法toURI()获取 custom_content object
 *         只有open_type为2时才有效
 *         ,自定义内容，键值对，Json对象形式(可选)；在android客户端，这些键值对将以Intent中的extra进行传递。</br>
 *         ====
 *         ==================================================================
 *         ==========
 * 
 */
public class PushParamMessage {

	private String title;
	private String description;
	private int open_type = 0;
	private Object custom_content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getOpen_type() {
		return open_type;
	}

	public void setOpen_type(int open_type) {
		this.open_type = open_type;
	}

	public Object getCustom_content() {
		return custom_content;
	}

	public void setCustom_content(Object custom_content) {
		this.custom_content = custom_content;
	}

}
