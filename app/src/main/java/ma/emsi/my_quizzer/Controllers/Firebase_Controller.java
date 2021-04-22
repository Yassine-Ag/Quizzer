package ma.emsi.my_quizzer.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ma.emsi.my_quizzer.Quizzes;


public class Firebase_Controller {

    public static FirebaseAuth mAuth = FirebaseAuth.getInstance ();
    public static FirebaseUser mUser;

    public static void loginToFirebase(Activity l, View v, String Login, String Password) {

        mAuth.signInWithEmailAndPassword (Login, Password)
                .addOnCompleteListener (l, new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful ()) {
                            // Sign in success, update UI with the signed-in user's information
                            mAuth.getCurrentUser ();
                            Toast.makeText (v.getContext (), "Loading..",
                                            Toast.LENGTH_SHORT).show ();
                            moveToQuizzesClass (l, v);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText (v.getContext (), "Authentication failed!",
                                            Toast.LENGTH_SHORT).show ();
                        }
                    }
                });
    }

    public static void RegisterToFirebase(Activity a, View v, String Login, String Password) {
        mAuth.createUserWithEmailAndPassword (Login, Password)
                .addOnCompleteListener (a, new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful ()) {
                            mUser = mAuth.getCurrentUser ();
                            Toast.makeText (v.getContext (), "SignUp successfully!.",
                                            Toast.LENGTH_SHORT).show ();
                            //  moveToQuizzesClass (a, v);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText (v.getContext (), "Sign-Up failed!",
                                            Toast.LENGTH_SHORT).show ();
                        }

                    }
                });
    }

    public static void moveToQuizzesClass(@NonNull Activity activity, View v) {
        activity.startActivity (new Intent (v.getContext (), Quizzes.class));
    }

}
