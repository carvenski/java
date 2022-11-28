# flink cdc 踩坑记录
```
FUCK
之前一直报错用户mysql表中的time字段和flink sql time关键字冲突了
搞了三天时间查询了所有google都没答案
加了三天班没办法用不了sql了 只能用java datastream api重写sql任务
今天早上不知怎么突然想起来在字段前面加个 `time` 括起来 试了一下
就好了。。。坑死了

注: 
`time` INT
sql中使用 `` 来区分保留关键字和用户字段(don't know that)
```

```
yuwei给onedb的新的mysql master刚刚配置了binlog
这边启动flink cdc去同步mysql却一直报错: mysql server id is 0, mysql master misconfig.
各种搜索测试 浪费2天时间debug
最后并不是flink cdc里面的slave id重复
就是mysql master id不能设置为0，改成123就好了。
都是坑。浪费人生。
```


