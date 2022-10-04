!function () {
    var b = BI.inherit(BI.Widget, {
        _store: function() {
            return BI.Models.getModel("plugin.model.nat.export.captcha.management.show.pane");
        },
        watch: {

        },
        render: function () {
            var b = this;
            return {
                type: "bi.horizontal_adapt",
                //height: 500,//
                css: {backgroundColor: "#fff"},
                vgap: 10,
                hgap: 10,
                columnSize: [300, "fill"],
                items: [{
                    el:{
                        type: "bi.vertical",
                        vgap: 10,
                        items: [{
                            type: "bi.label",
                            text: "平台仪表板目录列表"
                        }, {
                            type: "bi.button",
                            text: "批量添加至右侧列表",
                            handler: function() {
                                var values = b.allDirectoriesPane.getAllValue();
                                console.log(values);
                                var list = b.handlePara(values);
                                para = {
                                    type: "entry",
                                    addReports: BI.map(list.reportEntryIds, function (i, v) {
                                        return v.reportEntryId;
                                    }),
                                    removeReports: []
                                }
                                if(para.addReports.length > 0) {
                                    BI.reqPost(BI.fineServletURL + "/com.finebi.plugin.biproxy/report/manage/addReports", para, function (res) {
                                        console.log(res);
                                        if(res.data && res.data === "success"){
                                            BI.Msg.toast("添加目录节点成功！", {
                                                level: "success"
                                            });
                                            //刷新右侧列表
                                            b.populateDirectoriesPane();
                                        }
                                    })
                                }
                            }
                        }, {
                            type: "bi.tree_value_chooser_pane",
                            items: [],
                            //width: 200,
                            height: 300,
                            ref: function (_ref) {
                                b.allDirectoriesPane = _ref;
                            }
                        }]
                    }
                },{
                    el: {
                        type: "bi.vertical",
                        vgap: 10,
                        items: [{
                            type: "bi.label",
                            text: "导出短信验证目录列表"
                        }, {
                            type: "bi.button",
                            text: "批量移除目录节点",
                            handler: function () {
                                var value = b.DirectoriesPane.getValue();
                                var type = value.type;
                                var items = [];
                                switch (type) {
                                    case 1: {
                                        items = value.value;
                                        break;
                                    }
                                    case 2: {
                                        items = value.assist;
                                        break;
                                    }
                                }
                                if(items.length > 0 ){
                                    var para = {
                                        type: "entry",
                                        addReports: [],
                                        removeReports: BI.map(items,function (i, v) {
                                            return v;
                                        })
                                    }
                                    BI.reqPost(BI.fineServletURL + "/com.finebi.plugin.biproxy/report/manage/removeReports", para, function (res) {
                                        console.log(res);
                                        if(res.data && res.data === "success"){
                                            BI.Msg.toast("删除目录节点成功！", {
                                                level: "success"
                                            });
                                            //刷新右侧列表
                                            b.populateDirectoriesPane();
                                        }
                                    })
                                }
                            }
                        }, {
                            type: "bi.search_editor",
                            //width: 300,
                            watermark: "搜索目录节点名称",
                            errorText: "",
                            validationChecker: function (v) {
                                //return v.length > 4;
                                //做一个部门职务查询
                                var key = v;
                                var items = b.model.currSelectedItems;
                                //本地检索,遍历一下items
                                var newItems = [];
                                BI.each(items, function (i, v) {
                                    if(v.text.indexOf(key) !== -1){
                                        newItems.push(v);
                                    }
                                });

                                b.DirectoriesPane.populate(BI.createItems(newItems,{
                                    keyword: key,
                                    type: "bi.multi_select_item",
                                    logic: {
                                        dynamic: true
                                    }
                                }));
                                return true;
                            },
                            listeners: [{
                                eventName: "EVENT_CLEAR",
                                action: function() {
                                    b.populateDirectoriesPane();
                                }
                            }, {
                                eventName: "EVENT_BACKSPACE",
                                action: function() {
                                    if(BI.isEmpty(this.getValue())){
                                        b.populateDirectoriesPane();
                                    }
                                }
                            }]
                        }, {
                            type: "bi.select_list",
                            height: 270,
                            toolbar: {
                                type: "bi.multi_select_bar",
                                iconWrapperWidth: 26
                            },
                            el: {
                                el: {
                                    chooseType: BI.Selection.Multi
                                }
                            },
                            items: [],
                            ref: function (_ref) {
                                b.DirectoriesPane = _ref;
                            }
                        }]
                    }
                }]
            }
        },
        mounted: function () {
            // 在render方法里的子组件被渲染到页面之后的钩子函数
            var b = this;
            b.populateAllDirectoriesPane();
            b.populateDirectoriesPane();

        },
        //找出树控件选择项的末梢节点-递归处理下
        handlePara: function (para, newPara = []) {
            //分析: para里面还有父节点，但是父节点可能会没有子节点
            var b = this;
            for(var key of Object.keys(para)){
                if(Object.keys(para[key]).length > 0) {//判断是父节点
                    b.handlePara(para[key], newPara);
                } else {//判断是末梢子节点
                    //这里不用判断了吧，末梢节点只可能是仪表板，不可能是目录，/v10/entry/tree/201里都做了处理的
                    newPara.push({
                        reportEntryId: key
                    });
                }
            }
            return {
                reportEntryIds: newPara
            };
        },
        populateAllDirectoriesPane: function () {
            var b = this;
            BI.reqGet(BI.fineServletURL + "/v10/entry/tree/201","",function (res) {
                console.log(res);
                var items = BI.map(res.data, function (i, v) {
                    return {
                        pId: v.pId,
                        id: v.id,
                        text: BI.i18nText(v.text),
                        value: v.id,
                        open: true
                    }
                });
                b.allDirectoriesPane.populate(items);
            });
        },
        populateDirectoriesPane: function () {
            var b = this;
            BI.reqGet(BI.fineServletURL + "/com.finebi.plugin.biproxy/report/manage/getAllReportEntries","",function (res) {
                console.log(res);
                var items = BI.map(res.data, function (i, v) {
                    return {
                        text: v.fullPath,
                        value: v.id
                    }
                });
                b.store.setSelectedItem(items);
                b.DirectoriesPane.populate(BI.createItems(items,{
                    type: "bi.multi_select_item",
                    logic: {
                        dynamic: true
                    }
                }));
            });
        }
    });
    BI.shortcut("plugin.nat.export.captcha.management.entry.pane", b);
}();