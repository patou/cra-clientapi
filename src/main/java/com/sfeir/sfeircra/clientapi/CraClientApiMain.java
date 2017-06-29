package com.sfeir.sfeircra.clientapi;

import com.sfeir.sfeircra.cra.Cra;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;

import static java.lang.System.out;

/**
 *
 */
public class CraClientApiMain {
    static public void main(String[] argv) throws IOException {
        //callWithRetrofit();
        callWithUrlConnection();
    }


    private static void callWithUrlConnection() throws IOException {
        CraClientApiHttpUrlConnection service = new CraClientApiHttpUrlConnection("https://sfeircra.appspot.com/");
        String token = service.login("SFEIR.P", "SFEIR");
        out.println(token);
        Cra cra = service.get(token);
        printCra(cra);
    }

    private static void callWithRetrofit() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sfeircra.appspot.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CraClientApi service = retrofit.create(CraClientApi.class);

        String token = service.login("SFEIR.P", "SFEIR").execute().body();
        out.println(token);
        service.get(token).enqueue(new Callback<Cra>() {
            public void onResponse(Call<Cra> call, Response<Cra> response) {
                Cra cra = response.body();
                printCra(cra);
            }

            public void onFailure(Call<Cra> call, Throwable t) {

            }
        });
    }

    private static void printCra(Cra cra) {
        out.print("month=");
        out.println(cra.getMonth());
        out.println("--AvailableMonth--");
        cra.getAvailableMonth().keySet().forEach(System.out::println);
        out.println("--AvailableActivities--");
        cra.getAvailableActivities().keySet().stream().limit(5).forEach(System.out::println);
        out.println("...");
        out.println("--AvailableActivities--");
        cra.getActivities().forEach(activity -> {
            out.print(activity.getCodAct());
            out.print(" - ");
            out.println(activity.getComment());
            activity.getDays().entrySet().forEach(entry -> {
                out.print(" * ");
                out.print(entry.getKey());
                out.print(" - ");
                out.println(entry.getValue());
            });
        });
    }
}
