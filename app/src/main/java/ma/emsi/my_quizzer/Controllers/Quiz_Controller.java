package ma.emsi.my_quizzer.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ma.emsi.my_quizzer.Entities.Question;
import ma.emsi.my_quizzer.Entities.QuestionsRepositories;
import ma.emsi.my_quizzer.Quizzes;
import ma.emsi.my_quizzer.Score;

public class Quiz_Controller {

    static ImageView iv_QuizzImg;
    static TextView tv_Question;
    static RadioGroup rg_choices;
    static RadioButton rb_choice1;
    static RadioButton rb_choice2;


    public Quiz_Controller(ImageView iv, TextView tv, RadioGroup rg, RadioButton rb1, RadioButton rb2) {
        iv_QuizzImg = iv;
        tv_Question = tv;
        rg_choices = rg;
        rb_choice1 = rb1;
        rb_choice2 = rb2;
    }

    public Quiz_Controller(Quiz_Controller quiz_controller) {
        this (
                iv_QuizzImg,
                tv_Question,
                rg_choices,
                rb_choice1,
                rb_choice2
        );
    }

    public static void setQuizImg(String Url, ImageView iv) {
        Picasso.get ()
                .load (Url)
                .fit ().into (iv);
    }

    public static void setTextView(String Question, TextView tv) {
        tv.setText (Question);
    }

    public static void setRadioButton(String Answer, RadioButton rb) {
        rb.setText (Answer);
    }

    public static void setViewComponants(Question question) {
        Quiz_Controller.setQuizImg (question.getImg (), iv_QuizzImg);
        Quiz_Controller.setTextView (question.getValue (), tv_Question);
        Quiz_Controller.setRadioButton (question.getChoice1 ().getAnswer (), rb_choice1);
        Quiz_Controller.setRadioButton (question.getChoice2 ().getAnswer (), rb_choice2);
        clearRadioButtons (rb_choice1, rb_choice2);
    }

    public static void setLoadingToVisibilityTrue(pl.droidsonroids.gif.GifImageView progress, LinearLayout ll) {
        progress.setVisibility (View.VISIBLE);
        ll.setAlpha ((float) 0.1);

    }

    public static void clearRadioButtons(RadioButton rb1, RadioButton rb2) {
        rb1.setChecked (false);
        rb2.setChecked (false);
    }

    public static void setLoadingToVisibilityFalse(pl.droidsonroids.gif.GifImageView progress, LinearLayout ll) {

        progress.setVisibility (View.GONE);
        ll.setAlpha ((float) 0.9);
    }

    public static void setNextQuizz(View v, Activity activity) {
        if (Quizzes.currentQuestion == QuestionsRepositories.list_Questions.size () - 1) {
            activity.startActivity (new Intent (v.getContext (), Score.class));
            activity.finish ();
            Quizzes.currentQuestion = 0;
        } else {
            Quizzes.currentQuestion++;
            setViewComponants (QuestionsRepositories.list_Questions.get (Quizzes.currentQuestion));
        }
    }

    public static boolean getChoiceResult(int type) {
        boolean isChoiceCorrect = false;
        if (type == 1) {
            isChoiceCorrect = QuestionsRepositories.list_Questions
                    .get (Quizzes.currentQuestion)
                    .getChoice1 ().isCorrect ();

        } else if (type == 2) {

            isChoiceCorrect = QuestionsRepositories.list_Questions
                    .get (Quizzes.currentQuestion)
                    .getChoice2 ().isCorrect ();
        }
        return isChoiceCorrect;
    }

    public static void getPoint(boolean isChoiceCorrect) {
        if (isChoiceCorrect)
            Quizzes.score++;
    }

    public static void restartQuizz(View v, Activity activity) {
        Quizzes.score = 0;
        Quizzes.currentQuestion = 0;
        setViewComponants (QuestionsRepositories.list_Questions.get (0));
        activity.startActivity (new Intent (v.getContext (), Quizzes.class));
    }

}
