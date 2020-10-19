package controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Tasks;
import models.TasksValidator;
import utils.DButil;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession()));
            EntityManager em = DButil.createEntityManager();

            //idはeditでセッションスコープに入れたものから取得
            Tasks m = em.find(Tasks.class, (Integer)(request.getSession().getAttribute("tasks_id")));



            String content = request.getParameter("content");
            m.setContent(content);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            m.setUpdated_at(currentTime);

            //バリデーションの実行
            //validateメソッドの呼び出して結果をerrorsに突っ込む
                List<String> errors = TasksValidator.validate(m);
                if(errors.size() > 0){
                    em.close();

                //フォームに初期値を設定、さらにエラーメッセージを送る
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("tasks", m);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/tasklist/edit.jsp");
                rd.forward(request, response);
                } else {

                em.getTransaction().begin();
                //必要な情報をセットした Message クラスのオブジェクトを persist メソッドを使ってデータベースにセーブします。
                em.getTransaction().commit();
                request.getSession().setAttribute("flush","更新が完了しました");
                em.close();

                response.sendRedirect(request.getContextPath() + "/index");

                //セッションスコープから不要になったデータを削除
                request.getSession().removeAttribute("tasks_id");

                }







    }

}
