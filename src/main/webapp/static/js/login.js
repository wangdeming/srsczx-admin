webpackJsonp([0], [, , ,
    function(n, e, o) {
        n.exports = o(4)
    },
    function(n, e, o) {
        "use strict";
        Object.defineProperty(e, "__esModule", {
            value: !0
        });
        var t = o(5),
            a = (o.n(t), o(6)),
            c = (o.n(a), o(0)),
            i = o.n(c),
            l = o(1),
            s = o.n(l),
            r = o(2),
            u = o.n(r);
        // console.log("login.js"),
        i()(function() {
            i()("#x_code").html(u.a.createCode(4)),
                i()("#x_login").on("click",
                    function(n) {
                        if (u.a.isInputNotNull()) {
                            if (i()("#x_verify").val().toUpperCase() !== i()("#x_code").html()) return void alert("验证码不正确，请重新输入！");
                            new u.a.ajax(s.a.getUrl("login"),
                                function(n) {
                                    console.log("login:", n),
                                        0 == n.code ? window.location.href = "spotlist.html?n=" + encodeURI(n.data.nickName) + "&t=" + encodeURI(n.data.userType) : alert(n.errMsg)
                                }).setType("POST").setData({
                                name: i()("#x_user").val(),
                                password: i()("#x_passwd").val()
                            }).start(),
                                i()("#x_code").html(u.a.createCode(4))
                        }
                    }),
                i()("#x_code").on("click",
                    function(n) {
                        i()(this).html(u.a.createCode(4))
                    }),
                i()(":input").on("input",
                    function(n) {
                        i()("#x_login").css("background", u.a.isInputNotNull() ? "#6FD7A8": "rgba(111, 215, 168, 0.4)")
                    })
        })
    },
    function(n, e) {},
    function(n, e) {}], [3]);
$(function () {
    $("#kaptcha").on('click', function () {
        $("#kaptcha").attr('src', '${ctxPath}/kaptcha?' + Math.floor(Math.random() * 100)).fadeIn();
    });
    // 登陆
    $('#password').keydown(function(event) {
        if (event.keyCode == 13) {
            Login();
        }
    })
    $("#accountLogin").on('click', function () {
        Login();
    });
    function Login(){
        var username=$("#username").val();
        var password=$("#password").val();
        var remember =$("input[type='checkbox']").prop('checked');
        if(remember==false){
            var remember = '';
        }else{
            var remember = 'on';
        }
        $.ajax({
            type: "POST",
            url: Feng.ctxPath + "/login",
            data:{"remember":remember,"username":username,"password":password},
            success: function(data) {
                console.log(data);
                if(data.code == 200){
                    var url = data.data;
                    window.location.href = url;
                }else if(data.code == 431){
                    $("#formBox-security").removeClass("hide");
                    $("#formBox-account").addClass("hide");
                    $(".firstloginxp").removeClass("hide");
                    $(".firstlogin").addClass("hide");

                    $(".Namend").on('click', function () {
                        var url = data.data;
                        window.location.href = url;
                    });
                    changeDefaultPwd();


                }else{
                    Feng.error(data.message);
                }

            },
            error:function (data) {
                Feng.error(data.message);
            },
            //调用执行后调用的函数
            complete: function (XMLHttpRequest, textStatus) {
            },
        });
    }

    function changeDefaultPwd(){
        $("#verifCodeLogin").on('click', function () {
            var sysType = $("#sysType").val();
            var rePwd=$("#rePwd").val();
            var newPwd=$("#newPwd").val();
            $.ajax({
                type: "POST",
                url: Feng.ctxPath + "/mgr/changeDefaultPwd",
                data:{"newPwd":newPwd,"rePwd":rePwd,"sysType":sysType},
                success: function(data) {
                    console.log(data);
                    if(data.code == 200){
                        var url = data.data;
                        window.location.href = url;
                    }else{
                        Feng.error(data.message);
                    }

                },
                error:function (data) {
                    // console.log(data);
                    Feng.error(data.message);
                },
                //调用执行后调用的函数
                complete: function (XMLHttpRequest, textStatus) {
                },
            });
        });
    }

});
