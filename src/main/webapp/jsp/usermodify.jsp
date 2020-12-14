<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/jsp/common/head.jsp" %>
<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>用户管理页面 >> 用户修改页面</span>
    </div>
    <div class="providerAdd">
        <form id="userForm" name="userForm" method="post"
              action="${pageContext.request.contextPath }/user/update" enctype="multipart/form-data">
            <input type="hidden" name="method" value="modifyexe">
            <input type="hidden" name="id" value="${user.id }"/>
            <div>
                <label for="userName">用户名称：</label>
                <input type="text" name="userName" id="userName" value="${user.userName }">
                <font color="red"></font>
            </div>
            <div>
                <label>用户性别：</label>
                <select name="gender" id="gender">
                    <c:choose>
                        <c:when test="${user.gender == 1 }">
                            <option value="1" selected="selected">男</option>
                            <option value="2">女</option>
                        </c:when>
                        <c:otherwise>
                            <option value="1">男</option>
                            <option value="2" selected="selected">女</option>
                        </c:otherwise>
                    </c:choose>
                </select>
            </div>
            <div>
                <label for="birthday">出生日期：</label>
                <input type="text" Class="Wdate" id="birthday" name="birthday"
                       value="<fmt:formatDate value='${user.birthday}' pattern='yyyy-MM-dd' />"
                       readonly="readonly" onclick="WdatePicker();">
                <font color="red"></font>
            </div>

            <div>
                <label for="phone">用户电话：</label>
                <input type="text" name="phone" id="phone" value="${user.phone }">
                <font color="red"></font>
            </div>
            <div>
                <label for="address">用户地址：</label>
                <input type="text" name="address" id="address" value="${user.address }">
            </div>
            <div>
                <label>用户角色：</label>
                <!-- 列出所有的角色分类 -->
                <input type="hidden" value="${user.userRole}" id="rid"/>
                <select name="userRole" id="userRole"></select>
                <font color="red"></font>
            </div>

            <div>
                <label for="userPic">用户头像：</label>
                <td style="width: 10%">
                    <c:if test="${user.userPic !=null}">
                        <img src="http://192.168.4.3:8088/upload/${user.userPic}" width=100 height=100 alt="">
                    </c:if>
                </td>
                <input type="file" name="pictureFile" id="userPic" value="">
            </div>

            <div class="providerAddBtn">
                <input type="button" name="save" id="save" value="保存"/>
                <input type="button" id="back" name="back" value="返回"/>
            </div>
        </form>
    </div>
</div>
</section>
<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/usermodify.js"></script>
