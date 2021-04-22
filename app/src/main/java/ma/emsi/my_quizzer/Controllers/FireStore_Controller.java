package ma.emsi.my_quizzer.Controllers;

import android.util.Log;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ma.emsi.my_quizzer.Entities.Choice;
import ma.emsi.my_quizzer.Entities.Question;
import ma.emsi.my_quizzer.Entities.QuestionsRepositories;
import ma.emsi.my_quizzer.Quizzes;
import pl.droidsonroids.gif.GifImageView;


public class FireStore_Controller {

    public static void getDataFromFireStore(pl.droidsonroids.gif.GifImageView progress, LinearLayout ll) {
        FirebaseFirestore.getInstance ()
                .collection ("Quizzer-App-Data/Quizz-Art-Collection/Quiz-Art-1")
                .get ()
                .addOnCompleteListener (task -> {
                    if (task.isSuccessful ()) {
                        addDocumentDataToQuestionsList (task);
                        startFistQuestionInQuizz (progress, ll);
                    } else {
                        Log.d ("*** | **** :: ", "Error getting documents: ", task.getException ());
                    }
                });

    }

    private static void addDocumentDataToQuestionsList(Task<QuerySnapshot> task) {
        for (QueryDocumentSnapshot document : task.getResult ())
            QuestionsRepositories.list_Questions
                    .add (getQuestionFromDocument (document.getData ()));
    }

    private static void startFistQuestionInQuizz(GifImageView progress, LinearLayout ll) {
        Quiz_Controller.setViewComponants (QuestionsRepositories
                                                   .list_Questions
                                                   .get (0));
        Quizzes.currentQuestion = 0;
        Quiz_Controller.setLoadingToVisibilityFalse (progress, ll);
    }

    private static Question getQuestionFromDocument(Map<String, Object> mapData) {
        Question question = new Question ();
        List<Object> lstObject1 = new ArrayList<> (mapData.values ());
        question.setImg (lstObject1.get (0).toString ());
        question.setValue (lstObject1.get (1).toString ());
        question.setChoice1 (getChoiceFromDocument ((Map<String, Object>) lstObject1.get (2)));
        question.setChoice2 (getChoiceFromDocument ((Map<String, Object>) lstObject1.get (3)));
        return question;
    }

    private static Choice getChoiceFromDocument(Map<String, Object> map) {
        Choice choice = new Choice ();
        choice.setAnswer (map.get ("answer").toString ());
        boolean b = Boolean.parseBoolean (map.get ("isCorrect").toString ());
        choice.setCorrect (b);
        return choice;
    }

}
