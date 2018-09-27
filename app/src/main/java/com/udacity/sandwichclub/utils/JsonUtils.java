package com.udacity.sandwichclub.utils;


import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /**
     * @param json The string representing de json data
     * @return a new sandwich object with the data from the jason
     */
    public static Sandwich parseSandwichJson(String json)  {

        // Variables that defines the json elements that we are going to read
        final String jsonSandwich = "name";
        final String jsonName = "mainName";
        final String jsonAlsoKnownAs = "alsoKnownAs";
        final String jsonPlaceOfOrigin = "placeOfOrigin";
        final String jsonDescription = "description";
        final String jsonImage = "image";
        final String jsonIngredients = "ingredients";

        Sandwich sandwich = null;

        try {
            JSONObject sandwichDataJson = new JSONObject(json);

            JSONObject sandwichInfo = sandwichDataJson.getJSONObject(jsonSandwich);
            String sandwichName = sandwichInfo.getString(jsonName);

            JSONArray alias = sandwichInfo.getJSONArray(jsonAlsoKnownAs);

            List<String> names = jsonArrayFormat(alias);

            String sandwichPlaceOfOrigin = sandwichDataJson.getString(jsonPlaceOfOrigin);
            String sandwichDescription = sandwichDataJson.getString(jsonDescription);
            String sandwichImage = sandwichDataJson.getString(jsonImage);

            JSONArray sandwichIngredients = sandwichDataJson.getJSONArray(jsonIngredients);

            List<String>ingredients = jsonArrayFormat(sandwichIngredients);

            //When we have all the data we create the object sandwich with the values
            sandwich = new Sandwich(sandwichName, names, sandwichPlaceOfOrigin, sandwichDescription, sandwichImage, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }


    /**
     * @param data A JSONArray data for parsing
     * @return The data with appropriated format, added commas between values and
     * a period at the end
     */
    private static List<String> jsonArrayFormat(JSONArray data) throws JSONException {

        final String SEPARATOR = ",";
        final String FINAL = ".";
        final int LAST = 1;

        List<String> result = new ArrayList<>();

        String[] item = new String[data.length()];

        for (int i = 0; i < data.length(); i++) {
            item[i] = data.getString(i);

            if (item[i] != null && i < data.length() - LAST) {
                String dataBuilder = item[i] + SEPARATOR;
                result.add(dataBuilder);
            } else {
                String dataBuilder = item[i] + FINAL;
                result.add(dataBuilder);
            }

        }
        return result;
    }
}
