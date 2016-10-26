package com.umpay.book.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.umpay.book.entity.Book;
import com.umpay.book.service.BookService;
import com.umpay.book.service.impl.BookServiceImpl;
import com.umpay.book.util.ExcelImport;
import com.umpay.book.util.Pager;
import com.umpay.book.util.downLoad;

@SuppressWarnings("serial")
public class BookServlet extends HttpServlet  {
	BookService service = new BookServiceImpl();
	ExcelImport excel = new ExcelImport();

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html:charset=utf-8");
		String act = request.getParameter("act");
		/*
		 * 01.展示首页
		 */
		if (act.equals("show")) {
			show(request, response);
			/*
			 * 02.导出到EXCEL表中
			 */
		} else if (act.equals("daochu")) {
			daochuEXCEL(request, response);
			/*
			 * 03 跳转到新增页面
			 */
		} else if (act.equals("goAddBook")) {
			request.getRequestDispatcher("/WEB-INF/bookAdd.jsp").forward(
					request, response);
			// response.sendRedirect("${pageContext.request.contextPath}/WEB-INF/bookAdd.jsp");
			/*
			 * 04 新增图书
			 */
		} else if (act.equals("addBook")) {
			upfile(request, response);
			/*
			 * 05 下载电子书
			 */
		} else if ("xiazai".equals(act)) {
			sigledownload(request, response);
			/*
			 * 06 将excel表中的数据导入到数据库
			 */
		} else if ("inport".equals(act)) {
			try {
				improtExcel(request, response);
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			 * 07 多个文件批量下载成一个zip格式的压缩包
			 */
		} else if ("zipDownload".equals(act)) {
			String zipName = "myfile.zip";
	        response.setContentType("APPLICATION/OCTET-STREAM");  
	        response.setHeader("Content-Disposition","attachment; filename="+zipName);
	        ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
	        byte[] buf = new byte[1024];
			String[] books = request.getParameterValues("book");
			for (String bid : books) {
				Book b = service.getBid(bid);
				String bname = b.getBname() + ".text";
				File file = new File(this.getServletContext().getRealPath("\\statis\\ebook\\" + bname));
				FileInputStream fis = new FileInputStream(file);
				zos.putNextEntry(new ZipEntry(bname));
				zos.setEncoding("GBK");
				int read = 0;
				while ((read = fis.read(buf)) != -1) {
					zos.write(buf, 0, read);
				}
				zos.closeEntry();
				fis.close();
			}
			zos.close();
		}
	}

	// ===============================================================
	// ===========================
	// return "mdlsuccess";

	private void improtExcel(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, FileUploadException {
		String excelname=request.getParameter("excel");
		String inexcel = "";
		// 1 .判断请求中是否包含文件上传
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		// 2.创建工厂解析表单数据
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = upload.parseRequest(request);
				for (FileItem item : items) {
					     // 代表是文件上传框
						inexcel = item.getName();// 获取要上传的文件名
						if (inexcel != null && !inexcel.equals("")) {
							// 文件上传的路径 目前 在 fileUp文件夹中
							String uploadFilePath = request.getSession()
									.getServletContext()
									.getRealPath("/statis/Excel");
							File file = new File(uploadFilePath, inexcel);// 文件流
							try {
								item.write(file);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		
			}
		}
	String realPath = this.getServletContext().getRealPath("/statis/Excel/");
	System.out.println(realPath+inexcel);

		File file = new File(realPath+"/"+inexcel);
		List<Book> byExcel = excel.getAllByExcel(file);
		for (Book book : byExcel) {
			int bid = book.getBid();
			System.out.println(bid);
			if (!service.isExist(bid)) {
				service.addBook(book);
				// response.getWriter().write("<script>alert('新增成功！');location.href='/Book/BookServlet?act=show'</script>");
			} else {
				service.updateBook(book);
			}
		}
		//response.getWriter().write("<script>alert('导入成功');location.href='/Book/BookServlet?act=show'</script>");
		response.sendRedirect("/Book/BookServlet?act=show");
	}
		
	private void sigledownload(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			FileNotFoundException, IOException {
		String bid = request.getParameter("bid");
		List<Book> books = service.selectByBids(bid);
		for (Book book : books) {
			String bname = book.getBname() + ".text";

			// 让浏览器用附件的形式下载
			response.setHeader("Content-Disposition", "attachment;filename="
					+ URLEncoder.encode(bname, "utf-8"));
			response.setContentType(this.getServletContext().getMimeType(bname));// MIME类型

			InputStream in = new FileInputStream(this.getServletContext()
					.getRealPath("\\statis\\ebook\\" + bname));
			OutputStream out = response.getOutputStream();

			byte[] bs = new byte[1024];
			int i = 0;
			while ((i = in.read(bs)) != -1) {
				out.write(bs, 0, i);
			}
			out.close();
			in.close();
		}
	}

	private void upfile(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 变量的名字对应到表单中的name属性
		String bpic = "";
		String bname = "";
		String bauthor = "";
		String baddress = "";
		String bprice = "";
		// 1 .判断请求中是否包含文件上传
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart == true) {
			// 2.创建工厂解析表单数据
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				List<FileItem> items = upload.parseRequest(request);
				for (FileItem item : items) {
					if (item.isFormField() == true) {
						String fieldName = item.getFieldName();
						if ("bname".equals(fieldName)) {
							bname = item.getString("utf-8");
						} else if ("bauthor".equals(fieldName)) {
							bauthor = item.getString("utf-8");
						} else if ("baddress".equals(fieldName)) {
							baddress = item.getString("utf-8");
						} else if ("bprice".equals(fieldName)) {
							bprice = item.getString("utf-8");
						} else if ("bpic".equals(fieldName)) {
							bpic = item.getString("utf-8");
						}
					} else {// 代表是文件上传框
						bpic = item.getName();// 获取要上传的文件名
						if (bpic != null && !bpic.equals("")) {
							// 文件上传的路径 目前 在 fileUp文件夹中
							String uploadFilePath = request.getSession()
									.getServletContext()
									.getRealPath("/statis/Images");
							File file = new File(uploadFilePath, bpic);// 文件流
							item.write(file);
						}

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 新增数据到数据库中
		double price = Double.parseDouble(bprice);
		Book book = new Book(bname, baddress, bauthor, price, bpic);
		int count = service.addBook(book);
		if (count > 0) {
			response.sendRedirect("/Book/BookServlet?act=show");
			// response.getWriter().write("<script>alert('新增成功！');location.href='/Book/BookServlet?act=show'</script>");
		} else {
			request.getRequestDispatcher("/Book/WEB-INF/addBook.jsp").forward(
					request, response);
			// response.getWriter().write("<script>alert('新增失败');location.href='WEB-INF/addBook.jsp'</script>");
		}
	}

	private void daochuEXCEL(HttpServletRequest request,
			HttpServletResponse response) {
		String bid = request.getParameter("bids");
		String bids = bid.substring(0, bid.length() - 1);
		List<Book> books = service.selectByBids(bids);
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("图书统计表");
		int rowCount = 0;// 行数
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));// 合并单元格
		XSSFRow rowTitle = sheet.createRow(rowCount++);// 创建行
		XSSFCell cellTitle = rowTitle.createCell(0);// 创建列
		cellTitle.setCellValue("用户信息统计表");// 设置标题
		cellTitle.setCellStyle(bigTitleStyle(wb));

		// 设置行标题
		String[] title = new String[] { "标号", "书名", "作者", "地址", "价格", "图片" };
		XSSFRow smallRow = sheet.createRow(rowCount++);
		for (int i = 0; i < title.length; i++) {
			XSSFCell createCell = smallRow.createCell(i);
			createCell.setCellValue(title[i]);
		}

		// 循环生成内容
		for (int i = 0; i < books.size(); i++) {
			Book b = books.get(i);
			XSSFRow row = sheet.createRow(rowCount++);

			XSSFCell idcell = row.createCell(0);
			idcell.setCellValue(b.getBid());

			XSSFCell authorcell = row.createCell(1);
			authorcell.setCellValue(b.getBname());

			XSSFCell banemcell = row.createCell(2);
			banemcell.setCellValue(b.getBauthor());

			XSSFCell addresscell = row.createCell(3);
			addresscell.setCellValue(b.getBaddress());

			XSSFCell pricecell = row.createCell(4);
			pricecell.setCellValue(b.getBprice());

			XSSFCell piccell = row.createCell(5);
			piccell.setCellValue(b.getBpic());
		}

		// 生成Excel文件并下载
		String filename = "用户信息统计表.xlsx"; // Excel 2007 /2013
		OutputStream fos = null;
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ URLEncoder.encode(filename, "utf-8"));
			response.setContentType(this.getServletContext().getMimeType(
					filename));// MIME类型
			fos = response.getOutputStream();
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 大标题样式
	private static CellStyle bigTitleStyle(Workbook wb) {

		CellStyle curStyle = wb.createCellStyle();
		Font curFont = wb.createFont();

		curFont.setFontName("微软雅黑");
		curFont.setFontHeightInPoints((short) 16);
		curFont.setBoldweight(Font.BOLDWEIGHT_BOLD); // 字体加粗

		curStyle.setFont(curFont); // 绑定字体

		curStyle.setAlignment(CellStyle.ALIGN_CENTER); // 横向居中
		curStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 纵向居中

		return curStyle;
	}

	private void show(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pageIndex = 1;
		int pageSize = 3;
		String pageNo = request.getParameter("pageIndex");
		if (pageNo != null) {
			pageIndex = Integer.parseInt(pageNo);
		}
		int totalCount = service.totalCount();
		List<Book> pageContent = service.pageContent(pageSize, pageIndex);
		Pager p = new Pager(pageIndex, pageSize, totalCount, pageContent);
		request.setAttribute("pager", p);
		request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request,
				response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	

}
