# 注意事项
小demo代码逻辑很简单,纯servlet的curd操作。
- 该项目没有使用maven工程,jar包在WEB-INF/lib下
- 导入到本地工程的话需要配置参考之前写的导入web项目笔记,因为servlet需要依赖tomcat的lib包,需要配置下,要不然会找不到一些类。
- mysql和druid的jar包注意下版本,要不然操作数据库会出错。
- 核心内容是分页结果集查询BeanPage,查询默认不传条件或者传条件分页。
- filter的过滤放行,以及敏感词过滤
- 代码流程图参考：https://www.yuque.com/lizhesystem/java/project#ThB2E