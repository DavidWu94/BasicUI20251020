package tw.edu.pu.csim.tcyang.basicui

import android.app.Activity
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tw.edu.pu.csim.tcyang.basicui.ui.theme.BasicUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicUITheme {
                // *** 修正 1: 恢復成不含 bottomBar 的簡單 Scaffold ***
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Main(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Main(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var mper by remember { mutableStateOf<MediaPlayer?>(null) }
    var nowPlaying by remember { mutableStateOf("點擊按鈕播放音樂") }

    val animals = listOf(
        R.drawable.animal0, R.drawable.animal1, R.drawable.animal2, R.drawable.animal3,
        R.drawable.animal4, R.drawable.animal5, R.drawable.animal6, R.drawable.animal7,
        R.drawable.animal8, R.drawable.animal9
    )
    val animalsName = listOf(
        "鴨子", "企鵝", "青蛙", "貓頭鷹", "海豚",
        "牛", "無尾熊", "獅子", "狐狸", "小雞"
    )

    var flag by remember { mutableStateOf("test") }

    // 將狀態變數移回 Main 函式
    var showFox by remember { mutableStateOf(true) }

    DisposableEffect(Unit) {
        onDispose {
            mper?.release()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFE0BBE4))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_title),
            fontSize = 25.sp,
            color = Color.Blue,
            fontFamily = FontFamily(Font(R.font.kai))
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = stringResource(R.string.app_author),
            fontSize = 20.sp,
            color = Color(0xFF654321)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Row {
            Image(painter = painterResource(id = R.drawable.android), contentDescription = "Android 圖示", modifier = Modifier.size(100.dp).clip(CircleShape).background(Color.Yellow), alpha = 0.6f)
            Image(painter = painterResource(id = R.drawable.compose), contentDescription = "Compose icon", modifier = Modifier.size(100.dp))
            Image(painter = painterResource(id = R.drawable.firebase), contentDescription = "Firebase icon", modifier = Modifier.size(100.dp))
        }
        Spacer(modifier = Modifier.size(10.dp))
        LazyRow {
            items(51) { index ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "$index: ")
                    Text(text = animalsName[index % 10])
                    Image(painter = painterResource(id = animals[index % 10]), contentDescription = "可愛動物", modifier = Modifier.size(60.dp))
                }
            }
        }
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = nowPlaying)
        Spacer(modifier = Modifier.size(10.dp))
        Button(onClick = { flag = if (flag == "test") "A" else "test" }) {
            Text("歡迎修課")
        }
        Text(flag)
        Spacer(modifier = Modifier.size(20.dp))

        // 三個主要功能按鈕的 Row
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { /* ... */ }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = Color.Green)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "歡迎", color = Color.Blue); Text(text = "修課", color = Color.Red)
                    Image(painter = painterResource(id = R.drawable.teacher), contentDescription = "teacher icon")
                }
            }
            Button(onClick = { /* ... */ }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "展翅飛翔", color = Color.White)
                    Image(painter = painterResource(id = R.drawable.fly), contentDescription = "fly icon")
                }
            }
            Button(onClick = { (context as? Activity)?.finish() }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BFFF)), shape = CutCornerShape(10), border = BorderStroke(1.dp, Color.Blue), elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp)) {
                Text(text = "結束App")
            }
        }

        // *** 修正 2: 將圖片切換功能程式碼移動到這裡 ***
        Spacer(modifier = Modifier.size(20.dp))

        val imageResource = if (showFox) R.drawable.animal8 else R.drawable.animal9

        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "可切換的動物圖片",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .clickable {
                    showFox = !showFox
                }
        )
        // 為了讓它下面也有些空間
        Spacer(modifier = Modifier.size(20.dp))
    }
}