package com.example.testcompose

import android.content.res.Configuration
import android.graphics.Paint
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.view.Gravity
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testcompose.ui.theme.TestComposeTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Kanban(SampleData.columns, SampleData.swimlane)
                }
            }
        }
    }
}

fun getColor() = (Math.random() * 16777215).toInt() or (0xFF shl 24)

@Composable
fun Card(data: CardData) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 20.dp,
        modifier = Modifier
            .padding(8.dp)
            .width(250.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            data.tags.forEach {
                Row {
                    Canvas(
                        modifier = Modifier.size(12.dp),
                        onDraw = {
                            drawCircle(
                                color = Color(getColor()),
                                center = center
                            )
                        }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.subtitle1
                    )
                }

            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = data.topic,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(data.avatars) { item ->
                    Image(
                        painter = painterResource(item),
                        contentDescription = "Avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(35.dp)
                            .clip(CircleShape)
                    )
                }
            }
        }
    }
}

@Composable
fun HeadOfColumn(name: String, size: Int) {

    Row {
        Canvas(
            modifier = Modifier
                .size(35.dp)
                .padding(start = 24.dp, top = 15.dp),
            onDraw = {
                drawRect(
                    color = Color(getColor())
                )
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$name - $size",
            fontSize = 12.sp,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.width(130.dp))
        IconButton(onClick = { }) {
            Icon(
                Icons.Default.Add,
                "Add content"
            )
        }
    }
}

@Composable
fun CardsColumn(columnData: ColumnData) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 20.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        Column {
            HeadOfColumn(name = columnData.name, columnData.cards.size)
            Divider(color = Color.Gray, thickness = 8.dp)
            LazyColumn {
                items(columnData.cards) { item ->
                    Card(item)
                }
            }
        }
    }
}

@Composable
fun Columns(columnData: List<ColumnData>) {
    LazyRow {
        items(columnData) { item ->
            Column {
                CardsColumn(item)
            }
        }
    }
}

@Composable
fun SwimlaneList(swimlane: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    var tableName: String by remember { mutableStateOf(swimlane[0]) }

    Row(modifier = Modifier.padding(start = 20.dp)) {
        Text(text = "Swimlane:")
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Row(Modifier.clickable {
                expanded = !expanded
            }) {
                Text(text = tableName, color = Color.Green)
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    "More",
                    tint = Color.Green
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                swimlane.forEach { item ->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        tableName = item
                    }) {
                        Text(item)
                    }
                }
            }
        }
    }
}

@Composable
fun TeamList() {
    Row(modifier = Modifier.padding(15.dp)) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            "Back"
        )
        Spacer(modifier = Modifier.padding(12.dp))
        Text("The Princess Bride")
        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            "More"
        )
    }
}

@Composable
fun Kanban(columnData: List<ColumnData>, swimlane: List<String>) {
    Column {
        TeamList()
        SwimlaneList(swimlane)
        Columns(columnData)
    }
}


@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun DefaultPreview() {
    Kanban(SampleData.columns, SampleData.swimlane)
}
