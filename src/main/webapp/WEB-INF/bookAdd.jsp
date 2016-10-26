<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<title>收费项目管理</title> 、
<script type="text/javascript">
	
</script>
</head>
<body>
	<center>
		<form
			action="${pageContext.request.contextPath }/BookServlet?act=addBook"
			enctype="multipart/form-data" method="post">

			<table>
				<tr>
					<td>图书名<input type="text" name="bname" />
					</td>
				</tr>
				<tr>
					<td>作者名<input type="text" name="bauthor" />
					</td>
				</tr>
				<tr>
					<td>出版地址<input type="text" name="baddresss" />
					</td>
				</tr>
				<tr>
					<td>出售价格<input type="text" name="bprice" />
					</td>
				</tr>
				<tr>
					<td>图书封皮<input type="file" name="bpic" />
					</td>
				</tr>
				<tr>
				 <td>
				  <input type="submit" value="新增图书"/>
				 </td>
				</tr>
			</table>
		</form>
	</center>
</body>

</html>
