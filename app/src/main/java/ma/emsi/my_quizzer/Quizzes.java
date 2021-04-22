package ma.emsi.my_quizzer;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ma.emsi.my_quizzer.Controllers.FireStore_Controller;
import ma.emsi.my_quizzer.Controllers.Quiz_Controller;
import ma.emsi.my_quizzer.Entities.QuestionsRepositories;


public class Quizzes extends AppCompatActivity {

    public static int score;
    public static int currentQuestion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.quizzes);


        //Declarations:
        TextView tv_Question = findViewById (R.id.tv_question);
        ImageView iv_QuizzImg = findViewById (R.id.iv_quizz_img);
        RadioGroup rg_choices = findViewById (R.id.rg_choices);
        RadioButton rb_choice1 = findViewById (R.id.rb_choice1);
        RadioButton rb_choice2 = findViewById (R.id.rb_choice2);

        Quiz_Controller quiz_controller = new Quiz_Controller (iv_QuizzImg,
                                                               tv_Question, rg_choices, rb_choice1, rb_choice2);

        if (QuestionsRepositories.list_Questions.isEmpty ())
            FireStore_Controller.getDataFromFireStore (findViewById (R.id.progress_loader),
                                                       findViewById (R.id.linL_quizz_2));

        rb_choice1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Quiz_Controller.getPoint (Quiz_Controller.getChoiceResult (1));
                Quiz_Controller.setNextQuizz (v, Quizzes.this);
            }
        });

        rb_choice2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Quiz_Controller.getPoint (Quiz_Controller.getChoiceResult (2));
                Quiz_Controller.setNextQuizz (v, Quizzes.this);
            }
        });
    }


}