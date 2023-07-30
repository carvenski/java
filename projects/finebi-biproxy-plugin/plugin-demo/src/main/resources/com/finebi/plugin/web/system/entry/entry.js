!function () {
    var App = BI.inherit(BI.Widget, {
        
        props: {
            attributes: {
                id: "biproxy",
                name: "biproxy"
            }
        },

        mounted: function () {

// ================================================
//渲染html指定id=app
console.log("== 加载 div id=app "+new Date());
document.getElementById("biproxy").innerHTML = ""+
`
<div id="biproxy-app">    
 <el-table
    :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()) || data.id.toLowerCase().includes(search.toLowerCase()) || data.url.toLowerCase().includes(search.toLowerCase()) )"
    style="width: 100%; height: 100%">
    <el-table-column
      label="报表名称"
      prop="name">
    </el-table-column>
    <el-table-column
      label="外部地址"
      prop="id">
    <template slot-scope="scope">
        <el-link target="_blank" :href="scope.row.id">{{scope.row.id}}</el-link>
    </template>
    </el-table-column>
    <el-table-column
      label="内部地址"
      prop="url">
    <template slot-scope="scope">
        <el-link target="_blank" :href="scope.row.url">{{scope.row.url}}</el-link>
    </template>
    </el-table-column>
    <el-table-column
      align="right">
      <template slot="header" slot-scope="scope">
      <el-container>
          <el-input
                v-model="search"
                size="mini"
                placeholder="搜索"/></el-input>
          <el-divider direction="vertical"></el-divider>
          <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleEdit(-1,{id:guid()})">添加</el-button>
      </el-container>        
      </template>
      <template slot-scope="scope">
        <el-button
          size="mini"
          @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

<el-dialog title="编辑规则" :visible.sync="dialogFormVisible" :modal="false">
  <el-form :model="form_data">
    <el-form-item label="报表名称" :label-width="formLabelWidth">
      <el-input v-model="form_data.name" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="外部地址" :label-width="formLabelWidth">
      <el-input v-model="form_data.id" autocomplete="off" :disabled="true"></el-input>
    </el-form-item>
    <el-form-item label="内部地址" :label-width="formLabelWidth">
      <el-input v-model="form_data.url" autocomplete="off"></el-input>
    </el-form-item>    
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取消</el-button>
    <el-button type="primary" @click="handleSave()">保存</el-button>
  </div>
</el-dialog>
</div>
`;
            
console.log("=== 加载 js 业务代码"+new Date());


    var xxyyzz = new Vue({
       el: '#biproxy-app',
       data() {
            return {
              tableData: [],
              search: '',              
              dialogFormVisible: false,
              formLabelWidth: '120px',
              form_data: {},
              is_add: false,
            }
          },
      mounted: function () {
        console.log("== getAll data ==")
        // getAll db
        var url = BI.fineServletURL + "/publish/getAll";
        // here must use =>, not function, cause scope cant find global this
        BI.reqGet(url, "", (res) => {
            console.log("/getAll");
            console.log(res);
            // show url from id
            for(var i=0;i<res.data.length;i++){
              res.data[i]['id'] = window.location.origin + BI.fineServletURL + "/url/publish?id=" + res.data[i]['id']
            }            
            this.tableData = res.data
            console.log(this.tableData)
        });
      },
      methods: {
        handleEdit(index, row) {
          console.log("=== tableData")
          console.log(this.tableData)
          console.log(index, row)
          this.form_data = row
          this.dialogFormVisible = true
          if (index == -1) {this.is_add = true}
        },
        handleSave(){              
          console.log("== save")
          console.log(this.form_data)
          // update db
          var url = BI.fineServletURL + "/publish/addOrUpdate";
          BI.reqPost(url, this.form_data, function (res) {
              console.log("/addOrUpdate");
              console.log(res);
          });
          // save in tableData
          if (this.is_add) {this.tableData.unshift(this.form_data);this.is_add=false;}
          // close form
          this.dialogFormVisible = false
          this.$notify({title: '已保存',type: 'success',duration:2000});
        },            
        handleDelete(index, row) {
          console.log(index, row)
          // remove in db
          var url = BI.fineServletURL + "/publish/remove";
          BI.reqPost(url, row, function (res) {
              console.log("/remove");
              console.log(res);
          });
          this.$notify({title: '已删除',type: 'success',duration:2000});
          // remove in tableData
          this.tableData.splice(index, 1)
        },
        guid() {
            // generate uuid
            return window.location.origin + BI.fineServletURL + "/url/publish?id=" + 
              // 'xxxxxxxxxxxxxxxx-yxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c){
                'yxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c){
                var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
                return v.toString(16);
            });
        },
      },
    })


    }});
    BI.shortcut("biproxy-entry-js", App);
}();