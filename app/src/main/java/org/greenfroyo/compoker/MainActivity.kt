package org.greenfroyo.compoker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.greenfroyo.compoker.model.*
import org.greenfroyo.compoker.ui.theme.CompokerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompokerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainPage()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPage(viewModel: GameViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        StatusView(credit = state.credit)
        ContentView(Modifier.weight(1f), state.cards, state.dropSelection, state.winningCredit, state.winningHand) { i ->
            viewModel.toggleDropCardAt(i)
        }
        ControlView(state = state,
            actSwitchBet = { viewModel.switchBet() },
            actDraw = { viewModel.draw() },
            actDrop = { viewModel.drop() })
    }
}

@Composable
fun StatusView(modifier: Modifier = Modifier, credit: Int) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(color = Color.LightGray)
            .fillMaxWidth()
            .padding(16.dp)
            .then(modifier)
    ) {
        Text(text = credit.toCredit(), fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ContentView(
    modifier: Modifier = Modifier,
    cards: Array<Card>,
    selection: Selection,
    winningCredit: Int,
    winningHand: PokerHand?,
    onSelect: (Int) -> Unit = { _ -> }
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(color = Color.Green)
            .fillMaxWidth()
            .padding(16.dp)
            .then(modifier)
    ) {
        BoardView(cards = cards, selection = selection, onSelect = { i -> onSelect(i) })
        winningHand?.also {
            Text(text = stringResource(id = it.getDisplayText()))
            if (winningCredit>0) Text(text = "+ $$winningCredit")
        }
    }
}

@Composable
fun ControlView(
    modifier: Modifier = Modifier,
    state: GameState,
    actSwitchBet: () -> Unit,
    actDraw: () -> Unit,
    actDrop: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = Color.LightGray)
            .fillMaxWidth()
            .padding(16.dp)
            .then(modifier)
    ) {
        Button(onClick = { /*TODO*/ }, enabled = false) {
            Text(text = "TABLE")
        }
        Text(
            text = state.bet.toCredit(),
            fontSize = 22.sp,
            modifier = Modifier
                .border(2.dp, Color.Black, RoundedCornerShape(4.dp))
                .padding(4.dp)
                .clickable { actSwitchBet() }
        )
        Button(onClick = { actDraw() }, enabled = state.enableDraw()) {
            Text(text = "Draw")
        }
        Button(onClick = { actDrop() }, enabled = state.enableDrop()) {
            Text(text = "Drop")
        }
    }
}

