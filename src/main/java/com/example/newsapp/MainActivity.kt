package com.example.newsapp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.AsyncTaskLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , LoaderManager.LoaderCallbacks<List<Article>> ,
    ArticleAdapter.ListItemClickListener{

    override fun onListItemClick(article: Article) {
        val articleUrl = Uri.parse(article.articleUrl)
        val intent = Intent(this, WebView::class.java)
        intent.putExtra("url", articleUrl.toString())
        startActivity(intent)
    }

    private val mArticleLoaderId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        runLoaders()
    }

    private fun runLoaders(){
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo =  connectivityManager.activeNetworkInfo
        if(networkInfo != null && networkInfo.isConnected){
            supportLoaderManager.initLoader(mArticleLoaderId, null, this)
        } else {
            Toast.makeText(this, "No internet connection",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<Article>> {
        return object : AsyncTaskLoader<List<Article>>(this) {
            override fun onStartLoading() {
                forceLoad()
            }

            override fun loadInBackground(): List<Article>? {
                return QueryUtils.fetchArticleData()
            }
        }
    }

    override fun onLoadFinished(loader: Loader<List<Article>>, data: List<Article>?) {

        if(!data.isNullOrEmpty()){
            recyclerView.adapter = ArticleAdapter(this,data)

        }
    }

    override fun onLoaderReset(loader: Loader<List<Article>>) {
    }


}
