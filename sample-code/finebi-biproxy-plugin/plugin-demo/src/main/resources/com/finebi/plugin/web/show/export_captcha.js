!function () {
    function importFiles(files) {
        for (var i = 0; i < files.length; i ++) {
            BI.$import(BI.fineServletURL + "/file?path=/" + files[i] + "&type=plain&parser=plain");
        }
    };

    // 这里导入静态文件
    importFiles([
        // "com/finebi/plugin/web/show/export_captcha.service.js"
    ]);
    console.log("== LOAD export_captcha.js");
}();



// /**
//  * 导出短信验证码界面
//  */
// !function () {
//     //js 获取url参数
//     function getQueryString(name) {
//         var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
//         var r = window.location.search.substr(1).match(reg);
//         if (r != null) {
//             return decodeURI(r[2]);
//         }
//         return null;
//     }

//     // 判断手机号是否合法
//     function isPhoneNumber(tel) {
//         var reg =/^0?1[3|4|5|6|7|8][0-9]\d{8}$/;
//         return reg.test(tel);
//     }

//     var b = BI.inherit(BI.Widget, {
//         props: {
//             a: "",
//             b: "",
//             c: "",
//             mobile: "",
//             autoGetMobile: false
//         },
//         beforeInit: function (e) {
//             // 获取手机号
//             var b = this;
//             var o = b.options;
//             // 先判断url参数
//             var urlMobile = getQueryString("mobile");
//             if (BI.isNotEmptyString(urlMobile)) {
//                 o.mobile = urlMobile;
//                 e();
//             } else {
//                 BI.Services.getService("plugin.service.nat.export.captcha").getMobile(function (mobile) {
//                     if (BI.isNotEmptyString(mobile)) {
//                         o.mobile = mobile;
//                         o.autoGetMobile = true;
//                     }
//                     e();
//                 });
//             }
//         },
//         render: function () {
//             var b = this;
//             var o = b.options;
//             return {
//                 type: "bi.vertical",
//                 vgap: 10,
//                 hgap: 20,
//                 items: [BI.isNotEmptyString(o.mobile) ? {
//                     type: "bi.label",
//                     text: o.mobile,
//                     value: o.mobile,
//                     ref: function (_ref) {
//                         b.mobileLabel = _ref;
//                     }
//                 } : {
//                     type: "bi.text_editor",
//                     watermark: "请输入手机号",
//                     allowBlank: false,
//                     errorText: "非空!",
//                     ref: function (_ref) {
//                         b.mobileLabel = _ref;
//                     }
//                 }, {
//                     //验证码
//                     type: "bi.horizontal_adapt",
//                     columnSize: [200, "fill"],
//                     items: [{
//                         type: "bi.text_editor",
//                         watermark: "请输入验证码",
//                         ref: function(_ref) {
//                             b.captchaEditer = _ref;
//                         }
//                         //height: 30
//                     }, {
//                         type: "bi.button",
//                         text: "获取验证码",
//                         ref: function(_ref) {
//                             b.captchaButton = _ref;
//                         },
//                         handler: function () {//触发器事件回调
//                             var mobile = b.mobileLabel.getValue();
//                             //校验
//                             if(!mobile || mobile.length === 0){
//                                 BI.Msg.toast("消息提示: 验证码发送失败，未获取到手机号",{
//                                     level: "error"
//                                 });
//                                 return;
//                             }

//                             if (!o.autoGetMobile && !isPhoneNumber(mobile)) {
//                                 BI.Msg.toast("消息提示: 验证码发送失败，请输入正确手机号",{
//                                     level: "error"
//                                 });
//                                 return;
//                             }

//                             var sendDelay = b.sendDelay();//这里用了一个闭包函数
//                             sendDelay();
//                             BI.reqGet(BI.fineServletURL + "/com.finebi.plugin.biproxy/captcha/sendCaptcha?mobile=" + b.mobileLabel.getValue() + "&autoGetMobile=" + o.autoGetMobile, "", function (res) {// 这里手机获取做一下。
//                                 if(res.data && res.success === true) {
//                                     BI.Msg.toast("消息提示: " + b.mobileLabel.getValue() + " 验证码发送成功", {
//                                         level: "success"
//                                     });
//                                     b.sessionId = res.data;
//                                     b.exportButton.setEnable(true);
//                                 } else {
//                                     BI.Msg.toast("消息提示: " + b.mobileLabel.getValue() + " 验证码发送失败，请联系管理员",{
//                                         level: "error"
//                                     });
//                                 }
//                             })
//                         }
//                         //css: {background: "green"},
//                         //height: 30

//                     }]
//                 },{
//                     //按钮控件
//                     type: "bi.button",
//                     //width:300,
//                     text: "导出",
//                     //disableSelected: true,//设置点击不可选中，那么初始点击的时候，默认selected属性 = false,等到需要的时候，再切换成true
//                     disabled: true,
//                     ref: function(_ref) {
//                         b.exportButton = _ref;
//                     },
//                     handler: function () {//触发器事件回调
//                         var captcha = b.captchaEditer.getValue();
//                         if(BI.isNotEmptyString(captcha)) {
//                             BI.reqGet(BI.fineServletURL + "/com.finebi.plugin.biproxy/captcha/checkCaptcha?sessionId=" + b.sessionId + "&captcha=" + captcha, "", function (res) {
//                                 if(res.hasOwnProperty("data") && res.data === "success") {
//                                     BI.Msg.toast("消息提示: 验证码校验成功，即将导出...", {
//                                         level: "success"
//                                     });
//                                     BI.Popovers.removeAll();
//                                     o.c(1);
//                                     b.export(o.a, o.b, o.c);
//                                 } else {
//                                     BI.Msg.toast("消息提示: 验证码校验失败",{
//                                         level: "error"
//                                     });
//                                 }
//                             });
//                         } else {
//                             BI.Msg.toast("消息提示: 请输入验证码",{
//                                 level: "error"
//                             });
//                         }
//                     }
//                 }]
//             };
//         },
//         mounted: function () {

//         },
//         //闭包函数，验证码点击延时程序
//         sendDelay: function () {
//             var b= this;
//             //按钮灰化
//             b.captchaButton.setEnable(false);
//             //延时60s
//             //验证码发送延时程序
//             var countdown=60;
//             return function setTime() {
//                 if (countdown === 0) {
//                     b.captchaButton.setEnable(true);
//                     b.captchaButton.setText("获取验证码");
//                     countdown = 60;
//                     return;
//                 } else {
//                     b.captchaButton.setText("重新获取(" + countdown + ")");
//                     countdown--;
//                 }
//                 setTimeout(function() {
//                     setTime();
//                 },1000);
//             }
//         },
//         /**
//          * 实际导出方法
//          * @param a
//          * @param b
//          * @param c
//          */
//         export: function (a, b, c) {
//             BI.Utils.exportFile_Real(a, b, c)
//         }
//     });
//     BI.shortcut("plugin.nat.export.captcha", b);
// }();

// !function (factory) {
//     function getEntryId() {
//         if(BI.isNotEmptyString(BI.basePool.reportEntryId)) { // 20201105 上没有reportEntryId这个字段，只能从url中截取了
//             return BI.basePool.reportEntryId;
//         } else {
//             var a = location.href;
//             if(a.indexOf("/v10/entry/access/") !== -1) {
//                 return a.split("/v10/entry/access/")[1].split("?")[0];
//             }
//         }
//         return null;
//     }


//     var url;
//     var entryId = getEntryId();
//     if(BI.isNotEmptyString(entryId)) {
//         url = BI.fineServletURL + "/com.finebi.plugin.biproxy/report/check/checkNeedExportCaptcha?entryId=" + entryId; // 这里和自带的冲突了
//     } else {
//         if(BI.isNotEmptyString(BI.designConfigure.reportId)) {
//             url = BI.fineServletURL + "/com.finebi.plugin.biproxy/report/check/checkNeedExportCaptcha";//?reportId=" + BI.designConfigure.reportId;
//         }
//     }
//     BI.isNotEmptyString(url) && factory(url);
// }(function (url) {
//     //检测该模板是否需要导出短信验证
//     BI.reqGet(url, "", function (res) {
//         console.log(res);
//         if(res.hasOwnProperty("data") && res.data === true) {
//             BI.Utils.exportFile_Real = BI.Utils.exportFile;
//             //重写导出方法
//             BI.Utils.exportFile =  function(a, b, c) {
//                 c(0);
//                 //弹出框
//                 BI.Popovers.removeAll();
//                 var id = "弹出层id"
//                 BI.Popovers.create(id, {
//                     // String或者是json都行
//                     header: "导出短信验证",
//                     size: "small",
//                     body: {
//                         type: "plugin.nat.export.captcha",
//                         a: a,
//                         b: b,
//                         c: c
//                     },
//                     listeners: [{
//                         eventName: "EVENT_CLOSE",
//                         action: function(){
//                             //BI.Msg.toast("点击了关闭")
//                         }
//                     }]
//                 }).open(id);
//             };
//         }
//     });
// });