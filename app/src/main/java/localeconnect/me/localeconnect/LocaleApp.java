package localeconnect.me.localeconnect;

import android.app.Application;

/**
 * Created by admin on 11/13/2017.
 */

public class LocaleApp extends Application {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
