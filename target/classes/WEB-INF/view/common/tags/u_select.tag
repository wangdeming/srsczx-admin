@/*
    select标签中各个参数的说明:
    name : select的名称
    id : select的id
    underline : 是否带分割线
@*/

@var statusOptions = "<option value=''>全部</option><option value='1'>正常</option><option value='0'>停用</option>";
@if(id == "status"){
@   options = statusOptions;
@}

<div class="form-inline">
    <div class="col-sm-12" style="padding:0;">
        <label class="control-label">${name}</label>
        <select class="form-control" id="${id}" name="${id}" style="width:60%;">
            ${options!}
        </select>
        @if(isNotEmpty(hidden)){
            <input class="form-control" type="hidden" id="${hidden}" value="${hiddenValue!}">
        @}
    </div>
</div>
@if(isNotEmpty(underline) && underline == 'true'){
    <div class="hr-line-dashed"></div>
@}


