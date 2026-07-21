package com.noticeboard.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import com.noticeboard.dao.NoticeViewDAO;
import com.noticeboard.util.SessionUtil;
import com.noticeboard.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/mark-notice-read")
public class MarkNoticeReadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final NoticeViewDAO noticeViewDAO = new NoticeViewDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        Integer studentId = SessionUtil.getStudentId(request);
        String noticeIdParam = request.getParameter("noticeId");

        if (studentId == null || !ValidationUtil.isPositiveInteger(noticeIdParam)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try (PrintWriter out = response.getWriter()) {
                out.write("{\"success\": false}");
            }
            return;
        }

        int noticeId = Integer.parseInt(noticeIdParam);

        try {
            boolean recorded = noticeViewDAO.recordView(noticeId, studentId);

            try (PrintWriter out = response.getWriter()) {
                out.write("{\"success\": " + recorded + "}");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = response.getWriter()) {
                out.write("{\"success\": false}");
            }
        }
    }
}