/**
 * 角色管理的单例
 */
var Role = {
    id: "roleTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Role.initColumn = function () {
    var columns = [
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '角色名称', field: 'name', align: 'center', valign: 'middle'},
        {title: '角色说明', field: 'tips', align: 'center', valign: 'middle'},
        {title: '用户数量', field: 'userCount', align: 'center', valign: 'middle'},
        {title: '状态', field: 'statusName', align: 'center', valign: 'middle'},
        {title: '操作', field: 'status', align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (row.id == 1){
                    return '<a style="margin-right: 15px;" onclick="Role.managerdetail('+row.id+')">详情</a>'
                } else if (row.status == 1) {
                    if(row.editable == true){
                        return ''
                            + '<a style="margin-right: 15px;" onclick="Role.managerdetail('+row.id+')">详情</a>'
                            + '<a style="margin-right: 15px;" onclick="Role.editRole('+row.id+')">编辑</a>'
                            + '<a style="margin-right: 15px;" onclick="Role.authorityMgr('+row.id+')">权限管理</a>'
                            + '<a style="margin-right: 15px;" onclick="Role.authorizeduser('+row.id+')">授权用户</a>'
                            + '<a onclick="Role.freezeRole('+row.id+',1)">禁用</a>'
                    }else{
                        return ''
                            + '<a style="margin-right: 15px;" onclick="Role.managerdetail('+row.id+')">详情</a>'
                    }
                } else {
                    return ''
                        + '<a style="margin-right: 15px;" onclick="Role.managerdetail('+row.id+')">详情</a>'
                        + '<a style="margin-right: 15px;" onclick="Role.editRole('+row.id+')">编辑</a>'
                        + '<a style="margin-right: 15px;" onclick="Role.authorityMgr('+row.id+')">权限管理</a>'
                        + '<a style="margin-right: 15px;" onclick="Role.authorizeduser('+row.id+')">授权用户</a>'
                        + '<a onclick="Role.openRole('+row.id+',1)">解禁</a>'
                }
            }
        }];
    return columns;
};

/**
 * 搜索角色
 */
Role.search = function () {
    var queryData = {};
    queryData['keyword'] = $("#keyword").val();
    Role.table.refresh({query: queryData});
}

$(function () {
    var defaultColunms = Role.initColumn();
    var table = new BSTable(Role.id, "/role/list", defaultColunms);
    table.setPaginationType("server");
    table.showToolbar=false;
    Role.table = table.init();
});

/**
 * 点击添加角色
 */
Role.addRole = function () {
    var index = layer.open({
        type: 2,
        title: '新建角色',
        area: ['800px', '400px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/role/role_add'
    });
    this.layerIndex = index;
};

/**
 * 点击编辑角色
 */
Role.editRole = function (id) {
    var index = layer.open({
        type: 2,
        title: '编辑角色',
        area: ['800px', '400px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/role/role_edit/' + id
    });
    this.layerIndex = index;
};

/**
 * 详情
 */
Role.managerdetail = function (id) {
    window.location.href= Feng.ctxPath + '/role/detailHtml/'+id;
};
/**
 * 关闭
 */
Role.close = function (id) {
    window.location.href= Feng.ctxPath + '/role';
};

/**
 * 权限管理
 */
Role.authorityMgr = function (id) {
    var index = layer.open({
        type: 2,
        title: '权限管理',
        area: ['800px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/role/role_menu_edit/' + id
    });
    this.layerIndex = index;
};

/**
 * 授权用户
 */
Role.authorizeduser = function (id) {
    var index = layer.open({
        type: 2,
        title: '授权用户',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/role/role_user_edit/' + id
    });
    this.layerIndex = index;
};

/**
 * 禁用
 */
Role.freezeRole = function(id,type) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/role/freeze", function(data){
            if(data.code==200){
                Feng.success("操作成功!");
                if(type==1){
                    Role.table.refresh();
                }else if(type==2){
                    window.location.reload();
                }
            }else{id
                Feng.error( data.message + "!");
            }
        });
        ajax.set("roleId",id);
        ajax.start();
    };
    Feng.confirm("是否禁用角色?", operation);
}

/**
 * 启用
 */
Role.openRole = function(id,type) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/role/open", function(data){
            if(data.code==200){
                Feng.success("操作成功!");
                if(type==1){
                    Role.table.refresh();
                }else if(type==2){
                    window.location.reload();
                }
            }else{
                Feng.error( data.message + "!");
            }
        });
        ajax.set("roleId",id);
        ajax.start();
    };
    Feng.confirm("是否启用角色?", operation);
}