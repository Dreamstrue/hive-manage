function AddAttachments() {
    document.getElementById('attach').values = "继续添加附件";
    tb = document.getElementById('attAchments');
    var upFileName = "";
    
    if (tb.childNodes.length > 0) {
        var tr = tb.childNodes.item(tb.childNodes.length-1).childNodes;
        if (tr.length > 0) {
            var fileName = tr[tr.length - 1].lastChild.firstChild.value
            var attrFile = fileName.split(".");
            var extName = attrFile[attrFile.length - 1];
            if (extName != "pdf" && extName != "jpg" && extName != "png" && extName != "doc" && extName != "docx" && extName != "xls" && extName != "xlsx") {
                alert("最新选择的文件格式类型不对，请您重新选择文件！");
                return;
            }
        }
    }
    
    newRow = tb.insertRow();
    newRow.insertCell().innerHTML = "<input name='File' size='50' style='float:left;' onchange='CheckFileType(this)' type='file'>&nbsp;&nbsp;<input type=button value=''class='scInput'style='margin-top:0px;' onclick='delFile(this.parentElement.parentElement.rowIndex)'><br/>";

}
function delFile(index) {
    document.getElementById('attAchments').deleteRow(index);
    tb.rows.length > 0 ? document.getElementById('attach').innerText = "继续添加附件" : document.getElementById('attach').value = "添加附件";
}
//验证文件的格式类型
function CheckFileType(obj) {
    var fileName = obj.value;
    if (fileName != "") { 
        var attrFile = fileName.split(".");
        var extName = attrFile[attrFile.length - 1];
        if (extName != "pdf" && extName != "jpg" && extName != "png" && extName != "doc" && extName != "docx" && extName != "xls" && extName != "xlsx") { 
            alert("最新选择的文件格式类型不对，请您重新选择文件！");
            document.getElementById('attAchments').deleteRow(obj.parentElement.parentElement.rowIndex); //文件格式不对时，删除当前行
            return;
        } 
        $(obj).parent("td").children(".filename").html(fileName);
    }
}
 
    