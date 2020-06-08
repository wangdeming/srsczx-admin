/**
 * 用户详情对话框（可用于添加和修改对话框）
 */
var UserInfoDlg = {
    userInfoData: {},
    validateFields: {
        account: {
            validators: {
                notEmpty: {
                    message: '账户不能为空'
                },
                regexp: {//正则验证
                    regexp: /^[0-9a-z]{4,20}$/,
                    message: '支持小写字母、阿拉伯数字或两者组合，4~20个字符'
                }
            }
        },
        name: {
            validators: {
                notEmpty: {
                    message: '姓名不能为空'
                },
                regexp: {//正则验证
                    regexp: /^[\u4e00-\u9fa5]{2,5}$/,
                    message: '2~5个中文汉字'
                }
            }
        },
        password: {
            validators: {
                regexp: {//正则验证
                    regexp: /^[\da-zA-Z_!@#$%^&*]{6,30}$/,
                    message: '支持大小写字母、阿拉伯数字、符号或组合，6~30个字符，默认密码：111111'
                }
            }
        },
        phone: {
            validators: {
                regexp: {
                    regexp: /^[1][3,4,5,6,7,8,9][0-9]{9}$/,
                    message: '手机号格式错误'
                }
            }
        },
        email: {
            validators: {
                regexp: {
                    regexp: /^[_a-z0-9-\.]+@([-a-z0-9]+\.)+[a-z]{2,}$/i,
                    message: '电子邮箱格式有误'
                }
            }
        },
    }
};

/**
 * 清除数据
 */
UserInfoDlg.clearData = function () {
    this.userInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserInfoDlg.set = function (key, val) {
    this.userInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserInfoDlg.get = function (key) {
    return $("#" + key).val();
};



/**
 * 关闭此对话框
 */
UserInfoDlg.close = function () {
    parent.layer.close(window.parent.MgrUser.layerIndex);
};
/**
 * 收集数据
 */
UserInfoDlg.collectData = function () {
    this.set('account').set('password').set('name')
        .set('phone').set('email');
};
UserInfoDlg.collectEditData = function () {
    this.set('id').set('name')
        .set('phone').set('email');
};
/**
 * 验证数据是否为空
 */
UserInfoDlg.validate = function () {
    $('#userInfoForm').data("bootstrapValidator").resetForm();
    $('#userInfoForm').bootstrapValidator('validate');
    return $("#userInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加用户
 */
UserInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/mgr/add", function (data) {
        if (data.code == 200) {
            Feng.success(data.message);
            window.parent.MgrUser.table.refresh();
            UserInfoDlg.close();
        }else {
            Feng.error(data.message + "!");
        }
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
UserInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectEditData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/mgr/edit", function (data) {
        if (data.code == 200) {
            Feng.success(data.message);
            window.parent.MgrUser.table.refresh();
            UserInfoDlg.close();
        }else {
            Feng.error(data.message + "!");
        }
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userInfoData);
    ajax.start();
};

/**
 * 重置密码
 */
UserInfoDlg.resetPass = function(id) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/mgr/reset", function(data){
            if(data.code==200){
                Feng.success("操作成功!");
            }else{
                Feng.error( data.message + "!");
            }
        });
        ajax.set("userId",id);
        ajax.start();
    };
    Feng.confirm("是否重置密码?", operation);
}

/**
 * 修改密码
 */
UserInfoDlg.editPass = function (id) {
    var index = layer.open({
        type: 2,
        title: '修改密码',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/mgr/user_chpwd'
    });
    this.layerIndex = index;
};

$(function () {
    Feng.initValidator("userInfoForm", UserInfoDlg.validateFields);
});

/**
 * 提交修改
 */
UserInfoDlg.editCenter = function () {

    this.clearData();
    this.collectEditData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/mgr/edit", function (data) {
        if (data.code == 200) {
            Feng.success(data.message);
                var x = window.parent.document.querySelector('#name');
                var y = window.parent.document.querySelector('#phone');
                var z = window.parent.document.querySelector('#email');
                var name=window.parent.parent.document.querySelector('#adminlogoName');
                if(x){
                    x.innerHTML = $('#name').val();
                    y.innerHTML = $('#phone').val();
                    z.innerHTML = $('#email').val();
                    name.innerHTML = $('#name').val();
                }
                UserInfoDlg.close();
        }else {
            Feng.error(data.message + "!");
        }
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userInfoData);
    ajax.start();
};