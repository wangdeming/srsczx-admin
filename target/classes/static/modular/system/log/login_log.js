/**
 * 日志管理初始化
 */
var LoginLog = {
    id: "LoginLogTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
LoginLog.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '日志名称', field: 'logname', align: 'center', valign: 'middle', sortable: true},
        {title: '用户名称', field: 'userName', align: 'center', valign: 'middle'},
        {title: '时间', field: 'createtime', align: 'center', valign: 'middle', sortable: true},
        {title: '具体消息', field: 'message', align: 'center', valign: 'middle', sortable: true},
        {title: 'ip', field: 'ip', align: 'center', valign: 'middle', sortable: true}];
};

/**
 * 检查是否选中
 */
LoginLog.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        LoginLog.seItem = selected[0];
        return true;
    }
};


/**
 * 清空日志
 */
LoginLog.delLog = function () {
    Feng.confirm("是否清空所有日志?", function () {
        var ajax = Feng.baseAjax("/loginLog/delLoginLog", "清空日志");
        ajax.start();
        LoginLog.table.refresh();
    });
}

/**
 * 查询日志列表
 */
LoginLog.search = function () {
    var queryData = {};

    queryData['logName'] = $("#logName").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();

    LoginLog.table.refresh({query: queryData});
};

$(function () {

    init();

    var defaultColunms = LoginLog.initColumn();
    var table = new BSTable(LoginLog.id, "/loginLog/list", defaultColunms);
    table.setPaginationType("server");
    table.showToolbar=false;
    LoginLog.table = table.init();

    var r_data = {
        elem: '#beginTime',
        type:'datetime',
        format: 'yyyy-MM-dd HH:mm:ss',
        max:getNowFormatDate(),
    };
    var end_data = {
        elem: '#endTime',
        type:'datetime',
        format: 'yyyy-MM-dd HH:mm:ss',
        max:getNowFormatDate(),
    };

    laydate.render(r_data);
    laydate.render(end_data);
});

function init() {

    var BootstrapTable = $.fn.bootstrapTable.Constructor;
    BootstrapTable.prototype.onSort = function (event) {
        var $this = event.type === "keypress" ? $(event.currentTarget) : $(event.currentTarget).parent(),
            $this_ = this.$header.find('th').eq($this.index()),
            sortName = this.header.sortNames[$this.index()];

        this.$header.add(this.$header_).find('span.order').remove();

        if (this.options.sortName === $this.data('field')) {
            this.options.sortOrder = this.options.sortOrder === 'asc' ? 'desc' : 'asc';
        } else {
            this.options.sortName = sortName || $this.data('field');
            this.options.sortOrder = $this.data('order') === 'asc' ? 'desc' : 'asc';
        }
        this.trigger('sort', this.options.sortName, this.options.sortOrder);

        $this.add($this_).data('order', this.options.sortOrder);

        // Assign the correct sortable arrow
        this.getCaret();

        if (this.options.sidePagination === 'server') {
            this.initServer(this.options.silentSort);
            return;
        }

        this.initSort();
        this.initBody();
    };
    BootstrapTable.prototype.getCaret = function () {
        var that = this;

        $.each(this.$header.find('th'), function (i, th) {
            var sortName = that.header.sortNames[i];
            $(th).find('.sortable').removeClass('desc asc').addClass((sortName || $(th).data('field')) === that.options.sortName ? that.options.sortOrder : 'both');
        });
    };
}

function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    var hours=date.getHours();
    var minutes=date.getMinutes();
    var seconds=date.getSeconds();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    if (hours >= 0 && hours <= 9) {
        hours = "0" + hours;
    }
    if (minutes >= 0 && minutes <= 9) {
        minutes = "0" + minutes;
    }
    if (seconds >= 0 && seconds <= 9) {
        seconds = "0" + seconds;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
        + " " + hours + seperator2 + minutes + seperator2 + seconds;
    return currentdate;
}
