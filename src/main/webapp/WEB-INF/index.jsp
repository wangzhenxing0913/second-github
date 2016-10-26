<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<title>收费项目管理</title>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/statis/Css/bootstrap-responsive.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/statis/Css/style.css" />
<%-- <script type="text/javascript"
	src="${pageContext.request.contextPath }/mstatis/Js/jquery.js"></script> --%>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/statis/Js/jquery.sorted.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/statis/Js/bootstrap.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/statis/Js/ckform.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/statis/Js/common.js"></script>
<script
	src="${pageContext.request.contextPath }/statis/Js/jquery.min.js"></script>
<style type="text/css">
body {
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}

@media ( max-width : 980px) { /* Enable use of floated navbar text */
	.navbar-text.pull-right {
		float: none;
		padding-left: 5px;
		padding-right: 5px;
	}
}
</style>
<script type="text/javascript">
	//页面的跳转
	function goPage(pageIndex) {
		location.href = "${pageContext.request.contextPath }/BookServlet?act=show&pageIndex="
				+ pageIndex;
	}
	
	//全选和反选
	function ckall() {
		var state = $("#ckall1").attr("checked");
		if (state == "checked") {
			$("[name=book]").attr("checked", "checked");
		} else {
			$("[name=book]").removeAttr("checked");
		}
	}
	
	//新增图书
	function addBook() {
		location.href = "${pageContext.request.contextPath}/BookServlet?act=goAddBook";
	}
	
	function zipDownload(){
	   document.forms[0].action="${pageContext.request.contextPath}/BookServlet?act=zipDownload";
	   document.forms[0].submit();
	}
	//批量导出到EXCEL
	function daochu() {
		var length = $("[name=book]:checked").length;
		if (length == 0) {
			alert("请先选择要导出的数据");
			return;
		} else {
			var books = $("[name=book]:checked");
			var str = "";
			for (i = 0; i < books.length; i++) {
				str = str + books[i].value + ",";
			}
		location.href = "${pageContext.request.contextPath }/BookServlet?act=daochu&bids="+str;
		}
		};
</script>
</head>
<body>
	<form action="" method="post">

		<table class="table table-bordered table-hover definewidth m10">
			<thead>
				<tr>
					<th>图书的标号</th>
					<th><input type="checkbox" id="ckall1" onclick="ckall()" />
					</th>
					<th>图书的名字</th>
					<th>作者</th>
					<th>价格</th>
					<th>出版地址</th>
					<th>封皮</th>
				</tr>
				<c:forEach items="${pager.recordList}" var="b">
					<tr>
						<td style="vertical-align:middle;">${b.bid }</td>
						<td style="vertical-align:middle;"><input type="checkbox"
							name="book" value="${b.bid}" /></td>
						<td style="vertical-align:middle;">${b.bname }</td>
						<td style="vertical-align:middle;">${b.bauthor}</td>
						<td style="vertical-align:middle;">${b.bauthor}</td>
						<td style="vertical-align:middle;">${b.baddress}</td>
						<td style="vertical-align:middle;"><a href="#"><img
								src="${pageContext.request.contextPath }/statis/Images/${b.bpic}"
								width="30px" height="40px"> </a></td>
						<td style="vertical-align:middle;"><a
							href="BookServlet?act=xiazai&bid=${b.bid}">下载</a>
						</td>
					</tr>
				</c:forEach>
			</thead>
		</table>
	</form>
	<table class="table table-bordered table-hover definewidth m10">
		<tr>
			<th><a href="javascript:daochu()" style="color:blue;">导出EXCEL</a>
			<th>
				<form action="BookServlet?act=inport" method="post" enctype="multipart/form-data">
					<input type="file" name="excel" /> <input type="submit" value="导入EXCEL" />
				</form>
				</th>
			<th><a href="javascript:addBook()">新增电子书</a></th>
			<th><a href="javascript:zipDownload()">批量下载</a>
			</th>
			<th colspan="5">
				<div class="inline pull-right page">
					<c:if test="${pager.pageIndex>1 }">
						<a href='javascript:goPage(1)'>第一页</a>
						<a href='javascript:goPage(${pager.pageIndex-1 })'>上一页</a>
					</c:if>
					<c:if test="${pager.pageIndex<=1 }">
						<a>第一页</a>
						<a>上一页</a>
					</c:if>
					<c:forEach begin="${pager.pageStart }" end="${pager.pageEnd }"
						var="num">
						<c:if test="${num==pager.pageIndex }">
							<c:if test="${num==1 }">
								<a><span class='current'>${num }</span> </a>
							</c:if>
							<c:if test="${num!=1 }">
								<c:if test="${num==pager.pageCount }">
									<a><span class='current'>${num }</span> </a>
								</c:if>
								<c:if test="${num!=pager.pageCount }">
									<a href="javascript:goPage(${num })"><span class='current'>${num
											}</span> </a>
								</c:if>
							</c:if>
						</c:if>
						<c:if test="${num!=pager.pageIndex }">
							<a href="javascript:goPage(${num })">${num }</a>
						</c:if>
					</c:forEach>
					<c:if test="${pager.pageIndex<pager.pageCount }">
						<a href='javascript:goPage(${pager.pageIndex+1 })'>下一页</a>
					</c:if>
					<c:if test="${pager.pageIndex==pager.pageCount }">
						<a>下一页</a>
						<a>最后一页</a>
					</c:if>
					<c:if test="${pager.pageIndex<pager.pageCount }">
						<a href='javascript:goPage(${pager.pageCount })'>最后一页</a>
					</c:if>
					&nbsp;&nbsp;&nbsp; 共<span class='current'>${pager.totalCount
						}</span>条记录 <span class='current'> ${pager.pageIndex
						}/${pager.pageCount } </span>页
				</div>
		</tr>
	</table>
</body>
</html>
