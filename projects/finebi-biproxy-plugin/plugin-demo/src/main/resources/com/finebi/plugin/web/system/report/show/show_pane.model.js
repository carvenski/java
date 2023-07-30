!function () {
    var a = BI.inherit(Fix.Model, {
        state: function() {
            return {
                selectedReport: "",//当前选中仪表板id
                selectedItems: [],//目前只有这个用到了
            }
        },
        computed: {
            currSelectedItems: function () {
                return this.model.selectedItems;
            },
            currSelectedReport: function () {
                return this.model.selectedReport;
            }
        },
        actions: {
            setSelectedItem: function (items) {
                this.model.selectedItems = items;
            },
            setSelectedReport: function (id) {
                this.model.selectedReport = id;
            }
        }
    });
    BI.model("plugin.model.nat.export.captcha.management.show.pane", a);
}();