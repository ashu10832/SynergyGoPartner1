package sd_dtu.synergygopartner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginAct extends AppCompatActivity {

    EditText ema,pass,agentID;
    public String Emailin,PassIn,AgentIDin;
    public FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ema=(EditText)findViewById(R.id.AgenitIDeditText);
        pass=(EditText)findViewById(R.id.PasseditText);
        agentID=(EditText)findViewById(R.id.id);

    }

    public void onClickLogin(View view){

        Emailin=ema.getText().toString();
        PassIn=pass.getText().toString();
        AgentIDin=agentID.getText().toString();

        if(TextUtils.isEmpty(Emailin)){
            Toast.makeText(this,"Please enter AgentID",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(PassIn)){
            Toast.makeText(this,"Please enter Password",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(AgentIDin)){
            Toast.makeText(this,"Please enter Password",Toast.LENGTH_LONG).show();
            return;
        }


        mAuth=FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(Emailin, PassIn)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Intent intent=new Intent(LoginAct.this,AssignmentChooseAct.class);
                            intent.putExtra("Agentid",AgentIDin);
                            startActivity(intent);
                        }

                        else {
                            Toast.makeText(LoginAct.this, "Login Failed check credentials",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });



    }
}
