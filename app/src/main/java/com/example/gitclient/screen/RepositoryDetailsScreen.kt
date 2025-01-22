package com.example.gitclient.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gitclient.MainViewModel
import com.example.gitclient.R
import com.example.gitclient.ui.theme.AliceBlue


@Composable
fun RepositoryDetailsScreen(viewModel: MainViewModel, onClick: () -> Unit) {
    val content = viewModel.content.collectAsState()
    val localUriHandler = LocalUriHandler.current

    BackHandler { onClick() }
    Column()
    {
        Row(
            modifier = Modifier
                .background(color = Color.Black).fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = viewModel.url.toString(),
                modifier = Modifier.padding(20.dp, 50.dp), color = Color.White, fontSize = 18.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (isSystemInDarkTheme()) Color.Gray else AliceBlue),
        ) {
            LazyColumn(Modifier.fillMaxSize().padding(20.dp, 30.dp)) {
                content.value?.let { contentItem ->
                    items(contentItem) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .clickable {
                                    if (item.type == "file")
                                        localUriHandler.openUri(item.htmlUrl.toString())
                                    else {
                                        viewModel.url = item.url.toString()
                                        viewModel.getContent(item.url.toString())
                                    }

                                },
                            shape = RoundedCornerShape(5.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.White, //Card background color
                                contentColor = if (isSystemInDarkTheme()) Color.White else Color.DarkGray  //Card content color,e.g.text
                            ),
                            content = {
                                Row()
                                {
                                    Image(
                                        painter = painterResource(
                                            if (item.type == "dir")
                                                R.drawable.baseline_folder_24
                                            else
                                                R.drawable.baseline_insert_drive_file_24
                                        ),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(48.dp)
                                            .padding(5.dp)
                                            .align(Alignment.CenterVertically)
                                    )
                                    Text(
                                        text = item.name.toString(), fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(10.dp)
                                            .align(Alignment.CenterVertically),
                                    )
                                    if (item.type == "file") {
                                        Column(
                                            horizontalAlignment = Alignment.End,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                        {
                                            Text(
                                                text = item.size.toString() + " B",
                                                fontSize = 14.sp,
                                                color = Color.Gray,
                                                modifier = Modifier.padding(10.dp)
                                                    .padding(0.dp, 15.dp, 10.dp, 5.dp),
                                            )
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}