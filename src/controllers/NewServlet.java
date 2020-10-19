package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Tasks;

/**
 * Servlet implementation class NewServlet
 */
@WebServlet("/new")
public class NewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        //CSRF対策
        //hiddenで送られた値とセッション値が同じであれば後処理で送信を受け付ける
        //_tokenという名前でセッションIDを取得して、リクエストスコープにいれる
        request.setAttribute("_token", request.getSession().getId());

        request.setAttribute("tasks", new Tasks());


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/tasklist/new.jsp");
        rd.forward(request, response);

        /*
        EntityManager em = DButil.createEntityManager();
        em.getTransaction().begin();

        //Messageのインスタンスを作成
        Message m = new Message();

        //mの
        String title = "taro";
        m.setTitle(title);

        String content = "hello";
        m.setContent(content);

        //現在日時を取得
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        m.setCreated_at(currentTime);
        m.setUpdated_at(currentTime);

        //データベースに保存
        em.persist(m);
        em.getTransaction().commit();

        //IDの値を表示
        response.getWriter().append(Integer.valueOf(m.getId()).toString());

        em.close();
        */
    }

}



/*
■NewServlet
①セッションIDを_tokenとして、リクエストスコープに入れる（後処理で使うため）
②messageをインスタンス化してリクエストスコープに入れる
　⇒インスタンスのフィールドだけ、作られる。
③new.jspに飛ばす
*/

