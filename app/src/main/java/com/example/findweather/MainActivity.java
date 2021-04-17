package com.example.findweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.findweather.ApiInterface;
import com.example.findweather.Retrofit.ApiClient;
import com.example.findweather.Retrofit.Example;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ImageView search;
    TextView tempText, descText, humidityText;
    EditText textField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = findViewById(R.id.search);
        tempText = findViewById(R.id.tempText);
        descText = findViewById(R.id.descText);
        humidityText = findViewById(R.id.humidityText);
        textField = findViewById(R.id.textField);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textField.getText().toString().isEmpty())
                { String p=" ";
                    getWeatherData("Delhi");
                }
                else
               getWeatherData(textField.getText().toString().trim());
            }
        });
    }

    private void getWeatherData(String name)
    {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<Example> call = apiInterface.getweatherData(name);

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Log.d("data", response.body().getMain().getTemp());
                tempText.setText("Temp = "+response.body().getMain().getTemp());
                descText.setText("Feels Like = "+response.body().getMain().getFeels_like());
                humidityText.setText("Humidity is  = "+response.body().getMain().getHumidity());
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                tempText.setText("Temp Wrong");
                descText.setText("Feels Like Wrong");
                humidityText.setText("Humidity is Wrong");
            }
        });
    }
}