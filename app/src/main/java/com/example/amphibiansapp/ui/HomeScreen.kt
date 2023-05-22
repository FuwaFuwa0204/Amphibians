package com.example.amphibiansapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibiansapp.R
import com.example.amphibiansapp.data.AmphibianData
import com.example.amphibiansapp.data.AmphibianUiState

@Composable
fun HomeScreen(
    amphibianUiState: AmphibianUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    //sealed 인터페이스를 사용하여 세 가지 옵션만 있다는 것을 컴파일러에 알린다(이렇게 하면 조건문이 완전해짐).
    when (amphibianUiState) {
        is AmphibianUiState.Loading -> LoadingScreen(modifier = modifier)
        is AmphibianUiState.Success -> AmphibianScreen(amphibianUiState.items, modifier)
        else -> ErrorScreen(retryAction = retryAction,modifier = modifier)
    }
}
//data class를 List형태로 바꾸기 -> AmphibianUiState의 리스트
@Composable
    fun AmphibianScreen(amphibians: List<AmphibianData>, modifier: Modifier = Modifier) {

    LazyColumn(modifier= modifier,
        verticalArrangement = Arrangement.Center,
        contentPadding = PaddingValues(4.dp)
    ) {
        //아이템 리스트와 키 설정
        items(amphibians, { amphibians -> amphibians.name }) {
            AmphibianCard(amphibian = it, modifier = Modifier.fillMaxSize())
        }
    }
}

//data class 매개변수에 정의
@Composable
fun AmphibianCard(modifier:Modifier = Modifier, amphibian:AmphibianData){
    Card(modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                //$s에 삽입
                text = stringResource(R.string.amphibian_title, amphibian.name, amphibian.type),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            //coil 라이브러리
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(amphibian.imgSrc)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img)
            )
            Text(
                text = amphibian.description,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(20.dp)
            )
        }
    }

    }




@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            //로딩 이미지
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(stringResource(R.string.loading_failed))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}