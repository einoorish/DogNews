package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ArticleAdapter(private val mListItemClickListener: ListItemClickListener, private val mArticleData: List<Article>): RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

   interface ListItemClickListener{
       fun onListItemClick(article:Article)
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ArticleViewHolder(inflater.inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
       // var height = holder.tv_title.height
       // holder.imageView.layoutParams = RelativeLayout.LayoutParams(holder.imageView.width, height)
        holder.tv_title.text = mArticleData[position].title
        Picasso.get().load(mArticleData[position].imageUrl).resize(140, 90).centerCrop().into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return mArticleData.size
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            mListItemClickListener.onListItemClick(mArticleData[adapterPosition])
        }

        val imageView: ImageView = itemView.findViewById(R.id.image)
        val tv_title: TextView = itemView.findViewById(R.id.title)

    }


}