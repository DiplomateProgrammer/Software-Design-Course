import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.vk.api.sdk.client.ClientResponse;
import com.vk.api.sdk.exceptions.ClientException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VkManager {
    private final String ACCESS_TOKEN = "7318b5c17318b5c17318b5c145736114e1773187318b5c112793f1e46492bee95a0b7ea";
    private final int UID = 94604588;
    private final VkClient client;
    static final int MILLISECONDS_IN_SECOND = 1000;
    static final int SECONDS_IN_HOUR = 3600;


    public VkManager(VkClient client) {
        this.client = client;
    }

    public List<Integer> getNumNewsByHashTag(String hashTag, int hours) throws IOException {
        if (hours < 1 || hours > 24) {
            throw new IllegalArgumentException("Incorrect number of hours");
        }
        if (hashTag.isEmpty()) {
            throw new IllegalArgumentException("Empty hashTag");
        }
        if (hashTag.charAt(0) != '#') {
            throw new IllegalArgumentException("Request is not a hashtag");
        }
        List<Integer> res = new ArrayList<>(hours);
        int endTime = (int) (new Date().getTime() / MILLISECONDS_IN_SECOND);
        for (int i = 0; i < hours; ++i) {
            int startTime = endTime - SECONDS_IN_HOUR;
            String responseString = client.getNumNewsInInterval(hashTag, startTime, endTime);
            JsonElement wholeJson = new JsonParser().parse(responseString);
            JsonObject responseJson = wholeJson.getAsJsonObject().getAsJsonObject("response");
            JsonPrimitive totalCountJson = responseJson.getAsJsonPrimitive("total_count");
            if (totalCountJson == null) {
                throw new IOException("Request failed: received no response for key " + hashTag);
            }
            res.add(totalCountJson.getAsInt());
            endTime = startTime;
        }
        return res;
    }
}
