<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/jsp/common/head.jsp" %>
<style>
    .button {
        box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
    }

    .button:hover {
        box-shadow: 0 12px 16px 0 rgba(0, 0, 0, 0.24), 0 17px 50px 0 rgba(0, 0, 0, 0.19);
    }

    .button {
        padding: 5px 10px;
        background: #00b0f0;
        color: #FFF;
        border: none;
        border-radius: 5px;
        margin: 10px 10px 20px;
        width: 100px;
    }

    input[type=file] {
        overflow: hidden;
    }
</style>
<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>订单管理页面</span>
    </div>

    <div class="search">
        <form method="post" action="${pageContext.request.contextPath }/bill/search">
            <input type="hidden" name="pageIndex" value="${1}"/>
            <input type="hidden" name="totalPageCount" id="totalPageCount" value="${pageInfo.pages}"/>
            <span>商品名称：</span>
            <input name="queryProductName" id="queryProductName" type="text" value="${queryProductName}">

            <span>供应商：</span>
            <select id="queryProviderId" name="queryProviderId">
                <c:if test="${providerList != null }">
                    <option value="0">--请选择--</option>
                    <c:forEach var="provider" items="${providerList}">
                        <option
                                <c:if test="${provider.id == provide.id }">selected="selected"</c:if>
                                value="${provider.id}">${provider.proName}</option>
                    </c:forEach>
                </c:if>
            </select>

            <span>是否付款：</span>
            <select name="queryIsPayment">
                <option value="0">--请选择--</option>
                <option value="1" ${queryIsPayment == 1 ? "selected=\"selected\"":"" }>未付款</option>
                <option value="2" ${queryIsPayment == 2 ? "selected=\"selected\"":"" }>已付款</option>
            </select>

            <input value="查 询" type="submit" id="searchbutton">
            <a href="${pageContext.request.contextPath }/jsp/billadd.jsp">添加订单</a>
        </form>
    </div>


    <div style="width: 100%">
        <form id="downloadForm">
            <input type="hidden" id="downloadPageIndex" name="downloadPageIndex" value="${pageInfo.pageNum}"/>
            <input type="hidden" id="downloadTotalPageCount" name="downloadTotalPageCount" value="${pageInfo.pages}"/>
            <input type="hidden" id="downloadQueryProductName" name="downloadQueryProductName"
                   value="${queryProductName}"/>
            <input type="hidden" id="downloadQueryProviderId" name="downloadQueryProviderId" value="${provide.id}"/>
            <input type="hidden" id="downloadQueryIsPayment" name="downloadQueryIsPayment" value="${queryIsPayment}"/>
            <input type="button"
                   style="float: left;background: #47acf1 url(../images/download.png) 10px center no-repeat"
                   class="button" id="downloadFile" value="导出">
        </form>
    </div>


    <div style="width: 100%">
        <form id="uploadForm" action="${pageContext.request.contextPath}/bill/upload" enctype="multipart/form-data"
              method="post">
            <input type="file" class="button" id="uploadFile" style="width: 200px" name="uploadFile">
            <input type="button" class="button"
                   style="background: #47acf1 url(../images/upload.png) 10px center no-repeat" id="uploadBtn"
                   value="上传">
        </form>
    </div>
    <!--账单表格 样式和供应商公用-->
    <table class="providerTable" cellpadding="0" cellspacing="0">
        <tr class="firstTr">
            <th width="10%">订单编码</th>
            <th width="20%">商品名称</th>
            <th width="10%">供应商</th>
            <th width="10%">订单金额</th>
            <th width="10%">是否付款</th>
            <th width="10%">创建时间</th>
            <th width="30%">操作</th>
        </tr>
        <c:forEach var="bill" items="${pageInfo.list}" varStatus="status">
            <tr>
                <td>
                    <span>${bill.billCode }</span>
                </td>
                <td>
                    <span>${bill.productName }</span>
                </td>
                <td>
                    <span>${bill.providerName}</span>
                </td>
                <td>
                    <span>${bill.totalPrice}</span>
                </td>
                <td>
					<span>
						<c:if test="${bill.isPayment == 1}">未付款</c:if>
						<c:if test="${bill.isPayment == 2}">已付款</c:if>
					</span>
                </td>
                <td>
					<span>
					<fmt:formatDate value="${bill.creationDate}" pattern="yyyy-MM-dd"/>
					</span>
                </td>
                <td>
                    <span><a class="viewBill" href="javascript:;" billid=${bill.id } billcc=${bill.billCode }><img
                            src="${pageContext.request.contextPath }/images/read.png" alt="查看" title="查看"/></a></span>
                    <span><a class="modifyBill" href="javascript:;" billid=${bill.id } billcc=${bill.billCode }><img
                            src="${pageContext.request.contextPath }/images/xiugai.png" alt="修改" title="修改"/></a></span>
                    <span><a class="deleteBill" href="javascript:;" billid=${bill.id } billcc=${bill.billCode }><img
                            src="${pageContext.request.contextPath }/images/schu.png" alt="删除" title="删除"/></a></span>
                </td>
            </tr>
        </c:forEach>
    </table>
    <%@include file="/jsp/rollpage.jsp" %>
</div>
</section>
<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeBi">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>你确定要删除该订单吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>

<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/billlist.js"></script>