/**
 * 系统管理--用户管理的单例对象
 */
var MgrUser = {
    id: "managerTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    // deptid:0
};
var total = '';
/**
 * 初始化表格的列
 */
MgrUser.initColumn = function () {
    var columns = [
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '序号', field: '', align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                var pageSize=$('#managerTable').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
                var pageNumber=$('#managerTable').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
                return pageSize * (pageNumber - 1) + index + 1;//返回每条的序号： 每页条数 * （当前页 - 1 ）+ 序号
            }
        },
        {title: '账号', field: 'account', align: 'center', valign: 'middle'},
        {title: '姓名', field: 'name', align: 'center', valign: 'middle'},
        {title: '联系电话', field: 'phone', align: 'center', valign: 'middle'},
        {title: '电子邮箱', field: 'email', align: 'center', valign: 'middle'},
        {title: '状态', field: 'statusName', align: 'center', valign: 'middle'},
        {title: '操作', field: 'status', align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (row.status == 1) {
                    if(row.editable==true){
                        return ''
                            + '<a style="margin-right: 15px;" onclick="MgrUser.resetPass('+row.id+')">重置密码</a>'
                            + '<a style="margin-right: 15px;" onclick="MgrUser.detailsAddMgr('+row.id+')">详情</a>'
                            + '<a style="margin-right: 15px;" onclick="MgrUser.openChangeUser('+row.id+')">编辑</a>'
                            + '<a style="margin-right: 15px;" onclick="MgrUser.openAuthority('+row.id+')">授权功能</a>'
                            + '<a onclick="MgrUser.freezeUser('+row.id+')">冻结</a>'
                    }else{
                        return ''
                            + '<a style="margin-right: 15px;" onclick="MgrUser.detailsAddMgr('+row.id+')">详情</a>'
                    }
                } else {
                    return ''
                        + '<a style="margin-right: 15px;" data-roleid="'+row.id+'" data-account="'+row.id+'" onclick="MgrUser.resetPass('+row.id+')">重置密码</a>'
                        + '<a style="margin-right: 15px;" onclick="MgrUser.detailsAddMgr('+row.id+')">详情</a>'
                        + '<a style="margin-right: 15px;" onclick="MgrUser.openChangeUser('+row.id+')">编辑</a>'
                        + '<a style="margin-right: 15px;" onclick="MgrUser.openAuthority('+row.id+')">授权功能</a>'
                        + '<a onclick="MgrUser.unfreezeUser('+row.id+')">解冻</a>'
                }
            }
        }];
    return columns;
};

/**
 * 点击添加用户
 */
MgrUser.openAddMgr = function () {
    var index = layer.open({
        type: 2,
        title: '新建用户',
        area: ['900px', '560px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/mgr/user_add'
    });
    this.layerIndex = index;
};
/**
 * 点击个人中心编辑按钮时
 * @param userId 用户id
 */
MgrUser.openCenterUser = function (id) {
    var index = layer.open({
        type: 2,
        title: '编辑用户',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/mgr/user_info/edit/' + id
    });
    this.layerIndex = index;
};
/**
 * 用户详情
 */
MgrUser.detailsAddMgr = function (id) {
    var index = layer.open({
        type: 2,
        title: '用户详情',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/mgr/user_details/' + id
    });
    this.layerIndex = index;
};


MgrUser.openAuthority= function (id) {
    var index = layer.open({
        type: 2,
        title: '授权功能',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/mgr/user_authority?userId=' + id
    });
    this.layerIndex = index;
};

/**
 * 点击修改按钮时
 * @param userId 用户id
 */
MgrUser.openChangeUser = function (id) {
        var index = layer.open({
            type: 2,
            title: '编辑用户',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/mgr/user_edit/' + id
        });
        this.layerIndex = index;
};

MgrUser.search = function () {
    var queryData = {};
    queryData['keyword'] = $("#keyword").val();
    MgrUser.table.refresh({query: queryData});
}

$(function () {
    var defaultColunms = MgrUser.initColumn();
    var table = new BSTable("managerTable", "/mgr/list", defaultColunms);
    table.setPaginationType("server");
    table.showToolbar = false;
    MgrUser.table = table.init();
});

/**
 * 重置密码
 */
MgrUser.resetPass = function(id) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/mgr/reset", function(data){
            if(data.code==200){
                Feng.success("操作成功!");
                MgrUser.table.refresh();
            }else{id
                Feng.error( data.message + "!");
            }
        });
        ajax.set("userId",id);
        ajax.start();
    };
    Feng.confirm("是否重置密码?", operation);
}

/**
 * 冻结
 */
MgrUser.freezeUser = function(id) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/mgr/freeze", function(data){
            if(data.code==200){
                Feng.success("操作成功!");
                MgrUser.table.refresh();
            }else{id
                Feng.error( data.message + "!");
            }
        });
        ajax.set("userId",id);
        ajax.start();
    };
    Feng.confirm("是否冻结用户?", operation);
}

/**
 * 解冻
 */
MgrUser.unfreezeUser = function(id) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/mgr/unfreeze", function(data){
            if(data.code==200){
                Feng.success("操作成功!");
                MgrUser.table.refresh();
            }else{id
                Feng.error( data.message + "!");
            }
        });
        ajax.set("userId",id);
        ajax.start();
    };
    Feng.confirm("是否解冻用户?", operation);
}

