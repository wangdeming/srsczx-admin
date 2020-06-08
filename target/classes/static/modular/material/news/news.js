/**
 * 新闻资讯管理管理初始化
 */
var News = {
    id: "NewsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};


/**
 * 初始化表格的列
 */
News.initColumn = function () {
    return [
        {field: 'selectItem', visible: false},
        {title: 'id', field: 'newsId', visible: false, align: 'center', valign: 'middle'},
        {title: '序号', field: '', align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                var pageSize=$('#NewsTable').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
                var pageNumber=$('#NewsTable').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
                return pageSize * (pageNumber - 1) + index + 1;//返回每条的序号： 每页条数 * （当前页 - 1 ）+ 序号
            }
        },
        {title: '标题', field: 'title',align: 'center', valign: 'middle', width: 250},
        {title: '所属栏目', field: 'newsType',align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if(row.newsType==1){
                    return '新闻资讯'
                };
                if(row.newsType==2){
                    return '中心动态'
                };
                if(row.newsType==3){
                    return '重要通知'
                }
            }},
        {title: '是否为图片新闻', field: 'imageNews', align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if(row.imageNews==1){
                    return '是'
                }else{
                    return '否'
                }
            }
        },
        {title: '状态', field: 'isPublish', align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if(row.isPublish==1){
                    return '发布中'
                }else{
                    return '未发布'
                }
            }
        },
        {title: '展示时间', field: 'showTime',align: 'center', valign: 'middle',sortable: true},
        {title: '最后编辑时间', field: 'modifiedTime',align: 'center', valign: 'middle',sortable: true},
        {title: '操作', field: '',align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if(row.isPublish==1){
                    return ''
                        + '<a style="margin-right: 15px;" onclick="News.openNewsDetail('+row.id+')">详情</a>'
                        + '<a style="margin-right: 15px;" onclick="News.cancelPublish('+row.id+')">取消发布</a>'
                }else{
                    return ''
                        + '<a style="margin-right: 15px;" onclick="News.edit('+row.id+')">编辑</a>'
                        + '<a style="margin-right: 15px;" onclick="News.publish('+row.id+')">发布</a>'
                        + '<a style="margin-right: 15px;" onclick="News.delete('+row.id+')">删除</a>'
                }
            }
        }
    ];
};


/**
 * 发布
 */
News.publish = function (id) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/news/publish", function (data) {
            if(data.code==200){
                Feng.success("发布成功!");
                News.table.refresh();
            }else{
                Feng.error("发布失败!" + data.message + "!");
            }
        }, function (data) {
            Feng.error("发布失败!" + data.responseJSON.message + "!");
        });
        ajax.set("newsId",id);
        ajax.start();
    };
    Feng.confirm("是否发布该新闻？", operation);
};

/**
 * 取消发布
 */
News.cancelPublish = function (id) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/news/unpublish", function (data) {
            if(data.code==200){
                Feng.success("取消发布成功!");
                News.table.refresh();
            }else{
                Feng.error("取消发布失败!" + data.message + "!");
            }
        }, function (data) {
            Feng.error("取消发布失败!" + data.responseJSON.message + "!");
        });
        ajax.set("newsId",id);
        ajax.start();
    };
    Feng.confirm("是否取消发布该新闻？", operation);
};

/**
 * 删除新闻资讯管理
 */
News.delete = function (id) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/news/delete", function (data) {
            if(data.code==200){
                Feng.success("删除成功!");
                News.table.refresh();
            }else{
                Feng.error("删除失败!" + data.message + "!");
            }
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("newsId",id);
        ajax.start();
    };
    Feng.confirm("是否删除该新闻？", operation);
};

/**
 * 编辑
 */
News.edit = function (id) {
    window.location=Feng.ctxPath + "/news/news_update/"+id;
};

/**
 * 详情
 */
News.openNewsDetail = function (id) {
    window.location=Feng.ctxPath + "/news/news_detail/"+id;
};



/**
 * 查询新闻资讯管理列表
 */
News.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['newsType'] = $("#newsType").val();
    queryData['isPublish'] = $("#isPublish").val();
    queryData['imageNews'] = $("#imageNews").val();
    News.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = News.initColumn();
    var table = new BSTable(News.id, "/news/list", defaultColunms);
    table.showToolbar=false;
    News.table = table.init();
});
