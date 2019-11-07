<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>首页</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript">
    </script>
</head>
<body>

<%--<c:if test="${!empty adminUser}">--%>


<div class="container">
    <div>
        <a href="${pageContext.request.contextPath}/loginOutServlet">
            <button style="float: right;margin: 10px 8px;" type="button" class="btn btn-success">退出登录</button>
        </a>
    </div>
    <h3 style="text-align: center">用户信息列表</h3>


    <!--表格上查询按钮-->
    <div style="float: left">
        <form class="form-inline" action="${pageContext.request.contextPath}/findUserByPageServlet" method="post">
            <div class="form-group">
                <label for="exampleInputName2">姓名</label>
                <input type="text" name="name" value="${inputPara['name'][0]}" class="form-control"
                       id="exampleInputName2">
            </div>
            <%--这里EL获取Map总结下--%>

            <div class="form-group">
                <label for="jiguan">籍贯</label>
                <input type="text" name="address" value="${inputPara.address[0]}" class="form-control" id="jiguan">
            </div>

            <div class="form-group">
                <label for="exampleInputEmail2">邮箱</label>
                <input type="text" name="email" value="${inputPara.email[0]}" class="form-control"
                       id="exampleInputEmail2">
            </div>


            <button type="submit" class="btn btn-default">查询</button>
        </form>
    </div>


    <div style="float: right;margin: 5px;">

        <a class="btn btn-primary" href="${pageContext.request.contextPath}/add.jsp">添加联系人</a>
        <a class="btn btn-primary" href="javascript:void(0);" id="delSelectUser">删除选中</a>

    </div>

    <form id="form" action="${pageContext.request.contextPath}/delSelectUserServlet" method="post">
        <table border="1" class="table table-hover table-bordered">
            <tr class="success">
                <th><input type="checkbox" id="firstCb"></th>
                <th>编号</th>
                <th>姓名</th>
                <th>性别</th>
                <th>年龄</th>
                <th>籍贯</th>
                <th>QQ</th>
                <th>email</th>
                <th>操作</th>
            </tr>

            <c:forEach items="${bp.list}" var="user" varStatus="s">
                <tr>
                    <td><input type="checkbox" name="uid" value="${user.id}"></td>
                    <td>${s.count}</td>
                    <td>${user.name}</td>
                    <td>${user.gendet}</td>
                    <td>${user.age}</td>
                    <td>${user.address}</td>
                    <td>${user.qq}</td>
                    <td>${user.email}</td>
                    <td><a class="btn btn-default btn-sm"
                           href="${pageContext.request.contextPath}/findUserServlet?id=${user.id}">修改</a>&nbsp;
                        <a class="btn btn-default btn-sm" href="javascript:void(0);" onclick="deleteUser(${user.id});">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>
    <div>
        <nav aria-label="Page navigation">
            <ul class="pagination">


                <c:if test="${bp.currentPage == 1}">
                <li class="disabled">
                    </c:if>

                    <c:if test="${bp.currentPage != 1}">
                <li>
                    </c:if>

                    <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${bp.currentPage -1}&rows=5&name=${inputPara['name'][0]}&address=${inputPara['address'][0]}&email=${inputPara['email'][0]}"
                       aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>

                <c:forEach begin="1" end="${bp.totalPage}" var="i">
                    <c:if test="${bp.currentPage == i}">
                        <li class="active">
                            <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${i}&rows=5&name=${inputPara['name'][0]}&address=${inputPara['address'][0]}&email=${inputPara['email'][0]}">${i}</a>
                        </li>
                    </c:if>
                    <c:if test="${bp.currentPage != i}">
                        <li class="">
                            <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${i}&rows=5&name=${inputPara['name'][0]}&address=${inputPara['address'][0]}&email=${inputPara['email'][0]}">${i}</a>
                        </li>
                    </c:if>
                </c:forEach>


                <c:if test="${bp.currentPage == bp.totalPage}">
                    <li class="disabled">
                        <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${bp.totalPage}&rows=5&name=${inputPara['name'][0]}&address=${inputPara['address'][0]}&email=${inputPara['email'][0]}"
                           aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>

                <c:if test="${bp.currentPage != bp.totalPage}">
                    <li class="">
                        <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${bp.currentPage + 1}&rows=5&name=${inputPara['name'][0]}&address=${inputPara['address'][0]}&email=${inputPara['email'][0]}"
                           aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>


                &nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 22px">总共${bp.totalCount}条数据，共${bp.totalPage}页</span>
            </ul>
        </nav>
    </div>


</div>
<%--</c:if>--%>
<script>

    function deleteUser(id) {
        if (confirm("确定要删除吗?")) {
            location.href = "${pageContext.request.contextPath}/deleteUserServlet?id=" + id;
        }
    }

    $(function () {
        $("#delSelectUser").click(function () {
            if (confirm("确定要删除选中吗?")) {
                var flag = false;

                var uid = $("input[name='uid']");
                for (var i = 0; i < uid.length; i++) {
                    if (uid[i].checked) {
                        flag = true;
                        break;
                    }
                }

                if (flag) {
                    $("#form").submit();
                }
            }

        });

        $("#firstCb").click(function () {

            var uid = $("input[name='uid']");
            for (var i = 0; i < uid.length; i++) {
                uid[i].checked = this.checked;
            }

        })

    })

</script>
</body>
</html>