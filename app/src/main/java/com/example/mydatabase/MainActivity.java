package com.example.mydatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    EditText editName, editSurname, editMarks,editId;
    Button btnAddData;
    Button btnViewData;
    Button btnUpdate;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);


        editName=(EditText)findViewById(R.id.editText_name);
        editSurname=(EditText)findViewById(R.id.editText_surname);
        editMarks=(EditText)findViewById(R.id.editText_marks);
        editId=(EditText)findViewById(R.id.editText_id);
        btnAddData=(Button)findViewById(R.id.button_add);
        btnViewData=(Button)findViewById(R.id.button_show);
        btnUpdate=(Button)findViewById(R.id.button_update);
        btnDelete=(Button)findViewById(R.id.button_delete);

        AddData();
        ViewData();
        //showMessage("ERROR","Nothing found");
        UpdateData();
        DeleteData();
    }

    public void DeleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Integer deletedRows = myDb.deleteData(editId.getText().toString());

                        if (deletedRows > 0) {
                            Toast.makeText(MainActivity.this, "Data is Deleted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Data is not Deleted", Toast.LENGTH_LONG).show();
                        }
                    }

                }
        );
    }
    public void UpdateData(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updataData(editId.getText().toString(),
                                editName.getText().toString(),
                                editSurname.getText().toString(),
                                editMarks.getText().toString());
                        if (isUpdate==true){
                            Toast.makeText(MainActivity.this,"Data is Updated",Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Data is not Updated", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //on click event data is inserted in database tables
                        boolean isInserted =  myDb.insertData(editName.getText().toString(),
                                editSurname.getText().toString(),
                                editMarks.getText().toString());

                        if(isInserted==true)
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data is not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void ViewData(){

        btnViewData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Cursor res = myDb.getAllData();

                        if(res.getCount()==0){
                            Toast.makeText(MainActivity.this,"No Data",Toast.LENGTH_LONG).show();
                            return;
                        }

                        StringBuffer buffer=new StringBuffer();

                        while(res.moveToNext()){

                            buffer.append("id"+ res.getString(0)+"\n");

                            buffer.append("Name"+ res.getString(1)+"\n");

                            buffer.append("Surname"+ res.getString(2)+"\n");

                            buffer.append("Marks"+ res.getString(3)+"\n\n");

                        }
                        //show all data

                        showMessage("Data",buffer.toString());


                    }
                }
        );

    }

    public void showMessage(String title, String Message){

        AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
