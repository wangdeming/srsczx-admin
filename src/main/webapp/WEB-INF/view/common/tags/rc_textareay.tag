@/*
    select标签中各个参数的说明:

    name : select 的名称
    id : select 的 id
    options: 选框数据
@*/

<div class="form-group">
@if(isEmpty(direction)){
    <label class="col-sm-3 input-left control-label">${name}</label>
    <div class="col-sm-9 input-right">
@}else{
    <label class="col-sm-3 control-label">${name}</label>
    <div class="col-sm-9 vertical-bottom" style="position: relative">
@}
        @if(isEmpty(maxlength)){
        <textarea class="form-control" maxlength="300" id="${id}" name="${id}" placeholder="${placeholder}" style="height:110px;resize:none;">${value!}</textarea>
        @}else{
        <textarea class="form-control" maxlength="${maxlength}" id="${id}" name="${id}" placeholder="${placeholder}" style="height:136px;resize:none;">${value!}</textarea>
        @}
        <div style="position: absolute;right: 20px;bottom: 5px;z-index: 1">
        </div>
    </div>
</div>
</div>
    <script>
    </script>