package com.example.proyectedef.application;

import android.app.Application;

import com.example.proyectedef.models.City;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.concurrent.atomic.AtomicInteger;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MyApp extends Application {



    public static AtomicInteger CityID = new AtomicInteger();



    @Override

    public void onCreate() {

        setUpRealmConfig();



        Realm realm = Realm.getDefaultInstance();

        CityID = getIdByTable(realm, City.class);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        realm.close();

    }



    private void setUpRealmConfig() {

        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();

        Realm.setDefaultConfiguration(config);

    }



    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass) {

        RealmResults<T> results = realm.where(anyClass).findAll();

        return (results.size() > 0) ? new AtomicInteger(results.max("id").intValue()) : new AtomicInteger();

    }

}