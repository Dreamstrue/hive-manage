_CalF = {
    $:function(arg,context){
        var tagAll,n,eles=[],i,sub = arg.substring(1);
        context = context||document;
        if(typeof arg =='string'){
            switch(arg.charAt(0)){
                case '#':
                    return document.getElementById(sub);
                    break;
                case '.':
                    if(context.getElementsByClassName) return context.getElementsByClassName(sub);
                    tagAll = _CalF.$('*',context);
                    n = tagAll.length;
                    for(i = 0;i<n;i++){
                        if(tagAll[i].className.indexOf(sub) > -1) eles.push(tagAll[i]);
                    }
                    return eles;
                    break;
                default:
                    return context.getElementsByTagName(arg);
                    break;
            }
        }
    },
    bind:function(node,type,handler){
        node.addEventListener?node.addEventListener(type, handler, false):node.attachEvent('on'+ type, handler);
    },
    getPos:function (node) {
        var scrollx = document.documentElement.scrollLeft || document.body.scrollLeft,
                scrollt = document.documentElement.scrollTop || document.body.scrollTop;
        pos = node.getBoundingClientRect();
        return {top:pos.top + scrollt, right:pos.right + scrollx, bottom:pos.bottom + scrollt, left:pos.left + scrollx }
    },
    addClass:function(c,node){
        node.className = node.className + ' ' + c;
    },
    removeClass:function(c,node){
        var reg = new RegExp("(^|\\s+)" + c + "(\\s+|$)","g");
        node.className = node.className.replace(reg, '');
    },
    stopPropagation:function(event){
        event = event || window.event;
        event.stopPropagation ? event.stopPropagation() : event.cancelBubble = true;
    }
};
function Calender() {
    this.initialize.apply(this, arguments);
}
Calender.prototype = {
    constructor:Calender,
    _template :[
        '<dl>',
        '<dt class="title-date">',
        '<span class="prevyear">prevyear</span><span class="prevmonth">prevmonth</span>',
        '<span class="nextyear">nextyear</span><span class="nextmonth">nextmonth</span>',
        '</dt>',
        '<dt><strong>日</strong></dt>',
        '<dt>一</dt>',
        '<dt>二</dt>',
        '<dt>三</dt>',
        '<dt>四</dt>',
        '<dt>五</dt>',
        '<dt><strong>六</strong></dt>',
        '<dd></dd>',
        '</dl>'],
    initialize :function (options) {
        this.id = options.id; 
        this.input = _CalF.$('#'+ this.id);  
        this.isSelect = options.isSelect;   
        this.inputEvent();  
    },
    createContainer:function(){
        var odiv = _CalF.$('#'+ this.id + '-date');
        if(!!odiv) odiv.parentNode.removeChild(odiv);
        var container = this.container = document.createElement('div');
        container.id = this.id + '-date';
        container.style.position = "absolute";
        container.zIndex = 999;
        var input = _CalF.$('#' + this.id),
                inputPos = _CalF.getPos(input);
        container.style.left = inputPos.left + 'px';
        container.style.top = inputPos.bottom - 1 + 'px';
        _CalF.bind(container, 'click', _CalF.stopPropagation);
        document.body.appendChild(container);
    },
    drawDate:function (odate) {  
        var dateWarp, titleDate, dd, year, month, date, days, weekStart,i,l,ddHtml=[],textNode;
        var nowDate = new Date(),nowyear = nowDate.getFullYear(),nowmonth = nowDate.getMonth(),
                nowdate = nowDate.getDate();
        this.dateWarp = dateWarp = document.createElement('div');
        dateWarp.className = 'calendar';
        dateWarp.innerHTML = this._template.join('');
        this.year = year = odate.getFullYear();
        this.month = month = odate.getMonth()+1;
        this.date = date = odate.getDate();
        this.titleDate = titleDate = _CalF.$('.title-date', dateWarp)[0];
        if(this.isSelect){
            var selectHtmls =[];
            selectHtmls.push('<select style="height: 30px;font-size:14px;">');
            var myDate = new Date();
            for(i =myDate.getFullYear();i>1971;i--){
                if(i != this.year){
                    selectHtmls.push('<option style="height: 30px;" value ="'+ i +'">'+ i +'</option>');
                }else{
                    selectHtmls.push('<option style="height: 30px;font-size:18px;" value ="'+ i +'" selected>'+ i +'</option>');
                }
            }
            selectHtmls.push('</select>');
            selectHtmls.push('年');
            selectHtmls.push('<select style="height: 30px;font-size:14px;">');
            for(i = 1;i<13;i++){
                if(i != this.month){
                    selectHtmls.push('<option style="height: 30px;font-size:18px;" value ="'+ i +'">'+ i +'</option>');
                }else{
                    selectHtmls.push('<option style="height: 30px;font-size:18px; " value ="'+ i +'" selected>'+ i +'</option>');
                }
            }
            selectHtmls.push('</select>');
            selectHtmls.push('月');
            titleDate.innerHTML = selectHtmls.join('');
            this.selectChange();
        }else{
            textNode = document.createTextNode(year + '年' + month + '月');
            titleDate.appendChild(textNode);
            this.btnEvent();
        }
        this.dd = dd = _CalF.$('dd',dateWarp)[0];
        days = new Date(year, month, 0).getDate();
        weekStart = new Date(year, month-1,1).getDay();
        for (i = 0; i < weekStart; i++) {
            ddHtml.push('<a>&nbsp;</a>');
        }
        for (i = 1; i <= days; i++) {
            if (year < nowyear) {
                ddHtml.push('<a class="live">' + i + '</a>');
            } else if (year == nowyear) {
                if (month < nowmonth + 1) {
                    ddHtml.push('<a class="live">' + i + '</a>');
                } else if (month == nowmonth + 1) {
                    if (i < nowdate) ddHtml.push('<a class="live">' + i + '</a>');
                    if (i == nowdate) ddHtml.push('<a class="live tody">' + i + '</a>');
                    if (i > nowdate) ddHtml.push();
                } 
            } 
        }
        dd.innerHTML = ddHtml.join('');

        this.removeDate();
        this.container.appendChild(dateWarp);

        var ie6  = !!window.ActiveXObject && !window.XMLHttpRequest;
        if(ie6) dateWarp.appendChild(this.createIframe());

        this.linkOn();
        this.outClick();
    },

    createIframe:function(){
        var myIframe =  document.createElement('iframe');
        myIframe.src = 'about:blank';
        myIframe.style.position = 'absolute';
        myIframe.style.zIndex = '-1';
        myIframe.style.left = '-1px';
        myIframe.style.top = 0;
        myIframe.style.border = 0;
        myIframe.style.filter = 'alpha(opacity= 0 )';
        myIframe.style.width = this.container.offsetWidth + 'px';
        myIframe.style.height = this.container.offsetHeight + 'px';
        return myIframe;

    },

    selectChange:function(){
        var selects,yearSelect,monthSelect,that = this;
        selects = _CalF.$('select',this.titleDate);
        yearSelect = selects[0];
        monthSelect = selects[1];
        _CalF.bind(yearSelect, 'change',function(){
            var year = yearSelect.value;
            var month = monthSelect.value;
            that.drawDate(new Date(year, month-1, that.date));
        });
        _CalF.bind(monthSelect, 'change',function(){
            var year = yearSelect.value;
            var month = monthSelect.value;
            that.drawDate(new Date(year, month-1, that.date));
        })
    },
    removeDate:function(){
        var odiv = _CalF.$('.calendar',this.container)[0];
        if(!!odiv) this.container.removeChild(odiv);
    },
    btnEvent:function(){
        var prevyear = _CalF.$('.prevyear',this.dateWarp)[0],
                prevmonth = _CalF.$('.prevmonth',this.dateWarp)[0],
                nextyear = _CalF.$('.nextyear',this.dateWarp)[0],
                nextmonth = _CalF.$('.nextmonth',this.dateWarp)[0],
                that = this;
        prevyear.onclick = function(){
            var idate = new Date(that.year-1, that.month-1, that.date);
            that.drawDate(idate);
        };
        prevmonth.onclick = function(){
            var idate = new Date(that.year, that.month-2,that.date);
            that.drawDate(idate);
        };
        nextyear.onclick = function(){
            var idate = new Date(that.year + 1,that.month - 1, that.date);
            that.drawDate(idate);
        };
        nextmonth.onclick = function(){
            var idate = new Date(that.year , that.month, that.date);
            that.drawDate(idate);
        }
    },
    linkOn:function(){
        var links = _CalF.$('.live',this.dd),i,l=links.length,that=this;
        for(i = 0;i<l;i++){
            links[i].index = i;
            links[i].onmouseover = function(){
                _CalF.addClass("on",links[this.index]);
            };
            links[i].onmouseout = function(){
                _CalF.removeClass("on",links[this.index]);
            };
            links[i].onclick = function(){
                that.date = this.innerHTML;
                that.input.value = that.year + '-' + that.month + '-' + that.date;
                that.removeDate();
            }
        }
    },
    inputEvent:function(){
        var that = this;
        _CalF.bind(this.input, 'focus',function(){
            that.createContainer();
            that.drawDate(new Date());
        });
    },
    outClick:function(){
        var that = this;
        _CalF.bind(document, 'click',function(event){
            event = event || window.event;
            var target = event.target || event.srcElement;
            if(target == that.input)return;
            that.removeDate();
        })
    }
};