package sd_dtu.synergygopartner;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AssignmentChooseAct extends AppCompatActivity {

    ListView fileslv;
    DatabaseReference mDatabasechecked;
    ArrayList<String> fi = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_choose);

        fileslv=(ListView)findViewById(R.id.agentlv);


       final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Wait...");
        progressDialog.show();
        progressDialog.setCancelable(true);


        final String id=getIntent().getStringExtra("Agentid");
        mDatabasechecked=FirebaseDatabase.getInstance().getReference();

        mDatabasechecked.child("file").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int x=0;
                for (DataSnapshot file : dataSnapshot.getChildren()) {
                    Log.i("file", file.getKey());
                    fi.add(file.getKey());
                    x++;

                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(AssignmentChooseAct.this, android.R.layout.simple_list_item_1,fi);
                if(x==0){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AssignmentChooseAct.this);
                    alertDialogBuilder.setMessage("Nothing is there!! Please Try Again.");

                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                           Intent intent=new Intent(AssignmentChooseAct.this,LoginAct.class);
                            startActivity(intent);
                        }
                    });


                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

                else{
                    fileslv.setAdapter(arrayAdapter);

                    progressDialog.dismiss();
                }


            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });


        fileslv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

              Intent intent=new Intent(AssignmentChooseAct.this,DetailsAct.class);
              String option= fi.get(i);
                intent.putExtra("choice",option);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }
}
