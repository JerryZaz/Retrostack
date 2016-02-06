package us.hnry.retrostack;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import us.hnry.retrostack.model.SOQuestion;

/**
 * Created by Henry on 2/5/2016.
 * /search?page=1&order=desc&sort=activity&unsafe=true")
 * /search?order=desc&sort=activity")
 */
public class SOService {

    public interface SOAPI{
        @GET("{version}/search?page=1&order=desc&sort=activity&unsafe=true")
        Call<SOQuestion> getQuestions(
                @Path("version") String version,
                @Query("intitle") String intitle,
                @Query("site") String site
        );
    }
}
