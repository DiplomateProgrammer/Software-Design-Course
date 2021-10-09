import com.google.gson.JsonElement;

import java.io.IOException;
import java.util.List;

public interface VkClient {
    public String getNumNewsInInterval(String key, int startTime, int endTime) throws IOException;
}
