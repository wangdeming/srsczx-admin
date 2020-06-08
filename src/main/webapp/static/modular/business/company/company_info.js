/**
 * 初始化招商管理详情对话框
 */
var CompanyInfoDlg = {
    companyInfoData : {}
};


/**
 * 关闭此对话框
 */
CompanyInfoDlg.close = function() {
    parent.Company.search();
    parent.layer.close(window.parent.Company.layerIndex);
};

$(function() {
    var ajax = new $ax(Feng.ctxPath + "/company/detail", function (data) {
        if(data.code==200){
            $('#contactName').text(data.data.contactName);
            $('#contactPhone').text(data.data.contactPhone);
            $('#coreBusiness').text(data.data.coreBusiness);
            $('#empNum').text(data.data.empNum);
            $('#name').text(data.data.name);
            $('#setTime').text(data.data.setTime);
            $('#email').text(data.data.email);
            $('#remark').text(data.data.remark);
        }
    }, function (data) {
        Feng.error("获取详情失败!" + data.responseJSON.message + "!");
    });
    ajax.set('id',$('#id').val());
    ajax.start();
});
