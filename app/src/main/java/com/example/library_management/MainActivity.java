package com.example.library_management;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //references to buttons and other controls on the layout
    Button btn_add, btn_viewAll;
    EditText et_name;
    EditText et_page;
    Switch sw_IssuedBook;
    ListView lv_BookList;
    ArrayAdapter bookArrayAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_add = findViewById(R.id.btn_add);
        btn_viewAll = findViewById(R.id.btn_viewAll);
        et_name = findViewById(R.id.et_name);
        et_page = findViewById(R.id.et_pages);
        sw_IssuedBook = findViewById((R.id.sw_active));
        lv_BookList = findViewById(R.id.lv_Booklist);

        dataBaseHelper = new DataBaseHelper(MainActivity.this);

        ShowCustomerOnListview(dataBaseHelper);

        //button listeners for the add and view all buttons
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text;
                BookManagement bookManagement;
                try {
                    bookManagement = new BookManagement(-1, et_name.getText().toString(), Integer.parseInt(et_page.getText().toString()), sw_IssuedBook.isChecked());
                    Toast.makeText(MainActivity.this, bookManagement.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error! no input!", Toast.LENGTH_SHORT).show();
                    bookManagement = new BookManagement(-1, "error", 0, false);
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                boolean success = dataBaseHelper.addOne(bookManagement);
                Toast.makeText(MainActivity.this, "Success= " + success, Toast.LENGTH_SHORT).show();
                ShowCustomerOnListview(dataBaseHelper);
            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);


                bookArrayAdapter = new ArrayAdapter<BookManagement>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getEveryOne());


            }
        });
        lv_BookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                BookManagement clickedCustomer = (BookManagement) parent.getItemAtPosition(position);
                dataBaseHelper.deleteOne(clickedCustomer);
                ShowCustomerOnListview(dataBaseHelper);
                Toast.makeText(MainActivity.this, "Deleted" + clickedCustomer.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ShowCustomerOnListview(DataBaseHelper dataBaseHelper2) {
        bookArrayAdapter = new ArrayAdapter<BookManagement>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getEveryOne());
        lv_BookList.setAdapter(bookArrayAdapter);
    }
}
