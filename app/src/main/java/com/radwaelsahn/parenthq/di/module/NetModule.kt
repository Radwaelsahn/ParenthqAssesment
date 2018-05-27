package com.radwaelsahn.parenthq.di.module

import android.util.Log
import com.es.developine.network.INetworkApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetModule {

    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        Log.i("radwa", "1" + gson.toString());
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.baseUrl("https://jsonplaceholder.typicode.com/").build()
                .baseUrl("http://api.openweathermap.org/data/2.5/").build()
    }

    @Provides
    fun providesGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    fun provideNetworkService(retrofit: Retrofit): INetworkApi {
        Log.i("radwa", "2" + retrofit.baseUrl().toString());
        return retrofit.create(INetworkApi::class.java)
    }
}