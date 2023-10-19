package css.cis3334;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView textViewStatus;
    Button buttonGetFact, buttonCurrency, buttonWeather, buttonSpaceNews, buttonStudentAPI;
    ViewModelCurrency viewModelCurrency;
    ;

    // ---- Remember to use Volley web library add the following to the Module build.gradle file, see https://developer.android.com/training/volley
    //      implementation 'com.android.volley:volley:1.1.1'
    private RequestQueue mRequestQueue;         // Used by Volley to do HTTP request

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewStatus = findViewById(R.id.textViewStatus);
        // May need to add the following to the Module build.gradle file's dependencies section
        // implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
        viewModelCurrency = new ViewModelProvider(this).get(ViewModelCurrency.class);

        setupButtonCatFact();
        setupButtonCurrency();
        setupButtonWeather();
        setupButtonSpaceNews();
        setupButtonStudentAPI();
    }

    private void getStudentAPI() {
        // ======================= Student must add code here to get JSON data from an API =======================
        textViewStatus.setText("Not implemented yet ....");

    }
    private void getCatFact() {
        // Define URL to use. Using Cat Facts API here. Note number of facts set to 1
        // ---- Remember to add the following permission to the AndroidManifest.xml file
        //      <uses-permission android:name="android.permission.INTERNET" />
        String url = "https://cat-fact.herokuapp.com/facts/random?animal_type=cat&amount=1";
        // Create a Volley web request to receive back a JSON object.
        // This requires two listeners for Async response, onResponse() and onErrorResponse()
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        textViewStatus.setText("Received Response ");
                        String jsonFact= response.toString();
                        // Remember to add the following to the module build.gradle file for the gson library for parsing json files
                        // implementation 'com.google.code.gson:gson:2.8.6'
                        Gson gson = new Gson();
                        CatFact catFact = gson.fromJson(jsonFact, CatFact.class);
                        textViewStatus.setText(catFact.getText());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        textViewStatus.setText("ERROR Response: " + error.toString());
                    }
                });

        // Create a RequestQueue used to send web requests using Volley
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    // Get current weather conditions from
    // https://openweathermap.org/api with api key of 5aa6c40803fbb300fe98c6728bdafce7
    // https://api.openweathermap.org/data/2.5/weather?q=Duluth&units=imperial&appid=5aa6c40803fbb300fe98c6728bdafce7
    private void getWeather() {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=Duluth&units=imperial&appid=5aa6c40803fbb300fe98c6728bdafce7";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Gson gson = new Gson();
                        OpenWeather obj = gson.fromJson(response.toString(), OpenWeather.class);
                        textViewStatus.setText("Temperature is "+obj.getTemp().toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textViewStatus.setText("ERROR Response: " + error.toString());
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    // get a list of space news articles from
    // https://spaceflightnewsapi.net/
    // https://test.spaceflightnewsapi.net/api/v2/articles
    private void getSpaceNews() {
        String url = "https://test.spaceflightnewsapi.net/api/v2/articles";
        JsonArrayRequest jsonArrayRequest  = new JsonArrayRequest (Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Loop through the array elements
                            Log.d("CIS 3334", "In getSpaceNews -- Number of articles retrieved = "+ response.length());
                            textViewStatus.setText("Space News: ");
                            for(int i=0;i<response.length();i++){
                                Gson gson = new Gson();
                                SpaceNews obj = gson.fromJson(response.getJSONObject(i).toString(), SpaceNews.class);
                                textViewStatus.append(obj.getSummary()+"\n ================== \n");
                            }
                        } catch (JSONException e) {
                            Log.d("CIS 3334", "In getSpaceNews -- JSONException = "+e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("CIS 3334", "In getSpaceNews -- onErrorResponse = "+error);

                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonArrayRequest);
    }

    private void setupButtonCatFact() {
        buttonGetFact = findViewById(R.id.buttonGetFact);
        buttonGetFact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CIS 3334", "getCatFact onClick");
                getCatFact();
            }
        });
    }

    private void setupButtonCurrency() {
        buttonCurrency = findViewById(R.id.buttonCurrency);
        buttonCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CIS 3334", "getCurrency onClick");
                textViewStatus.setText("Euro rate = "+viewModelCurrency.eurRate.toString());
            }
        });
    }
    private void setupButtonWeather() {
        buttonWeather = findViewById(R.id.buttonWeather);
        buttonWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CIS 3334", "getWeather onClick");
                getWeather();
            }
        });
    }

    private void setupButtonSpaceNews() {
        buttonSpaceNews = findViewById(R.id.buttonSpaceNews);
        buttonSpaceNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CIS 3334", "getSpaceNews onClick");
                getSpaceNews();
            }
        });
    }

    private void setupButtonStudentAPI() {
        buttonStudentAPI = findViewById(R.id.buttonStudentAPI);
        buttonStudentAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CIS 3334", "getStudentAPI onClick");
                getStudentAPI();
            }
        });
    }

}