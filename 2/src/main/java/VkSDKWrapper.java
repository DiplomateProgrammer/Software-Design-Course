import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vk.api.sdk.client.ClientResponse;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.queries.newsfeed.NewsfeedSearchQuery;

import java.io.IOException;

public class VkSDKWrapper implements VkClient {
    private final UserActor userActor;
    private final VkApiClient vkApiClient;
    private final int RESPONSE_CHUNK_SIZE = 200;

    VkSDKWrapper(int Uid, String accessToken) {
        this.userActor = new UserActor(Uid, accessToken);
        TransportClient transportClient = HttpTransportClient.getInstance();
        this.vkApiClient = new VkApiClient(transportClient);
    }

    public String getNumNewsInInterval(String key, int startTime, int endTime) throws IOException{
        try {
            NewsfeedSearchQuery searchQuery = vkApiClient.newsfeed().search(userActor)
                    .q(key)
                    .count(RESPONSE_CHUNK_SIZE)
                    .startTime(startTime)
                    .endTime(endTime);
            return searchQuery.executeAsRaw().getContent();
        } catch (ClientException e) {
            throw new IOException(e);
        }

    }
}
