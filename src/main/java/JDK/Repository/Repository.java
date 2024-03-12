package JDK.Repository;

import java.io.IOException;

public interface Repository{
    void saveInLog(String text) throws IOException;

    String loadLog();
}
