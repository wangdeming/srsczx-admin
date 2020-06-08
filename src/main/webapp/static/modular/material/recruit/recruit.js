/**
 * 人才招聘管理初始化
 */
var Recruit = {
    id: "RecruitTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Recruit.initColumn = function () {
    return [
        {field: 'selectItem', visible: false},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '序号', field: '', align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                var pageSize=$('#RecruitTable').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
                var pageNumber=$('#RecruitTable').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
                return pageSize * (pageNumber - 1) + index + 1;//返回每条的序号： 每页条数 * （当前页 - 1 ）+ 序号
            }
        },
        {title: '标题', field: 'title',align: 'center', valign: 'middle', width: 250},
        {title: '招聘单位', field: 'company',align: 'center', valign: 'middle'},
        {title: '状态', field: 'status', align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if(row.status==1){
                    return '发布中'
                }else{
                    return '未发布'
                }
            }
        },
        {title: '展示时间', field: 'showDatetime',align: 'center', valign: 'middle',sortable: true},
        {title: '最后编辑时间', field: 'modifiedTime',align: 'center', valign: 'middle',sortable: true},
        {title: '操作', field: '',align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if(row.status==1){
                    return ''
                        + '<a style="margin-right: 15px;" onclick="Recruit.openRecruitDetail('+row.id+')">详情</a>'
                        + '<a style="margin-right: 15px;" onclick="Recruit.publish('+row.id+', 0)">取消发布</a>'
                }else{
                    return ''
                        + '<a style="margin-right: 15px;" onclick="Recruit.edit('+row.id+')">编辑</a>'
                        + '<a style="margin-right: 15px;" onclick="Recruit.publish('+row.id+', 1)">发布</a>'
                        + '<a style="margin-right: 15px;" onclick="Recruit.delete('+row.id+')">删除</a>'
                }
            }
        }
    ];
};

/**
 * 发布或取消发布（status=1为发布，status=0为取消发布）
 */
Recruit.publish = function (id, status) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/recruit/publish", function (data) {
            if(data.code==200){
                if (status == 0) {
                    Feng.success("取消发布成功!");
                } else {
                    Feng.success("发布成功!");
                }
                Recruit.table.refresh();
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
        ajax.set("recruitId",id);
        ajax.set("status",status);
        ajax.start();
    };
    if (status == 0) {
        Feng.confirm("是否取消发布该招聘信息？", operation);
    } else {
        Feng.confirm("是否发布该招聘信息？", operation);
    }
}

/**
 * 打开查看人才招聘详情
 */
Recruit.openRecruitDetail = function (id) {
    window.location=Feng.ctxPath + "/recruit/recruit_detail/"+id;
};

/**
 * 点击进入编辑人才招聘页面
 */
Recruit.edit = function (id) {
    window.location=Feng.ctxPath + "/recruit/recruit_update/"+id;
};

/**
 * 删除人才招聘
 */
Recruit.delete = function (id) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/recruit/delete", function (data) {
            Feng.success("删除成功!");
            Recruit.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("recruitId", id);
        ajax.start();
    };
    Feng.confirm("是否删除该招聘信息？", operation);
};

/**
 * 查询人才招聘列表
 */
Recruit.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['status'] = $("#isPublish").val();
    Recruit.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Recruit.initColumn();
    var table = new BSTable(Recruit.id, "/recruit/list", defaultColunms);
    table.showToolbar=false;
    Recruit.table = table.init();
});
