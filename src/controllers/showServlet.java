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
 * Servlet implementation class showServlet
 */
@WebServlet("/show")
public class showServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public showServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DButil.createEntityManager();

        //該当のIDのメッセージ１件のみをデータベースから取得（１件だけデータを取ってくるem.find()）。Integer.parseIntで整数値に変換
        Tasks m = em.find(Tasks.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        //メッセージデータをリクエストスコープに
        request.setAttribute("tasks", m);


       RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/tasklist/show.jsp");
       rd.forward(request, response);

    }

}


/*
 ■サーブレット
 ①findのMessage.class指定で、messageのインスタンスが作られる。
 　そのうえで、getParameterでもらってきたidを指定すれば、それに紐づくmessageの情報だけ引っ張ってきて、mに入れる

 ②mをリクエストスコープに入れる

■jsp
①サーブレットの②で入れたmのフィールドをそれぞれ表示


質問
Message m = em.find(Message.class, Integer.parseInt(request.getParameter("id")));
で、
・findの第一引数にMessage.classを指定することで、messageのインスタンスが作られる
・findの第二引数でmessageのidを指定
することで、find()が、第二引数に紐づく、messageの各フィールドの情報を取ってきてくれていると
思います。（ここまであっていますでしょうか）
上記あっているとして、このfind()は、中身は以下の処理、ということでしょうか？
・messageのインスタンスおよびフィールドを作る
・hybernateが「@NamedQuery」に、where id =第二引数
　のクエリを投げてくれる
・戻ってきた結果をmessageの各setterに入れてくれる
・getterを呼んで、全getterの結果をもってきてくれる
⇒その結果をmに入れる
request.setAttribute("message", m);
これは、"message"に、mの結果（つまり全部のgetter分）を全部リクエストスコープに突っ込んで、
show.jspで使えるようにする、となると解釈していますが、あっていますでしょうか。

 */
