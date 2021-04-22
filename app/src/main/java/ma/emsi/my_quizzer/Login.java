package ma.emsi.my_quizzer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ma.emsi.my_quizzer.Controllers.Firebase_Controller;


public class Login extends AppCompatActivity {

    public static String Login, Password;

    public static void setLoginAndPasswordValues(String log, String pass) {
        Login = log;
        Password = pass;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.login);

        // Declaration:
        Button btn_Login = findViewById (R.id.Log_B_Login);
        Button btn_SingUp = findViewById (R.id.Log_B_SingUp);

        EditText ed_Login = findViewById (R.id.Log_ET_Login),
                ed_Password = findViewById (R.id.Log_ET_Pswd);

        //Button Click Events :
        //1. Button SignUp Click Event
        btn_SingUp.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                moveToRegisterClass (v);
            }
        });

        //2. Button Login Click Event

        btn_Login.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                getDataFromEditTexts (ed_Login, ed_Password);

                if (isEditTextsAreEmpty ()) {
                    Toast.makeText (v.getContext (), "No values entered!", Toast.LENGTH_SHORT).show ();
                    finish ();
                } else
                    Firebase_Controller.loginToFirebase (Login.this, v, Login, Password);


            }
        });
    }

    private void getDataFromEditTexts(EditText et_Login, EditText et_Password) {
        Login = et_Login.getText ().toString ();
        Password = et_Password.getText ().toString ();
    }

    private boolean isEditTextsAreEmpty() {
        return Login.isEmpty () || Password.isEmpty ();
    }

    public void moveToRegisterClass(View v) {
        startActivity (new Intent (v.getContext (), Register.class));
    }

}