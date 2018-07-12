package com.yidao.mvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements IUserView{

    private EditText editText;
    private EditText editText2;
    private EditText editText3;
    private Button button;
    private Button button2;
    private IUserPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new UserPresenter(this);
        initView();
    }

    private void initView() {
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.saveUser(getID(),getFristName(),getLastName());
            }
        });
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadUser(getID());
            }
        });
    }

    @Override
    public int getID() {
        return Integer.parseInt(editText.getText().toString());
    }

    @Override
    public String getFristName() {
        return editText2.getText().toString();
    }

    @Override
    public String getLastName() {
        return editText3.getText().toString();
    }

    @Override
    public void setID(int id) {
        editText.setText(id+"");
    }

    @Override
    public void setFirstName(String firstName) {
        editText2.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        editText3.setText(lastName);
    }
}
