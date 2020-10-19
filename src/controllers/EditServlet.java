package controllers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Tasks;
import utils.DButil;

/**
 * Servlet implementation class EditServlet
 */
@WebServlet("/edit")
public class EditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DButil.createEntityManager();

        //messageクラスでmessagesテーブル全部抜いてきて、その中から、getParameteのidで全部のレコード持ってくる
        Tasks m = em.find(Tasks.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        //メッセージ情報とセッションIDをリクエストスコープに登録
        request.setAttribute("tasks", m);
        request.setAttribute("_token", request.getSession().getId());

        //メッセージIDをセッションスコープに登録
        if(m != null){
        request.getSession().setAttribute("tasks_id", m.getId());
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/tasklist/edit.jsp");
        rd.forward(request, response);

    }

}
