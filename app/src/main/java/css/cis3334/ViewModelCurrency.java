package css.cis3334;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

public class ViewModelCurrency  extends AndroidViewModel {
    CurrencyRate currencyRate;
    Application application;
    String url = "https://api.ratesapi.io/api/latest?base=USD&symbols=EUR";
    Float eurRate = -999.9f;

    public ViewModelCurrency(@NonNull Application application) {
        super(application);
        this.application = application;
        getCurrency();
    }

    public Float getEur() {
        return eurRate;
    }

    // Get currency conversion rates from
    // https://ratesapi.io/documentation/
    // https://api.ratesapi.io/api/latest?base=USD&symbols=EUR
    private void getCurrency() {
        String url = "https://api.ratesapi.io/api/latest?base=USD&symbols=EUR";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.d("CIS 3334", "Updating currency rate");
                        Gson gson = new Gson();
                        currencyRate = gson.fromJson(response.toString(), CurrencyRate.class);
                        eurRate = currencyRate.getEUR();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("CIS 3334", "Error getting currency rate" + error.toString());

                        eurRate = -999.9f;
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(application);
        queue.add(jsonObjectRequest);
    }

}
