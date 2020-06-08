/**
 * 招商管理管理初始化
 */
var Company = {
    id: "CompanyTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Company.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true,},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '阅读状态', field: 'isRead', visible: false, align: 'center', valign: 'middle'},
        {title: '企业名称', field: 'name', align: 'center', valign: 'middle'},
        {title: '成立时间', field: 'setTime',align: 'center', valign: 'middle'},
        {title: '主营业务', field: 'coreBusiness',align: 'center', valign: 'middle',width:'400px'},
        {title: '联系人姓名', field: 'contactName',align: 'center', valign: 'middle'},
        {title: '联系电话', field: 'contactPhone',align: 'center', valign: 'middle'},
        {title: '投递时间', field: 'createdTime',align: 'center', valign: 'middle',sortable: true},
        {title: '状态', field: 'readStatus',align: 'center', valign: 'middle'},
        {title: '操作', field: '',align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return '<a style="margin-right: 15px;" onclick="Company.export('+row.id+')">导出</a>'
            }
        },
    ];
};

/**
 * 检查是否选中
 */
Company.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Company.seItem = selected;
        return true;
    }
};

/**
 * 打开查看留言详情
 */
Company.checkDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '入孵申请详情',
        area: ['750px', '600px'], //宽高
        fix: false, //不固定
        maxmin: false,
        content: Feng.ctxPath + '/company/company_detail/' + id,
        end: function () {
            Company.search();
        }

    });
    this.layerIndex = index;
};

Company.search = function () {
    var queryData = {};
    queryData['isRead']=$('.radio input[type="radio"]:checked').val();
    Company.table.refresh({query: queryData});
};


/**
 * 标记为已读
 */
Company.hasRead=function(){
    if (this.check()) {
        var companyIdsData=[];
        for(var i=0;i<Company.seItem.length;i++){
            companyIdsData.push(Company.seItem[i].id);
        }
        var companyIds = companyIdsData.join(",");
        var ajax = new $ax(Feng.ctxPath + "/company/read", function (data) {
            if(data.code==200){
                Feng.success("标记成功!");
                Company.table.refresh();
            }
        }, function (data) {
            Feng.error("标记失败!" + data.responseJSON.message + "!");
        });
        ajax.set('companyIds',companyIds);
        ajax.start();
    }
};

/**
 * 导出
 */
Company.export = function (id) {
    window.location = Feng.ctxPath + "/company/export?companyIds="+id;
};


/**
 * 批量导出
 */
Company.batchExport=function(){
    if (this.check()) {
        var companyIdsData=[];
        for(var i=0;i<Company.seItem.length;i++){
            companyIdsData.push(Company.seItem[i].id);
        }
        var companyIds = companyIdsData.join(",");
        window.location = Feng.ctxPath + "/company/export?companyIds="+companyIds;
    }
};


$(function () {
    var defaultColunms = Company.initColumn();
    var table = new BSTable(Company.id, "/company/list", defaultColunms);
    table.method='get';
    table.showToolbar=false;
    table.queryParams={'isRead': 0};
    Company.table = table.init();

    $('.radio input[type="radio"]').change(function(e){
        if($(this).is(':checked')){
            var state=$(this).val();
            Company.table.refresh({query: {'isRead': state}});
        }
    });
});
