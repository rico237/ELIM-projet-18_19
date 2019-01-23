package herrick_wolber.fr.elim.ApplicationSettings;

import android.support.multidex.MultiDexApplication;

import com.parse.Parse;

public class App extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("myAppId")
                .server("https://elim20182019.herokuapp.com/parse/")
                .build()
        );
    }
}
