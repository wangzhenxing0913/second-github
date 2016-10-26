package com.umpay.book.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.umpay.book.entity.Book;
import com.umpay.book.service.BookService;
import com.umpay.book.service.impl.BookServiceImpl;

public class zipServlet extends HttpServlet {

	BookService service = new BookServiceImpl();

	@SuppressWarnings("static-access")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html:charset=utf-8");
		String act = request.getParameter("act");
		if ("zipLoad".equals(act)) {
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

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

}
