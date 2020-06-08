/**
 * 初始化新闻资讯管理详情对话框
 */
var NewsInfoDlg = {
    newsInfoData : {},
    validateFields: {
        title: {
            validators: {
                notEmpty: {
                    message: '政策标题不能为空'
                },
                regexp: {//正则验证
                    regexp: /^.{2,80}$/,
                    message: '2-80字符'
                }
            }
        },
        from: {
            validators: {
                notEmpty: {
                    message: '政策来源不能为空'
                },
                regexp: {//正则验证
                    regexp: /^.{2,40}$/,
                    message: '2-40字符'
                }
            }
        }
    }

};

var E= window.wangEditor;
var editor = new E('#editor');
// editor.customConfig.uploadImgShowBase64 = true;  // 使用 base64 保存图片
editor.customConfig.uploadImgServer = Feng.ctxPath + '/fastDfs/upload';
editor.customConfig.uploadImgHooks = {
    customInsert: function (insertImg, result, editor) {
        if(result.code==200){
            var url = result.data;
            insertImg(url);
        }else{
            Feng.error("添加失败!" + result.message + "!");
        }
    }
};
//粘贴来的内容过滤图片
editor.customConfig.pasteIgnoreImg = true;
// 关闭粘贴样式的过滤
editor.customConfig.pasteFilterStyle = false;
// 自定义处理粘贴的文本内容
editor.customConfig.pasteTextHandle = function (content) {
    // content 即粘贴过来的内容（html 或 纯文本），可进行自定义处理然后返回
    // return content + '<p>在粘贴内容后面追加一行</p>'
    if (content == '' && !content) return ''
    var input = content;
    console.log('input----',input)
    // 1. remove line breaks / Mso classes
    var stringStripper = /(\n|\r| class=(")?Mso[a-zA-Z]+(")?)/g;
    var output = input.replace(stringStripper, ' ');
    // 2. strip Word generated HTML comments
    var commentSripper = new RegExp('<!--(.*?)-->','g');
    var output = output.replace(commentSripper, '');
    var tagStripper = new RegExp('<(/)*(meta|link|span|\\?xml:|st1:|o:|font)(.*?)>','gi');
    // 3. remove tags leave content if any
    output = output.replace(tagStripper, '');
    // 4. Remove everything in between and including tags '<style(.)style(.)>'
    var badTags = ['style', 'script','applet','embed','noframes','noscript'];

    for (var i=0; i< badTags.length; i++) {
        tagStripper = new RegExp('<'+badTags[i]+'.*?'+badTags[i]+'(.*?)>', 'gi');
        output = output.replace(tagStripper, '');
    }
    // 5. remove attributes ' style="..."'
    var badAttributes = ['style', 'start'];
    for (var i=0; i< badAttributes.length; i++) {
        var attributeStripper = new RegExp(' ' + badAttributes[i] + '="(.*?)"','gi');
        output = output.replace(attributeStripper, '');
    }
    return output;
};
editor.customConfig.showLinkImg = false; // 隐藏“网络图片”tab
editor.customConfig.onchange = function (html) {
    if(editor.txt.text()){
        $('#editor').parents('.form-group').find('.help-block').remove();
        $('#editor').parents('.form-group').addClass('has-success').removeClass('has-error');
    }else{
        $('#editor').parents('.form-group').find('.help-block').remove();
        $('#editor').parents('.form-group').addClass('has-error').removeClass('has-success');
        var tip=$('<small class="help-block" data-bv-validator="notEmpty" data-bv-for="title" data-bv-result="INVALID" style="">' +
            '正文内容不能为空' +
            '</small>')
        $('#editor').after(tip);
    }
};
editor.create();


/**
 * 关闭此对话框
 */
NewsInfoDlg.close = function() {
    var operation = function(){
        window.location=Feng.ctxPath + "/policy"
    };
    Feng.confirm("系统将不会保存当前页面所填写的内容。是否取消？", operation);
}

/**
 * 关闭此对话框
 */
NewsInfoDlg.detailClose = function() {
    window.location=Feng.ctxPath + "/policy";
}

/**
 * 收集数据
 */
NewsInfoDlg.collectData = function() {
    this.set('title').set('level');
};

/**
 * 验证数据是否为空
 */
NewsInfoDlg.validate = function () {
    $('#NewsForm').data("bootstrapValidator").resetForm();
    $('#NewsForm').bootstrapValidator('validate');
    return $("#NewsForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
NewsInfoDlg.addSubmit = function() {
    if (!this.validate()) {
        if(!editor.txt.text()){
            $('#editor').parents('.form-group').find('.help-block').remove();
            $('#editor').parents('.form-group').addClass('has-error');
            var tip=$('<small class="help-block" data-bv-validator="notEmpty" data-bv-for="title" data-bv-result="INVALID" style="">' +
                '正文内容不能为空' +
                '</small>')
            $('#editor').after(tip);
        }
        return;
    }else{
        if(!editor.txt.text()){
            $('#editor').parents('.form-group').find('.help-block').remove();
            $('#editor').parents('.form-group').addClass('has-error');
            var tip=$('<small class="help-block" data-bv-validator="notEmpty" data-bv-for="title" data-bv-result="INVALID" style="">' +
                '正文内容不能为空' +
                '</small>')
            $('#editor').after(tip);
            return;
        }
    }

    var content = editor.txt.html();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/policy/add", function(data){
        if(data.code==200){
            // Feng.success("添加成功!");
            var id=data.data;
            layer.confirm('保存成功，是否立即发布？', {
                btn: ['是','否'] //按钮
            }, function(){
                var ajax = new $ax(Feng.ctxPath + "/policy/publish", function (data) {
                    if(data.code==200){
                        Feng.success("发布成功!");
                        window.location=Feng.ctxPath + "/policy"
                    }else{
                        Feng.error("发布失败!" + data.message + "!");
                    }
                }, function (data) {
                    Feng.error("发布失败!" + data.responseJSON.message + "!");
                });
                ajax.set("policyId",id);
                ajax.set("status",1);
                ajax.start();
            }, function(){
                window.location=Feng.ctxPath + "/policy"
            });
        }else{
            Feng.error("添加失败!" + data.message + "!");
        }


    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.contentType='application/x-www-form-urlencoded';
    ajax.dataType='json';
    NewsInfoDlg.newsInfoData['title']=$('#title').val();
    NewsInfoDlg.newsInfoData['level']=$('#newsType').val();
    NewsInfoDlg.newsInfoData['source']=$('#from').val();
    NewsInfoDlg.newsInfoData['content']=content;
    NewsInfoDlg.newsInfoData['attachment']=$('#extraFile').attr('data-src');
    NewsInfoDlg.newsInfoData['attachmentName']= $('#extraFile').attr('data-name');
    NewsInfoDlg.newsInfoData['publishDate']= select_date;
    ajax.setData(NewsInfoDlg.newsInfoData);
    ajax.start();

}

/**
 * 提交修改
 */
NewsInfoDlg.editSubmit = function() {
    if (!this.validate()) {
        if(!editor.txt.text()){
            $('#editor').parents('.form-group').find('.help-block').remove();
            $('#editor').parents('.form-group').addClass('has-error');
            var tip=$('<small class="help-block" data-bv-validator="notEmpty" data-bv-for="title" data-bv-result="INVALID" style="">' +
                '正文内容不能为空' +
                '</small>')
            $('#editor').after(tip);
        }
        return;
    }else{
        if(!editor.txt.text()){
            $('#editor').parents('.form-group').find('.help-block').remove();
            $('#editor').parents('.form-group').addClass('has-error');
            var tip=$('<small class="help-block" data-bv-validator="notEmpty" data-bv-for="title" data-bv-result="INVALID" style="">' +
                '正文内容不能为空' +
                '</small>')
            $('#editor').after(tip);
            return;
        }
    }

    var content = editor.txt.html();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/policy/update", function(data){
        if(data.code==200){
            Feng.success("修改成功!");
            window.location=Feng.ctxPath + "/policy"
        }else{
            Feng.error("修改失败!" + data.message + "");
        }
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.contentType='application/x-www-form-urlencoded';
    ajax.dataType='json';
    NewsInfoDlg.newsInfoData['title']=$('#title').val();
    NewsInfoDlg.newsInfoData['level']=$('#newsType').val();
    NewsInfoDlg.newsInfoData['source']=$('#from').val();
    NewsInfoDlg.newsInfoData['content']=content;
    NewsInfoDlg.newsInfoData['attachment']=$('#extraFile').attr('data-src');
    NewsInfoDlg.newsInfoData['attachmentName']= $('#extraFile').attr('data-name');
    NewsInfoDlg.newsInfoData['publishDate']= select_date;
    NewsInfoDlg.newsInfoData['id']= $("#id").val();
    ajax.setData(NewsInfoDlg.newsInfoData);
    ajax.start();
}

function getObjectURL(file) {
    var url = null ;
    if (window.createObjectURL!=undefined) { // basic
        url = window.createObjectURL(file) ;
    } else if (window.URL!=undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file) ;
    } else if (window.webkitURL!=undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file) ;
    }
    return url ;
}

var select_date = getNowFormatDate();
$(function() {
    if ($("#isDetail").val()) {
        editor.$textElem.attr('contenteditable', false);
        $(".w-e-toolbar").hide();
    }

    $("#queryTime").datepicker({
        format: 'yyyy-mm-dd',
        startView: 0,
        language:  'zh-CN',
        autoclose:true,
        endDate:new Date()
    }).on('changeDate',function(ev){
        select_date = $("#queryTime").val();

        $("#queryTime").datepicker('hide');
    });

    $("#queryTime").val(getNowFormatDate());

    if ($("#publishDate").val()) {
        var date = new Date($("#publishDate").val());
        var year = date.getFullYear();
        var month = date.getMonth() + 1 > 9 ? date.getMonth() + 1 : '0' + (date.getMonth() + 1);
        var day = date.getDate() > 9 ? date.getDate(): '0' + date.getDate();
        var publishDate = year + '-' + month + '-' + day;
        $("#queryTime").val(publishDate);
    }

    if($('#fileName').text() != '') {
        $("#closeImg").css("display",'inline-block');
    }

    $("#closeImg").click(function () {
        $("#closeImg").css("display",'none');
        $('#fileName').text('');
        $('#extraFile').attr('data-src','');
        $('#extraFile').attr('data-name','');
    });

    $("#extraFile").change(function(){
        var self = this;
        var file = self.files[0];
        var name=file.name;
        if(!(new RegExp('\.(doc|docx|xls|xlsx|pdf)$', 'i')).test(file.name)){
            Feng.error('附件格式不对');
            $(self).val('');
            return false;
        }
        var fileData = new FormData();
        fileData.append('file', file);
        fileData.append('maxSize', 10*1024*1024);
        var imgSrcI = getObjectURL(file);
        $.ajax({
            type: "post",
            url: Feng.ctxPath + "/fastDfs/upload",
            dataType: "json",
            async: false,
            data: fileData,
            processData: false,
            contentType:false,
            success: function(data) {
                if(data.code==200){
                    $("#closeImg").css("display",'inline-block');
                    $('#fileName').text(name);
                    $('#extraFile').attr('data-src',data.data);
                    $('#extraFile').attr('data-name',name);
                }else{
                    Feng.info(data.message)
                }
            },
            error: function(data) {
                Feng.error("上传失败!" + data.responseJSON.message + "!");
            }
        });

    });

    Feng.initValidator("NewsForm", NewsInfoDlg.validateFields);

});

function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    return currentdate;
}