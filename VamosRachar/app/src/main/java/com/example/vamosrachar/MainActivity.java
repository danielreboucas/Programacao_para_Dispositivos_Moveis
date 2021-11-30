package com.example.vamosrachar;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import java.text.DecimalFormat;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, TextToSpeech.OnInitListener {

    FloatingActionButton btShare;
    FloatingActionButton btSpeak;
    EditText numPeople, payValue;
    TextToSpeech ttsPlayer;
    TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (TextView) findViewById(R.id.resultView);

        numPeople = (EditText) findViewById(R.id.editTxtPeopleNum);
        numPeople.addTextChangedListener(this);

        payValue = (EditText) findViewById(R.id.editTxtValue);
        payValue.addTextChangedListener(this);

        btShare = (FloatingActionButton) findViewById(R.id.shareFloatingButton);
        btShare.setOnClickListener(this);
        btSpeak = (FloatingActionButton) findViewById(R.id.speakFloatingButton);
        btSpeak.setOnClickListener(this);


        Intent checkTTS = new Intent();
        checkTTS.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTS, 1122);

    }

    @Override
    public void onClick(View view) {

        if (view==btShare) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "O valor da conta por pessoa é de: " + result.getText().toString());
            startActivity(intent);
        }

        if (view==btSpeak) {
            if (ttsPlayer != null) {
                ttsPlayer.speak("O valor da conta por pessoa é de " + result.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1122) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                ttsPlayer = new TextToSpeech(this, this);
            } else {
                Intent installTTS = new Intent();
                installTTS.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTS);
            }
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Toast.makeText(this, "TTS ativado", Toast.LENGTH_LONG).show();
        } else if (status == TextToSpeech.ERROR) {
            Toast.makeText(this, "TTS desativado", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.v("PDM", payValue.getText().toString());
        Log.v("PDM", numPeople.getText().toString());

        try {
            double numPessoas = Double.parseDouble(numPeople.getText().toString());
            double value = Double.parseDouble(payValue.getText().toString());
            double finalValue = 0;
            String formatedFinalValue;
            DecimalFormat df = new DecimalFormat("#.00");

            finalValue = value / numPessoas;
            formatedFinalValue = df.format(finalValue);
            result.setText("R$ " + formatedFinalValue);

        } catch (Exception e) {
            result.setText("R$ 0.00");
        }
    }


}