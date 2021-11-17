package com.example.vamosrachar;

import android.view.View;

public class Button_Controller implements View.OnClickListener{
    MainActivity mainActivity;

    public Button_Controller(MainActivity mainActivity) {
        this.mainActivity=mainActivity;
    }

    @Override
    public void onClick(View view) {
        double numPessoas = Double.parseDouble(this.mainActivity.numPeople.getText().toString());
        double value = Double.parseDouble(this.mainActivity.payValue.getText().toString());
        double result = 0;

        result = value / numPessoas;
    }
}
