package ipca.example.newsapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ipca.example.newsapp.models.Article
import ipca.example.newsapp.models.toStringDate
import java.util.Date

@Composable
fun ArticleRowView(modifier: Modifier = Modifier, article: Article) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.padding(8.dp)) {
            article.urlToImage?.let {
                AsyncImage(
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .padding(6.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    model = it,
                    contentDescription = "Article Image",
                    contentScale = ContentScale.Crop
                )
            } ?: run {
                Image(
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp),
                    painter = painterResource(id = R.drawable.baseline_photo_camera_back_24),
                    contentDescription = "Article Image"
                )
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = article.title ?: "",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = article.description ?: "",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = article.publishedAt?.toStringDate() ?: ""
                )
            }
        }
        // Adicionar o separador entre os artigos
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 8.dp),
            thickness = 1.dp,
            color = Color.Gray
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ArticleRowViewPreview() {
    val article = Article(title = "title", description = "description", url = "url", urlToImage = null, publishedAt = Date())
    ArticleRowView(article = article)
}
