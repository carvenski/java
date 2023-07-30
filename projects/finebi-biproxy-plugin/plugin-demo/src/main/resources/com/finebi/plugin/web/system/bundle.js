!function () {
    function importFiles(files) {
        for (var i = 0; i < files.length; i ++) {
            BI.$import(Dec.fineServletURL + "/file?path=/" + files[i] + "&type=plain&parser=plain");
        }
    }

    // 导入静态文件
    importFiles([
        // element-ui.css文件需要拷贝到bundle.css里面才能导入
        "com/finebi/plugin/web/system/entry/vue.js",
        "com/finebi/plugin/web/system/entry/element-ui.js",           
        "com/finebi/plugin/web/system/entry/lib.js",
        "com/finebi/plugin/web/system/entry/entry.js",
    ]);
    
}(), function () {
    //  示例,向管理系统节点加入帆软帮助文档
    //  特别注意,此配置需要配合服务端SystemOptionProvider接口使用,不然会因无权限而不显示节点.
    BI.config("dec.provider.management", function (provider) {
        provider.inject({
            modules: [
                {
                    value: "biproxy", // 地址栏显示的hash值
                    id: "plugin-decision-management-nat-export-captcha-ZZZ", // id
                    text: BI.i18nText("报表转发"), // 文字
                    cardType: "biproxy-entry-js", // 组件的shortcut,适用于用fineui开发的页面.
                    cls: "setting-font" // 图标类名
                }
            ]
        });        
    });    
}();