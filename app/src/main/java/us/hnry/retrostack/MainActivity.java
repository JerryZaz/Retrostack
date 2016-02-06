package us.hnry.retrostack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import us.hnry.retrostack.SOService.SOAPI;
import us.hnry.retrostack.model.SOQuestion;

public class MainActivity extends AppCompatActivity {

    private static final String API_BASE_URL = "https://api.stackexchange.com";
    private final String intitle = "android";
    private final String site = "stackoverflow";
    private Call<SOQuestion> call;
    private SOQuestion question;
    private List<SOQuestion.SOItem> items;
    private QuestionsAdapter questionsAdapter;
    private String version = "2.2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        questionsAdapter = new QuestionsAdapter(items);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.questions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(questionsAdapter);

        //SOAPI soapi = retrofit.create(SOAPI.class);
        SOAPI soapi = retrofit.create(SOAPI.class);

        call = soapi.getQuestions(version, intitle, site);
        call.enqueue(new Callback<SOQuestion>() {
            @Override
            public void onResponse(Response<SOQuestion> response) {
                try {
                    Log.v("onResponse", "Reached.");
                    question = response.body();
                    items = question.getItems();
                    questionsAdapter.swapList(items);
                } catch (NullPointerException e) {
                    Log.v("Catch", "Reached.");
                    Toast toast = null;
                    if (response.code() == 401) {
                        toast = Toast.makeText(MainActivity.this, "Unauthenticated", Toast.LENGTH_SHORT);
                    } else if (response.code() >= 400) {
                        toast = Toast.makeText(MainActivity.this, "Client error "
                                + response.code() + " " + response.message(), Toast.LENGTH_SHORT);
                    }
                    if (toast != null) {
                        toast.show();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("getQuestions threw ", t.getMessage());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        call.cancel();
    }
}
