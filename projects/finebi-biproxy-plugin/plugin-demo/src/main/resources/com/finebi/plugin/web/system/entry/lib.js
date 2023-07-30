// 动态导入css和js文件
function FFcss(url) {
        var head = document.getElementsByTagName('head')[0];
        var link = document.createElement('link');
        link.type='text/css';
        link.rel = 'stylesheet';
        url = window.location.origin + Dec.fineServletURL + "/file?path=/" + url + "&type=plain&parser=plain";
        link.href = url;
        head.appendChild(link);
        console.log(">>> Loading css: "+url);
};
function FFjs(url) {
        var head = document.getElementsByTagName('head')[0];
        var script = document.createElement('script');
        script.type = 'text/javascript';
        url = window.location.origin + Dec.fineServletURL + "/file?path=/" + url + "&type=plain&parser=plain";
        script.src = url;        
        head.appendChild(script);
        console.log(">>> Loading js: "+url);
};

// 发送http请求
function FFhttp(para){

    // get请求
    var url = BI.fineServletURL + "/publish/getAll";
    BI.reqGet(url, "", function (res) {
        console.log("/getAll");
        console.log(res);

        // post请求
        var url = BI.fineServletURL + "/publish/addOrUpdate";
        BI.reqPost(url, para, function (res) {
            console.log("/addOrUpdate");
            console.log(res);

            // remove
            var url = BI.fineServletURL + "/publish/remove";
            BI.reqPost(url, para, function (res) {
                console.log("/remove");
                console.log(res);
            });

        });

    });
    
}