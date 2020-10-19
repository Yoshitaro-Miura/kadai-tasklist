package controllers;

import java.io.IOException;
import java.util.List;

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
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        //EntityManagerはDBへの接続をつかさどる。createEntityManagerも同じ
        EntityManager em = DButil.createEntityManager();

        //開くページを取得
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e){}


        //DBへの問い合わせと結果の格納。getResultListはリスト形式で取得
        List<Tasks> tasks = em.createNamedQuery("getAllTasklist", Tasks.class)
                .setFirstResult(15 * (page -1))
                .setMaxResults(15)
                .getResultList();

        //全件数を取得
        long tasklist_count = (long)em.createNamedQuery("getTasklistCount", Long.class).getSingleResult();
        System.out.println("てすと");

        em.close();

        request.setAttribute("tasks", tasks);
        //全件数
        request.setAttribute("tasklist_count", tasklist_count);
        request.setAttribute("page",page);

        //フラッシュメッセージがセッションスコープにセットされていたら
        //リクエストスコープに保存する（セッションスコープからは削除する）

        if(request.getSession().getAttribute("flush") != null){
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/tasklist/index.jsp");
        rd.forward(request, response);
    }

}


/*　処理のトレース
1. IndexServlet【コントローラ】
    ①DB接続する
    ⇒ここで、create文の生成？

    【モデル】へのアクセス
    ②リスト「messages」にクエリ実行結果を入れる。
    　クエリは「Message.class」の「@NamedQuery」で指定した名前「getAllMessages」を実行し、getResultList()で
    　結果を取得。
    　データの件数をカウント。
    ③DB接続を閉じる
    ④リクエストスコープmessagesに②のmessagesを突っ込む

    【View】への展開
    ⑤index.jspへ転送

2. index.jsp
    ①メッセージ一覧を表示するため、リクエストスコープmessageを取得して、ループさせ、
    　idにリンクを付与して表示、タイトルと本文も表示

    ②新規メッセージを作成するnewサーブレットのリンクを作る
*/
