var a = BI.inherit(Fix.Model, {
    state: function() {
        return {
            selectedItems: [],//目前只有这个用到了
        }
    },
    computed: {
        currSelectedItems: function () {
            return this.model.selectedItems;
        }
    },
    actions: {
        setSelectedItem: function (items) {
            this.model.selectedItems = items;
        }
    }
});
BI.model("plugin.model.nat.export.captcha.management.entry.pane", a);