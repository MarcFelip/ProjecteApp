package cat.udl.tidic.amd.beenote.network;
import android.util.Log;

import cat.udl.tidic.amd.beenote.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClientInstance {

    private static Retrofit retrofit;
    //IMPORTANT --> Canviar la IP tambe el arxiu res/xml/network_security_config.xml
    private static final String BASE_URL = BuildConfig.API_URL;

    public static Retrofit getRetrofitInstance() {
        Log.d("Retrofit", BASE_URL);
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
