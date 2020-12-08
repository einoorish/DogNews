package com.example.newsapp

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

object QueryUtils{

    fun fetchArticleData(): List<Article>? {

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://newscatcher.p.rapidapi.com/v1/search_free?q=dog&media=True&lang=en")
            .get()
            .addHeader("x-rapidapi-key", "a14d439bb0msh179934e7abdfdcfp16c708jsn92da962908c0")
            .addHeader("x-rapidapi-host", "newscatcher.p.rapidapi.com")
            .build()

        val response = client.newCall(request).execute()

        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        return extractArticleDataFromJson(response.body()!!.string())
    }

    private fun extractArticleDataFromJson(jsonResponse: String?): List<Article>? {

        if(jsonResponse == null || jsonResponse.isEmpty())
            return null

        val articles = mutableListOf<Article>()
        try{
            val baseJsonResponse = JSONObject(jsonResponse)
            val articleArray = baseJsonResponse.getJSONArray("articles")

            for(i in 0 until articleArray.length()){
                val currentArticle = articleArray.getJSONObject(i)

                val title = if (currentArticle.has("title")) currentArticle.getString("title")
                else "null"

                val media = if (currentArticle.has("media")) currentArticle.getString("media")
                else "null"

                val url = if (currentArticle.has("link")) currentArticle.getString("link")
                else "null"

                articles.add(Article(title, media, url))
            }
        } catch (e: JSONException) {
            Log.e("QueryUtils: ", "Problem parsing the article JSON results", e)
        }

        return articles
    }

}