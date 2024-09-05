package com.loeth.insight.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.loeth.insight.R
import com.loeth.insight.data.entity.Article
import com.loeth.insight.data.entity.NewsResponse
import com.loeth.insight.ui.theme.Purple40

@Composable
fun Loader(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp)
                .padding(10.dp),
            color = Purple40
        )

    }


}

val TAG = "News List"

@Composable
fun NewsList(response: NewsResponse) {

    Log.d(TAG, "Articles count: ${response.articles.size}")

    LazyColumn(){
        items(response.articles){ article ->
            NormalTextComponent(textValue = article.title ?: "NA")
        }

    }

}


@Composable
fun NormalTextComponent(textValue: String){
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        text = textValue,
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black

        )
    )
}


@Composable
fun HeaderTextComponent(textValue: String, centerAligned: Boolean = false){
    Text(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
            .padding(8.dp),
        text = textValue,
        style = TextStyle(
            fontSize =  24.sp,
            fontWeight = FontWeight.Medium

        ),
        textAlign = if(centerAligned) TextAlign.Center else TextAlign.Start
    )
}

@Composable
fun NewsRowComponent(page: Int, article: Article) {
    Column( modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
        .background(Color.White)
    ){
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .height(240.dp),
            model = article.urlToImage,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
            error = painterResource(id = R.drawable.lth)
        )

        Spacer(modifier = Modifier.size(20.dp))

        HeaderTextComponent(textValue = article.title?: "")

        Spacer(modifier = Modifier.size(10.dp))

        NormalTextComponent(textValue = article.content?: "")

        Spacer(modifier = Modifier.weight(1f))

        AuthorDetailComponent(article.author, article.source?.name)

    }
}

@Preview(showBackground = true)
@Composable
fun NewsRowComponentPreview(){
    val article = Article(
        null,
        author = "Mr X",
        title = "Hello Dummy News",
        null,
        null,
        null,
        null,
        null,
    )
    NewsRowComponent(0, article)
}

@Composable
fun AuthorDetailComponent(authorName:String?, sourceName:String?){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp, bottom = 24.dp)){
        if (authorName != null) {
            Text(text = authorName)
        }
        Spacer(modifier = Modifier.weight(1f))
        if (sourceName != null) {
            Text(text = sourceName)
        }


    }
}

@Composable
fun EmptyStateComponent(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        
        Image( painter = painterResource(id = R.drawable.no_data),
            contentDescription = null
        )
        
        HeaderTextComponent(textValue = "No News Available now, Please check back later! ",
            centerAligned = true )

    }
}