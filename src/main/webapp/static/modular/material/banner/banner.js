/**
 * 轮播图管理管理初始化
 */
var Banner = {
    id: "BannerTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};
var path=[];

/**
 * 编辑
 */
Banner.Edit = function () {
    if(path.length>4){
        $('.add_div').hide();
    }else{
        $('.add_div').show();
    }
    $('.editBtn_div').show();
    $('.tips').show();
    $(' #ImgList li button').show();
    $('.btn_div').hide();
    $('#ImgList').sortable({
        beforeStop:function (event, ui) {
            draftingSort();
        }
    });
};

/**
 * 保存
 */
Banner.Save = function () {
    var ajax = new $ax(Feng.ctxPath + "/banner/save", function (data) {
        if(data.code==200){
            Feng.success("保存成功!");
            window.location=Feng.ctxPath + "/banner";
        }else{
            Feng.error("保存失败!" + data.message + "!");
        }
    }, function (data) {
        Feng.error("保存失败!" + data.responseJSON.message + "!");
    });
    var p=path.join(',');
    ajax.set('bannerParam', p);
    ajax.start();

};

/**
 * 取消
 */
Banner.Cancel = function () {
    window.location=Feng.ctxPath + "/banner";
    // $( "#ImgList" ).sortable( "disable" );
};

/**
 * 删除轮播图管理
 */
Banner.delete = function (obj) {
    var index=$(obj).parent('li').index();
    $(obj).parent('li').remove();
    path.splice(index,1);
    if(path.length>4){
        $('.add_div').hide();
    }else{
        $('.add_div').show();
    }
};
function draftingSort() {
    path = $("#ImgList").sortable("toArray", {attribute: "data-path"});
}

$(function () {
    var ajax = new $ax(Feng.ctxPath + "/banner/list", function (data) {
        if(data.code==200){
            for(var i=0;i<data.data.length;i++){
                var li=$('<li data-path="'+data.data[i].path+'">' +
                    '<img src="'+data.data[i].path+'" alt="图片缺失"/>' +
                    '<button onclick="Banner.delete(this)"><i class="fa fa-close"></i></button>'+
                    '</li>');
                $('#ImgList').append(li);
                path.push(data.data[i].path);
            }
            $(' #ImgList li button').hide();
        }else{
            Feng.error("获取列表失败!" + data.message + "!");
        }
    }, function (data) {
        Feng.error("获取列表失败!" + data.responseJSON.message + "!");
    });
    ajax.start();

    $("#images").change(function(){
        var self = this;
        var file = self.files[0];
        if(!(new RegExp('\.(jpeg|jpg|png)$', 'i')).test(file.name)){
            Feng.error('图片格式不对');
            $(self).val('');
            return false;
        }
        var reader=new FileReader();
        var imgWidth;
        var imgHeight;
        reader.onload=function(){
            // 也可以用 window.URL.createObjectURL(this.result)
            var oImg=new Image();
            oImg.src=this.result;
            document.body.appendChild(oImg);

            oImg.onload=function(){
                imgWidth=oImg.offsetWidth;
                imgHeight=oImg.offsetHeight;
                document.body.removeChild(oImg);
                if(imgWidth==1200&&imgHeight==200){
                    var fileData = new FormData();
                    fileData.append('file', file);
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
                                var li=$('<li data-path="'+data.data+'">' +
                                    '<img src="'+data.data+'" alt=""/>' +
                                    '<button onclick="Banner.delete(this)"><i class="fa fa-close"></i></button>'+
                                    '</li>');
                                $('#ImgList').append(li);
                                path.push(data.data);
                                $(' #ImgList li button').show();
                                if(path.length>4){
                                    $('.add_div').hide();
                                }else{
                                    $('.add_div').show();
                                }
                            }else{
                                Feng.info(data.message);
                            }
                        },
                        error: function(data) {
                            Feng.error("上传失败!" + data.responseJSON.message + "!");
                        }
                    });
                }else{
                    Feng.info("只能上传尺寸为1200*200的图片！");
                }
            };
        };
        reader.readAsDataURL(file);
    });
});
