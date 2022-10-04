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
                            text: "仪表板列表"
                        }, {
                            type: "bi.button",
                            text: "批量添加至右侧列表",
                            handler: function() {
                                var values = b.allDirectoriesPane.getAllValue();
                                console.log(values);
                                var list = b.handlePara(values);
                                para = {
                                    type: "report",
                                    addReports: BI.map(list.reportIds, function (i, v) {
                                        return v.reportId;
                                    }),
                                    removeReports: []
                                }
                                console.log(para);
                                if(para.addReports.length > 0) {
                                    BI.reqPost(BI.fineServletURL + "/com.finebi.plugin.biproxy/report/manage/addReports", para, function (res) {
                                        console.log(res);
                                        if(res.data && res.data === "success"){
                                            BI.Msg.toast("添加仪表板成功！", {
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
                            height: 300,
                            ref: function (_ref) {
                                b.allDirectoriesPane = _ref;
                            }
                        }]
                    }
                }, {
                    el: {
                        type: "bi.vertical",
                        vgap: 10,
                        items: [{
                            type: "bi.label",
                            text: "导出短信验证仪表板列表"
                        }, {
                            type: "bi.button",
                            text: "批量移除仪表板",
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
                                            BI.Msg.toast("删除仪表板成功！", {
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
                            watermark: "搜索仪表板名称",
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
                } else {//判断是末梢子节点,这里怎么判断是文件夹节点呢，需要把文件夹都过滤掉，或者一开始就要过滤掉
                    if(b.itemMap[key] && !b.itemMap[key].isParent) {
                        newPara.push({
                            reportId: key
                        });
                    }
                }
            }
            return {
                reportIds: newPara
            };
        },
        populateAllDirectoriesPane: function () {
            var b = this;
            BI.reqGet(BI.fineServletURL + "/v10/dashboard/tree","",function (res) {
                console.log(res);
                var items = [];
                //TODO 这里需要做一下处理
                var my = {
                    id: "bi-templates-root",
                    isParent: true,
                    open: true,
                    pId: "",
                    text: "我的模板",
                    value: "bi-templates-root",
                };
                items.push(my);
                items.push({
                    id: "bi-user-templates-root",
                    pId: "",
                    isParent: true,
                    open: true,
                    text: "所有用户模板",
                    value: "bi-user-templates-root"
                })
                for(var key of Object.keys(res.data)) {
                    if(key === "currentReport") {
                        items = items.concat(BI.map(res.data[key], function (i, v) {
                            return {
                                pId: v.createBy === v.pId ? "bi-templates-root" : v.pId,
                                isParent: v.folder,
                                id: v.id,
                                text: BI.i18nText(v.name),
                                value: v.id,
                                open: true
                            }
                        }));
                    } else {
                        items.push({
                            id: key,
                            isParent: true,
                            pId: "bi-user-templates-root",
                            text: key,
                            value: key,
                            open: true
                        });
                        items = items.concat(BI.map(res.data[key], function (i, v) {
                            return {
                                pId: key,
                                isParent: v.folder,
                                id: v.id,
                                text: BI.i18nText(v.name),
                                value: v.id,
                                open: true
                            }
                        }));
                    }
                }

                console.log(items);// TODO 这里是不是可以弄个字典？

                b.itemMap = {};
                BI.each(items, function (i, v) {
                    b.itemMap[v.id] = v;
                });


                /*
                var items = BI.map(res.data, function (i, v) {
                    return {
                        pId: v.pId,
                        id: v.id,
                        text: BI.i18nText(v.text),
                        value: v.id,
                        open: true
                    }
                });

                 */
                /*
                b.allDirectoriesPane.populate(BI.filter(items, function (i, v) {
                    return b.itemMap[v.id] && !b.itemMap[v.id].isParent;
                }));

                 */
                b.allDirectoriesPane.populate(items);
            });
        },
        populateDirectoriesPane: function () {
            var b = this;
            BI.reqGet(BI.fineServletURL + "/com.finebi.plugin.biproxy/report/manage/getAllReports","",function (res) {
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
    BI.shortcut("plugin.nat.export.captcha.management.show.pane", b);
}();