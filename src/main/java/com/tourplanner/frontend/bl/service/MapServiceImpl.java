package com.tourplanner.frontend.bl.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourplanner.frontend.bl.Publisher;
import com.tourplanner.frontend.dal.MapApi;

import okhttp3.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
@Service
@Configuration
@PropertySource("classpath:application.properties")
public class MapServiceImpl implements MapService {

    private final MapApi service;
    private static final Logger logger;
    static {
        try {
            // you need to do something like below instaed of Logger.getLogger(....);
            logger = LogManager.getLogger(TourServiceImpl.class);
        } catch (Throwable th) {
            throw new IllegalArgumentException("Cannot load the log property file", th);
        }
    }

    @Value("${apiKey}")
    private String apiKey;
    @Autowired
    public MapServiceImpl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.mapquestapi.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        service = retrofit.create(MapApi.class);
    }
    @Override
    public void getMap(UUID id, String from, String to) {
        Call<ResponseBody> responseBodyCall = service.fetchRoute("yaHxV4XvjwMdBRxa1tkcXOs6dgaw3vg4", from, to, "390,360");
        responseBodyCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                storeResponseInFile(response,id);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                logger.warn("API route call failed for route"+id.toString());
            }
        });

    }

    @Override
    public String[] getDistanceAndTime(String from, String to) throws IOException {
        Response<ResponseBody> response = service.getDistanceAndTime("yaHxV4XvjwMdBRxa1tkcXOs6dgaw3vg4",from,to, "k").execute();
        //System.out.println(apiKey);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode resultObj = mapper.readTree(response.body().string());
        String[] destAndTime = new String[2];
        try{
            destAndTime[0] = resultObj.get("route").get("distance").toString();
            destAndTime[1] = resultObj.get("route").get("realTime").toString();
        }catch (NullPointerException ex1){
            destAndTime[0] = "0.0";
            destAndTime[1] = "0";
        }

        return destAndTime;
    }

    private static void storeResponseInFile(Response<ResponseBody> response,UUID id) {
        try (ResponseBody body = response.body()) {
            byte[] bytes = body.bytes();
            FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir")+"/images/Route"+id+".png");
            fos.write(bytes);
            fos.close();
            Publisher.notifySubscribers(id);
        } catch (IOException e) {
            //add Logger
            logger.warn("The picture for the tour"+id.toString()+" could not be stored");
        }
    }
}
