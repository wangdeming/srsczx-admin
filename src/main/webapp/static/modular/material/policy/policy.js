/**
 * 招商政策管理初始化
 */
var Policy = {
    id: "PolicyTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Policy.initColumn = function () {
    return [
        {field: 'selectItem', visible: false},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '序号', field: '', align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                var pageSize=$('#PolicyTable').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
                var pageNumber=$('#PolicyTable').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
                return pageSize * (pageNumber - 1) + index + 1;//返回每条的序号： 每页条数 * （当前页 - 1 ）+ 序号
            }
        },
        {title: '标题', field: 'title',align: 'center', valign: 'middle', width: 250},
        {title: '所属级别', field: 'level',align: 'center', valign: 'middle'},
        {title: '政策来源', field: 'source',align: 'center', valign: 'middle', width: 250},
        {title: '状态', field: 'status', align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if(row.status==1){
                    return '发布中'
                }else{
                    return '未发布'
                }
            }
        },
        {title: '发布时间', field: 'publishDate',align: 'center', valign: 'middle',sortable: true,
            formatter: function (value, row, index) {
                if(row.publishDate){
                    return row.publishDate.substr(0,10);
                }else{
                    return ''
                }
            }
        },
        {title: '最后编辑时间', field: 'modifiedTime',align: 'center', valign: 'middle',sortable: true},
        {title: '操作', field: '',align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if(row.status==1){
                    return ''
                        + '<a style="margin-right: 15px;" onclick="Policy.openPolicyDetail('+row.id+')">详情</a>'
                        + '<a style="margin-right: 15px;" onclick="Policy.publish('+row.id+', 0)">取消发布</a>'
                }else{
                    return ''
                        + '<a style="margin-right: 15px;" onclick="Policy.edit('+row.id+')">编辑</a>'
                        + '<a style="margin-right: 15px;" onclick="Policy.publish('+row.id+', 1)">发布</a>'
                        + '<a style="margin-right: 15px;" onclick="Policy.delete('+row.id+')">删除</a>'
                }
            }
        }
    ];
};

/**
 * 发布或取消发布（status=1为发布，status=0为取消发布）
 */
Policy.publish = function (id, status) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/policy/publish", function (data) {
            if(data.code==200){
                if (status == 0) {
                    Feng.success("取消发布成功!");
                } else {
                    Feng.success("发布成功!");
                }
                Policy.table.refresh();
            }else{
                if (status == 0) {
                    Feng.error("取消发布失败!" + data.message + "!");
                } else {
                    Feng.error("发布失败!" + data.message + "!");
                }
            }
        }, function (data) {
            if (status == 0) {
                Feng.error("取消发布失败!" + data.responseJSON.message + "!");
            } else {
                Feng.error("发布失败!" + data.responseJSON.message + "!");
            }
        });
        ajax.set("policyId",id);
        ajax.set("status",status);
        ajax.start();
    };
    if (status == 0) {
        Feng.confirm("是否取消发布该招商政策？", operation);
    } else {
        Feng.confirm("是否发布该招商政策？", operation);
    }
}

/**
 * 打开查看招商政策详情
 */
Policy.openPolicyDetail = function (id) {
    window.location=Feng.ctxPath + "/policy/policy_detail/"+id;
};

/**
 * 打开编辑招商政策
 */
Policy.edit = function (id) {
    window.location=Feng.ctxPath + "/policy/policy_update/"+id;
};

/**
 * 删除招商政策
 */
Policy.delete = function (id) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/policy/delete", function (data) {
            Feng.success("删除成功!");
            Policy.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("policyId",id);
        ajax.start();
    };
    Feng.confirm("是否删除该政策？", operation);
};

/**
 * 查询招商政策列表
 */
Policy.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['level'] = $("#newsType").val();
    queryData['status'] = $("#isPublish").val();
    Policy.table.refresh({query: queryData});
};

var order1 = '';

$(function () {
    var defaultColunms = Policy.initColumn();
    var table = new BSTable(Policy.id, "/policy/list", defaultColunms);
    table.showToolbar=false;
    Policy.table = table.init();
});
