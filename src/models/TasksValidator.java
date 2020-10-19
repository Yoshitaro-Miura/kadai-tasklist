package models;

import java.util.ArrayList;
import java.util.List;

public class TasksValidator {

    //バリデーションを実行する
    public static List<String> validate(Tasks m){

        //errorsにArraylistを突っ込む宣言
        List<String> errors = new ArrayList<String>();



        //コンテンツエラーチェックメソッドの呼び出し
        String content_error = _validateContent(m.getContent());
            if(!content_error.equals("")){
                errors.add(content_error);
            }

        //validateメソッドの戻り値
        return errors;
    }




    private static String _validateContent(String content) {
        // TODO 自動生成されたメソッド・スタブ
        if(content == null || content.equals("")){
            return "メッセージを入力してください。";
        }

        return "";
    }


}
