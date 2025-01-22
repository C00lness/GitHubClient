package com.example.gitclient.screen


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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.DataStatus
import com.example.gitclient.MainViewModel
import com.example.gitclient.R
import com.example.gitclient.ui.theme.AliceBlue
import com.example.net.data.RepositoryItems
import com.example.net.data.UserItems

@Composable
fun SearchResultScreen(viewModel: MainViewModel, onClick: () -> Unit) {

    var query by rememberSaveable { mutableStateOf("") }
    val localUriHandler = LocalUriHandler.current
    val resource = viewModel.combinedData.collectAsState()

    Column()
    {
        Row(
            modifier = Modifier
                .background(color = Color.Black)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = query,
                onValueChange = {
                    query = it
                    if (it.length > 2) viewModel.getCombinedData(it + "+in:login", it + "+in:name")
                },
                label = { Text("Поиск", fontSize = 14.sp) },
                modifier = Modifier.padding(20.dp),
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = if (isSystemInDarkTheme()) Color.DarkGray else Color(0xffeeeeee),
                    unfocusedTextColor = if (isSystemInDarkTheme()) Color.LightGray else Color(0xff888888),
                    focusedContainerColor = if (isSystemInDarkTheme()) Color.White else Color.White,
                    focusedTextColor = if (isSystemInDarkTheme()) Color.DarkGray else Color(0xff222222),
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (isSystemInDarkTheme()) Color.Black else AliceBlue),
        ) {
            when (resource.value?.status)
            {
                DataStatus.Status.EMPTY -> DinamicScreen(R.drawable.baseline_hourglass_empty_24,
                    stringResource(R.string.EmptyMsg), stringResource(R.string.EmptyHeader), false,
                    { viewModel.getCombinedData(query + "+in:login", query + "+in:name") })

                DataStatus.Status.ERROR -> DinamicScreen(R.drawable.baseline_error_24,
                    stringResource(R.string.ErrorMsg) + "\n" + resource.value?.message.toString(),
                    stringResource(R.string.ErrorHeader), true,
                    { viewModel.getCombinedData(query + "+in:login", query + "+in:name") })
                DataStatus.Status.SUCCESS ->
                    LazyColumn(
                        Modifier
                            .fillMaxSize()
                            .padding(20.dp)) {
                        resource.value?.data?.items
                            ?.let{ combinedItems ->
                            items(combinedItems){ item->
                                when(item){
                                    is UserItems -> UserCard(item.htmlUrl.toString(), item.avatarUrl.toString(),
                                        item.name.toString(), localUriHandler, if (isSystemInDarkTheme()) Color.DarkGray else Color.White)
                                    is RepositoryItems ->
                                    {
                                        var checked = rememberSaveable  { mutableStateOf(false) }
                                        Card(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(5.dp)
                                                .clickable {
                                                    viewModel.url =
                                                        item.contentsUrl
                                                            .toString()
                                                            .replace("/{+path}", "")
                                                    onClick()
                                                },
                                            shape = RoundedCornerShape(5.dp),
                                            colors = CardDefaults.cardColors(
                                                containerColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.White,
                                                contentColor = if (isSystemInDarkTheme()) Color.Black else Color.DarkGray
                                            ),
                                            content = {
                                                Row()
                                                {
                                                    Text(
                                                        text = item.name.toString(),
                                                        modifier = Modifier.padding(10.dp), fontSize = 20.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
                                                    )

                                                    Column(
                                                        horizontalAlignment = Alignment.End,
                                                        modifier = Modifier.fillMaxSize()
                                                    )
                                                    {
                                                        Row(
                                                            horizontalArrangement = Arrangement.Center,
                                                            verticalAlignment = Alignment.CenterVertically
                                                        )
                                                        {
                                                            Text(
                                                                text = item.stargazersCount.toString(),
                                                                fontSize = 13.sp,
                                                                color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
                                                            )
                                                            Image(
                                                                painter = painterResource(R.drawable.baseline_star_border_24),
                                                                contentDescription = null
                                                            )
                                                            Text(
                                                                text = item.watchersCount.toString(),
                                                                fontSize = 13.sp,
                                                                modifier = Modifier.padding(start = 5.dp),
                                                                color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
                                                            )
                                                            Image(
                                                                painter = painterResource(R.drawable.baseline_remove_red_eye_24),
                                                                contentDescription = null,
                                                                modifier = Modifier.padding(start = 2.dp)
                                                            )
                                                            Text(
                                                                text = item.forksCount.toString(),
                                                                fontSize = 13.sp,
                                                                modifier = Modifier.padding(start = 5.dp),
                                                                color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
                                                            )
                                                            Image(
                                                                painter = painterResource(R.drawable.baseline_fork_right_24),
                                                                contentDescription = null
                                                            )
                                                        }
                                                    }
                                                }
                                                Row(horizontalArrangement = Arrangement.Center,
                                                    verticalAlignment = Alignment.CenterVertically) {
                                                    IconButton(onClick = {checked.value = !checked.value}) {
                                                        Icon(
                                                            if (checked.value == true)
                                                                Icons.Filled.KeyboardArrowDown
                                                            else Icons.Filled.KeyboardArrowUp,
                                                            contentDescription = "Скрыть\\Показать"
                                                        )
                                                    }
                                                    Text(
                                                        text = "Подробнее",
                                                        fontSize = 16.sp, color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
                                                    )
                                                }
                                                Column (Modifier.padding(10.dp)) {
                                                    if (checked.value == true) {
                                                        UserCard(item.owner?.htmlUrl.toString(), item.owner?.avatarUrl.toString(),
                                                            item.owner?.login.toString(), localUriHandler,
                                                            if (isSystemInDarkTheme()) Color.Black else Color.LightGray)
                                                        Text(text = "Описание: ", color =  if (isSystemInDarkTheme()) Color.LightGray else Color.Black)
                                                        Text(text = item.description.toString(), color =  if (isSystemInDarkTheme()) Color.LightGray else Color.Black)
                                                        Text(text = "Создан: " + item.createdAt.toString().replace("T", " ")
                                                            .substring(0, item.createdAt.toString().length - 4)
                                                            .toString(), modifier = Modifier.padding(top = 5.dp),
                                                             color =  if (isSystemInDarkTheme()) Color.LightGray else Color.Black)
                                                        Text(text = "Обновление: " + item.updatedAt.toString().replace("T", " ")
                                                            .substring(0, item.updatedAt.toString().length - 4)
                                                            .toString(), modifier = Modifier.padding(top = 5.dp),
                                                            color =  if (isSystemInDarkTheme()) Color.LightGray else Color.Black)
                                                    }
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                else ->  DinamicScreen(R.drawable.baseline_location_searching_24,
                    stringResource(R.string.SearchStartMsg) + String(Character.toChars(0x1F60A)),
                    stringResource(R.string.SearchStartHeader), false, { })
            }
        }
    }
}







