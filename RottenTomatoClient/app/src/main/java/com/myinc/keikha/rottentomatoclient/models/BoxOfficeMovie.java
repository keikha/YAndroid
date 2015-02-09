package com.myinc.keikha.rottentomatoclient.models;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by keikha on 1/29/15.
 */
public class BoxOfficeMovie {

    private String title;
    private int year;
    private String synopsis;
    private int criticsScore;
    private String posterURL;
    private ArrayList<String> castList;


    public static BoxOfficeMovie fromJSON(JSONObject movieObj)
    {
        BoxOfficeMovie movie = new BoxOfficeMovie();
        try
        {
            movie.title = movieObj.getString("title");
            movie.year = movieObj.getInt("year");
            movie.synopsis = movieObj.getString("synopsis");
            movie.criticsScore = movieObj.getJSONObject("ratings").getInt("critics_score");
            movie.posterURL = movieObj.getJSONObject("posters").getString("thumbnail");

            movie.castList = new ArrayList<String>();

            JSONArray castArray = movieObj.getJSONArray("abridged_cast");
            for(int i =0 ; i< castArray.length() ; i++)
            {
                movie.castList.add(castArray.getJSONObject(i).getString("name"));
            }
        }
        catch(JSONException e )
        {
            e.printStackTrace();
        }

        return movie;
    }


    public static ArrayList<BoxOfficeMovie> extractMoviesFromJSON(JSONArray moviesJSONArray) throws JSONException {
        ArrayList<BoxOfficeMovie> movies = new ArrayList<BoxOfficeMovie>();

        for( int i =0 ; i< moviesJSONArray.length() ; i++)
        {

            JSONObject obj = null;
            try{
                obj = moviesJSONArray.getJSONObject(i);
            }
            catch (JSONException e)
            {e.printStackTrace();}


            if(obj!=null)
            {
                BoxOfficeMovie movie = BoxOfficeMovie.fromJSON(obj);
                if(movie!=null)
                    movies.add(movie);
            }



        }
        return movies;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public int getCriticsScore() {
        return criticsScore;
    }

    public void setCriticsScore(int criticsScore) {
        this.criticsScore = criticsScore;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public ArrayList<String> getCastList() {
        return castList;
    }

    public String getCasts()
    {
        return TextUtils.join(", ", castList);
    }

    public void setCastList(ArrayList<String> castList) {
        this.castList = castList;
    }




}
