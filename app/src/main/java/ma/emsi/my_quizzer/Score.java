package ma.emsi.my_quizzer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;

import ma.emsi.my_quizzer.Controllers.Quiz_Controller;
import ma.emsi.my_quizzer.Entities.QuestionsRepositories;

public class Score extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.score);
        getImageFromFirebaseStore ();
        com.github.lzyzsd.circleprogress.DonutProgress donutProgress = findViewById (R.id.donut_progress);
        donutProgress.setProgress (Quizzes.score * 100 / QuestionsRepositories.list_Questions.size ());
        findViewById (R.id.bReplay).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Quiz_Controller.restartQuizz (v, Score.this);
                Quiz_Controller.setLoadingToVisibilityFalse (findViewById (R.id.progress_loader), findViewById (R.id.linL_quizz_2));
                finish ();
            }
        });

        findViewById (R.id.bLogOut).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //Firebase_Controller.mAuth.signOut();
                startActivity (new Intent (v.getContext (), Login.class));
                finish ();
            }
        });

        findViewById (R.id.bInfo).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (v.getContext (), LocationMaps.class));
            }
        });

    }

    public void getImageFromFirebaseStore() {
        String imgName = Login.Login.split ("@")[0];
        ImageView iv = findViewById (R.id.iv_score_Avatar);
        Register.firebaseStorage.child (imgName)
                .getDownloadUrl ().addOnSuccessListener (new OnSuccessListener<Uri> () {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with (Score.this)
                        .load (uri.toString ()).into (iv);
            }
        });


    }
}