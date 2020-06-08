/**
 * Created by macroot on 2020/5/28.
 */
// 时间戳转化为时间
var d=$('#radio').attr('data-val');
var date = new Date(parseInt(d));
Y = date.getFullYear() + '-';
M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';

D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';
// D = date.getDate() + ' ';
h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':';
// h = date.getHours() + ':';
m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
// m = date.getMinutes() + ':';
s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds()) + '';
// s = date.getSeconds();
var time=Y+M+D+h+m+s;
$('#radio+label').text(time);
var index=$('#newsType ').attr('data-value');
$('#newsType option:eq('+(index-1)+')').attr('selected','selected');
//封面图片
var imgSrcI=$('#coverImage').attr('data-src');
$('#coverImage').attr('data-oldSrc',imgSrcI);
$(".div-input").hide();
var divImg=$(' <div class="div-img form-control">' +
    ' <img src="'+imgSrcI+'" alt="图片缺失">' +
    ' <button onclick="delImgEdit(this)"><i class="fa fa-close"></i></button>' +
    '</div>')
$('.upImg-div').append(divImg);
editor.txt.html($('#mainFile').attr('data-val'));

function delImgEdit(obj){
    $(obj).parent('.div-img').remove();
    $(".div-input").show();
    $('#coverImage').val('');
    $('#coverImage').attr('data-src','');
    var tip=$('<small class="help-block" data-bv-validator="notEmpty" data-bv-for="title" data-bv-result="INVALID" style="">' +
        '封面图片不能为空' +
        '</small>')
    $('#coverImage').parent('.div-input').after(tip);
    $('#coverImage').parents('.form-group').addClass('has-error').removeClass('has-success');
}