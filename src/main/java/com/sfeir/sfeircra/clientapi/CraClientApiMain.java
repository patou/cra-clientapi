package com.sfeir.sfeircra.clientapi;

import com.sfeir.sfeircra.cra.Cra;
import retrofit2.Retrofit;
import retrofit2.adapter.java8.Java8CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static java.lang.System.out;

/**
 *
 */
public class CraClientApiMain {
    static public void main(String[] argv) throws IOException, ExecutionException, InterruptedException {
        callWithRetrofit();
        //callWithUrlConnection();
    }


    private static void callWithUrlConnection() throws IOException {
        CraClientApiHttpUrlConnection service = new CraClientApiHttpUrlConnection("https://sfeircra.appspot.com/");
        String token = service.login("SFEIR.P", "SFEIR");
        out.println(token);
        Cra cra = service.get(token);
        printCra(cra);
    }

    private static void callWithRetrofit() throws IOException, ExecutionException, InterruptedException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sfeircra.appspot.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(Java8CallAdapterFactory.create())
                .build();

        CraClientApi service = retrofit.create(CraClientApi.class);

        String token = service.login("SFEIR.P", "SFEIR").get();
        out.println(token);
        service.get(token).thenAccept(cra -> {
            printCra(cra);
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
