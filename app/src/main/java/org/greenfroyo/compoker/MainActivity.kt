package org.greenfroyo.compoker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.greenfroyo.compoker.model.BETTING
import org.greenfroyo.compoker.model.Card
import org.greenfroyo.compoker.model.GameUiState
import org.greenfroyo.compoker.model.GameViewModel
import org.greenfroyo.compoker.model.Selection
import org.greenfroyo.compoker.ui.theme.ChipHighBG
import org.greenfroyo.compoker.ui.theme.ChipHighBorder
import org.greenfroyo.compoker.ui.theme.ChipHighText
import org.greenfroyo.compoker.ui.theme.ChipLowBG
import org.greenfroyo.compoker.ui.theme.ChipLowBorder
import org.greenfroyo.compoker.ui.theme.ChipLowText
import org.greenfroyo.compoker.ui.theme.ChipMediumBG
import org.greenfroyo.compoker.ui.theme.ChipMediumBorder
import org.greenfroyo.compoker.ui.theme.ChipMediumText
import org.greenfroyo.compoker.ui.theme.CompokerTheme
import org.greenfroyo.compoker.ui.theme.DarkBlue
import org.greenfroyo.compoker.ui.theme.GradientColors
import org.greenfroyo.compoker.ui.theme.PrimaryDark

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Screen(viewModel: GameViewModel = viewModel()) {
    CompokerTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val state by viewModel.gameState.collectAsState()
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.cloth_g),
                    contentDescription = "null",
                    contentScale = ContentScale.FillBounds
                )
                Box(modifier = Modifier.fillMaxSize()) {
                    StatusView(
                        modifier = Modifier.align(Alignment.TopCenter),
                        credit = state.credit
                    )
                    ContentView(
                        Modifier,
                        state.cards,
                        state.dropSelection,
                        state.winningCredit,
                        state.winningHand
                    ) { i ->
                        viewModel.toggleDropCardAt(i)
                    }
                    ControlView(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        state = state,
                        actSwitchBet = { viewModel.switchBet() },
                        actDraw = { viewModel.draw() },
                        actDrop = {
                            viewModel.drop()
                            viewModel.newRound()
                            if (viewModel.haveNotEnoughCredit()) viewModel.switchBet()
                        })
                }
                if (viewModel.isGameOver()) {
                    GameOverDialog {
                        viewModel.restart()
                    }
                }
            }
        }
    }
}

@Composable
fun StatusView(modifier: Modifier = Modifier, credit: Int) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = DarkBlue)
            .fillMaxWidth()
            .padding(16.dp, 8.dp)
            .then(modifier)
    ) {
        CreditView(credit = credit)
        Spacer(modifier = Modifier.weight(1f))
        var openTableDialog by remember {
            mutableStateOf(false)
        }
        Button(onClick = { openTableDialog = true }) {
            Text(text = "TABLE")
        }
        if (openTableDialog) {
            HandTableDialog { openTableDialog = false }
        }
    }
}

@Composable
fun CreditView(
    modifier: Modifier = Modifier,
    credit: Int = 0
) {
    Box(modifier = Modifier.then(modifier), contentAlignment = Alignment.CenterStart) {
        Row() {
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = credit.toString(),
                textAlign = TextAlign.Right,
                color = Color(255, 255, 255, 192),
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .width(128.dp)
                    .background(color = Color(0, 0, 0, 100), shape = RoundedCornerShape(4.dp))
                    .padding(6.dp)
            )
        }
        Text(
            text = "$",
            fontSize = 34.sp,
            color = PrimaryDark,
            fontWeight = FontWeight.Bold
        )
    }

}


@OptIn(ExperimentalTextApi::class)
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
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        BoardView(modifier = Modifier
            .weight(5f)
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
            cards = cards, selection = selection, onSelect = { i -> onSelect(i) })
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(4f)
                .fillMaxWidth()
        ) {
            winningHand?.also {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(id = it.getDisplayText()),
                    style = MaterialTheme.typography.body2.merge(
                        TextStyle(brush = Brush.linearGradient(GradientColors))
                    )
                )
                if (winningCredit > 0)
                    Text(
                        text = "+ $$winningCredit", style = MaterialTheme.typography.body2.merge(
                            TextStyle(brush = Brush.linearGradient(GradientColors))
                        )
                    )
            }
        }
    }
}

@Composable
fun ControlView(
    modifier: Modifier = Modifier,
    state: GameUiState,
    actSwitchBet: () -> Unit,
    actDraw: () -> Unit,
    actDrop: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier), contentAlignment = Alignment.BottomCenter
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = DarkBlue)
                .fillMaxWidth()
                .padding(12.dp)
                .then(modifier)
        ) {
            Button(onClick = { actDraw() }, enabled = state.enableDraw()) {
                Text(text = "DRAW")
            }
            Spacer(modifier = Modifier.width(52.dp))
            Button(onClick = { actDrop() }, enabled = state.enableDrop()) {
                Text(text = "DROP")
            }
        }
        Column() {
            BettingChip(modifier = Modifier.clickable(
                    interactionSource = interactionSource, indication = null
                ) { actSwitchBet() }, bet = state.bet
            )
            val chipAnimation: Int by animateIntAsState(
                targetValue = if (state.phase == BETTING) 48 else 164,
                animationSpec = spring(0.5f)
            )
            Spacer(modifier = Modifier.height(chipAnimation.dp))
        }

    }
}

@Composable
fun BettingChip(modifier: Modifier = Modifier, bet: Int) {
    val chipColor = when (bet) {
        BET_MEDIUM -> ChipMediumBG
        BET_HIGH -> ChipHighBG
        else -> ChipLowBG
    }
    val chipBorder = when (bet) {
        BET_MEDIUM -> ChipMediumBorder
        BET_HIGH -> ChipHighBorder
        else -> ChipLowBorder
    }
    val chipText = when (bet) {
        BET_MEDIUM -> ChipMediumText
        BET_HIGH -> ChipHighText
        else -> ChipLowText
    }
    val stroke = Stroke(
        width = 18f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(95f, 60f), 46f)
    )
    Text(
        text = bet.toString(),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        color = chipText,
        fontSize = 22.sp,
        modifier = Modifier
            .width(36.dp)
            .drawBehind {
                drawCircle(
                    color = chipColor,
                    radius = this.size.width + 9
                )
            }
            .drawBehind {
                drawCircle(
                    color = chipBorder,
                    radius = this.size.width,
                    style = stroke
                )
            }
            .then(modifier)
    )
}
