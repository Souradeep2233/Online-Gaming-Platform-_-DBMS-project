package com.example.quizforfun.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.room.TypeConverter;

import java.util.List;

public class Converter {

    @TypeConverter
    public String fromAttemptList(List<Attempt> attempts) {
        return new Gson().toJson(attempts);
    }

    @TypeConverter
    public List<Attempt> toAttemptList(String json) {
        return new Gson().fromJson(json, new TypeToken<List<Attempt>>() {}.getType());
    }
}