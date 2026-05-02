package max.ohm.insight.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Colors
val BgColor = Color(0xFFF4F7FB)
val CardBgColor = Color(0xFFFFFFFF)
val TextPrimary = Color(0xFF1E1E1E)
val TextSecondary = Color(0xFF757575)

val BarBgColor = Color(0xFFE5DDF0)
val BarFillPurple = Color(0xFF9E85CD)
val BarFillPink = Color(0xFFEBA5B1)

val ChartLineColor = Color(0xFFEBA5B1)
val ChartGradientStart = Color(0x66EBA5B1)
val ChartGradientEnd = Color(0x00EBA5B1)

val DonutColor1 = Color(0xFF9E85CD)
val DonutColor2 = Color(0xFFEBA5B1)
val DonutColor3 = Color(0xFFB5D3C4)

val StabilityLight = Color(0xFFE5DDF0)
val StabilityDark = Color(0xFF9E85CD)

val LifestylePurple = Color(0xFF9E85CD)
val LifestyleRed = Color(0xFFEBA5B1)
val LifestyleGreen = Color(0xFFB5D3C4)
val LifestyleGray = Color(0xFFE0E0E0)
val BottomNavBg = Color(0xFFFFFFFF)

@Composable
fun InsightsScreen() {
    Scaffold(
        containerColor = BgColor,
        bottomBar = { BottomNavigationBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            TopBar()
            
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Spacer(modifier = Modifier.height(16.dp))
                
                StabilitySummaryCard()
                
                Spacer(modifier = Modifier.height(32.dp))
                
                SectionTitle("Cycle Trends")
                CycleTrendsCard()
                
                Spacer(modifier = Modifier.height(32.dp))
                
                SectionTitle("Body & Metabolic Trends")
                BodyMetabolicTrendsCard()

                Spacer(modifier = Modifier.height(32.dp))

                SectionTitle("Body Signals")
                BodySignalsCard()
                
                Spacer(modifier = Modifier.height(32.dp))
                
                SectionTitle("Lifestyle Impact")
                LifestyleImpactCard()

                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Insights",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
fun StabilitySummaryCard() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = CardBgColor
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = "Stability Summary", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Based on your recent logs and symptom patterns.", fontSize = 14.sp, color = TextSecondary)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Stability Score", fontSize = 14.sp, color = TextSecondary)
            Text(text = "78%", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            BoxWithConstraints(modifier = Modifier.fillMaxWidth().height(140.dp)) {
                val chartHeight = 120.dp
                
                Canvas(modifier = Modifier.fillMaxWidth().height(chartHeight).align(Alignment.BottomCenter)) {
                    val w = size.width
                    val h = size.height
                    
                    // Light purple background area
                    val pathLight = Path().apply {
                        moveTo(0f, h * 0.8f)
                        quadraticTo(w * 0.3f, h * 0.8f, w * 0.5f, h * 0.6f)
                        quadraticTo(w * 0.8f, h * 0.3f, w, h * 0.2f)
                        lineTo(w, h)
                        lineTo(0f, h)
                        close()
                    }
                    drawPath(pathLight, StabilityLight)
                    
                    // Dark purple foreground area
                    val pathDark = Path().apply {
                        moveTo(0f, h)
                        quadraticTo(w * 0.3f, h * 0.9f, w * 0.5f, h * 0.7f)
                        quadraticTo(w * 0.8f, h * 0.5f, w, h * 0.4f)
                        lineTo(w, h)
                        lineTo(0f, h)
                        close()
                    }
                    drawPath(pathDark, StabilityDark)
                    
                    // Tooltip line
                    val tooltipX = w * (2f / 3f) // Align with "Mar"
                    val pointY = h * 0.6f
                    drawLine(
                        color = TextSecondary.copy(alpha = 0.5f),
                        start = Offset(tooltipX, 0f),
                        end = Offset(tooltipX, h),
                        strokeWidth = 3f,
                        pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )
                    // Draw point marker
                    drawCircle(color = Color(0xFF6A9988), radius = 6.dp.toPx(), center = Offset(tooltipX, pointY))
                }
                
                // Y-axis labels
                Column(
                    modifier = Modifier.height(chartHeight).align(Alignment.BottomStart).padding(start = 4.dp, top = 16.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("32d", fontSize = 10.sp, color = TextPrimary, fontWeight = FontWeight.Medium)
                    Text("28d", fontSize = 10.sp, color = TextPrimary, fontWeight = FontWeight.Medium)
                    Text("24d", fontSize = 10.sp, color = TextPrimary, fontWeight = FontWeight.Medium)
                }
                
                // Tooltip Bubble
                Box(
                    modifier = Modifier
                        .offset { 
                            val pointX = maxWidth.toPx() * (2f / 3f)
                            val pointYAbsolute = 20.dp.toPx() + 120.dp.toPx() * 0.6f
                            val tooltipHeight = 36.dp.toPx()
                            val tooltipWidth = 72.dp.toPx()
                            androidx.compose.ui.unit.IntOffset(
                                x = (pointX - tooltipWidth / 2).toInt(), 
                                y = (pointYAbsolute - tooltipHeight - 8.dp.toPx()).toInt()
                            ) 
                        }
                        .background(TextPrimary, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Stability\nimproving", 
                        color = Color.White, 
                        fontSize = 10.sp, 
                        textAlign = TextAlign.Center,
                        lineHeight = 14.sp
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Months row
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                listOf("Jan", "Feb", "Mar", "Apr").forEach {
                    Text(text = it, fontSize = 12.sp, color = TextSecondary)
                }
            }
        }
    }
}

@Composable
fun CycleTrendsCard() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = CardBgColor
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun")
            val lengths = listOf(28f, 30f, 28f, 32f, 28f, 28f)
            val fills = listOf(
                Pair(0.6f, 0.2f), Pair(0.8f, 0.3f), Pair(0.5f, 0.2f),
                Pair(0.9f, 0.1f), Pair(0.6f, 0.15f), Pair(0.5f, 0.1f)
            )

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                val barWidth = 14.dp.toPx()
                val cornerRadius = CornerRadius(barWidth / 2, barWidth / 2)
                val spacing = (size.width - (barWidth * months.size)) / (months.size - 1)
                
                val topPadding = 20.dp.toPx()
                val bottomPadding = 40.dp.toPx()
                val availableHeight = size.height - topPadding - bottomPadding

                for (i in months.indices) {
                    val xPos = i * (barWidth + spacing)
                    
                    // Draw Bar Background
                    drawRoundRect(
                        color = BarBgColor,
                        topLeft = Offset(xPos, topPadding),
                        size = Size(barWidth, availableHeight),
                        cornerRadius = cornerRadius
                    )

                    // Draw Pink Fill (Bottom)
                    val pinkHeight = availableHeight * fills[i].second
                    val pinkTop = topPadding + availableHeight - pinkHeight
                    drawRoundRect(
                        color = BarFillPink,
                        topLeft = Offset(xPos, pinkTop),
                        size = Size(barWidth, pinkHeight),
                        cornerRadius = cornerRadius
                    )

                    // Draw Purple Fill (Top portion of the filled area)
                    val purpleHeight = availableHeight * fills[i].first
                    val purpleTop = topPadding + availableHeight - pinkHeight - purpleHeight
                    drawRoundRect(
                        color = BarFillPurple,
                        topLeft = Offset(xPos, purpleTop),
                        size = Size(barWidth, purpleHeight),
                        cornerRadius = cornerRadius
                    )
                    
                    // Icon placeholder
                    drawCircle(
                        color = Color.White.copy(alpha = 0.5f),
                        radius = barWidth / 3,
                        center = Offset(xPos + barWidth / 2, purpleTop + purpleHeight / 2)
                    )
                }
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                months.forEach { month ->
                    Text(
                        text = month,
                        fontSize = 12.sp,
                        color = TextSecondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun BodyMetabolicTrendsCard() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = CardBgColor
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            var selectedTab by remember { mutableStateOf("Monthly") }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Your weight", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                    Text(text = "in kg", fontSize = 14.sp, color = TextSecondary)
                }
                
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(BgColor)
                        .padding(4.dp)
                ) {
                    val tabs = listOf("Monthly", "Weekly")
                    tabs.forEach { tab ->
                        val isSelected = tab == selectedTab
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) TextPrimary else Color.Transparent)
                                .clickable { selectedTab = tab }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = tab,
                                color = if (isSelected) Color.White else TextSecondary,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            val dataPoints = listOf(50f, 52f, 48f, 65f, 58f, 54f)
            val minWeight = 40f
            val maxWeight = 80f
            
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                val width = size.width
                val height = size.height
                
                val gridY75 = height * (1 - (75f - minWeight) / (maxWeight - minWeight))
                val gridY50 = height * (1 - (50f - minWeight) / (maxWeight - minWeight))
                
                drawLine(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    start = Offset(0f, gridY75),
                    end = Offset(width, gridY75),
                    strokeWidth = 2f,
                    pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )
                drawLine(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    start = Offset(0f, gridY50),
                    end = Offset(width, gridY50),
                    strokeWidth = 2f,
                    pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )
                
                val stepX = width / (dataPoints.size - 1)
                val points = dataPoints.mapIndexed { index, weight ->
                    val x = index * stepX
                    val y = height * (1 - (weight - minWeight) / (maxWeight - minWeight))
                    Offset(x, y)
                }
                
                val linePath = Path().apply {
                    moveTo(points.first().x, points.first().y)
                    for (i in 0 until points.size - 1) {
                        val current = points[i]
                        val next = points[i + 1]
                        val controlX = (current.x + next.x) / 2
                        cubicTo(controlX, current.y, controlX, next.y, next.x, next.y)
                    }
                }
                
                val fillPath = Path().apply {
                    addPath(linePath)
                    lineTo(width, height)
                    lineTo(0f, height)
                    close()
                }
                
                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(ChartGradientStart, ChartGradientEnd),
                        startY = 0f,
                        endY = height
                    )
                )
                
                drawPath(
                    path = linePath,
                    color = ChartLineColor,
                    style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                )
                
                points.forEach { point ->
                    drawCircle(color = Color.White, radius = 4.dp.toPx(), center = point)
                    drawCircle(color = ChartLineColor, radius = 4.dp.toPx(), center = point, style = Stroke(width = 2.dp.toPx()))
                }
            }
        }
    }
}

data class DonutSegment(val label: String, val percent: Int, val color: Color)

@Composable
fun BodySignalsCard() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = CardBgColor
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(text = "Symptom Trends", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            Text(text = "Compared to last cycle", fontSize = 12.sp, color = TextSecondary)
            Spacer(modifier = Modifier.height(32.dp))
            
            Box(modifier = Modifier.fillMaxWidth().height(240.dp), contentAlignment = Alignment.Center) {
                val segments = listOf(
                    DonutSegment("Mood", 30, Color(0xFFFDE4E8)),
                    DonutSegment("Bloating", 31, DonutColor1),
                    DonutSegment("Fatigue", 21, DonutColor2),
                    DonutSegment("Acne", 17, DonutColor3)
                )
                
                // Draw Donut
                Canvas(modifier = Modifier.size(200.dp)) {
                    val strokeWidth = 32.dp.toPx()
                    var startAngle = -90f
                    
                    segments.forEach { segment ->
                        val sweepAngle = (segment.percent / 100f) * 360f
                        drawArc(
                            color = segment.color,
                            startAngle = startAngle,
                            sweepAngle = sweepAngle,
                            useCenter = false,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Butt),
                            size = size
                        )
                        startAngle += sweepAngle
                    }
                }
                
                // Draw Labels
                val radiusDp = 100.dp
                var startAngleForLabel = -90f
                segments.forEach { segment ->
                    val sweepAngle = (segment.percent / 100f) * 360f
                    val midAngle = startAngleForLabel + sweepAngle / 2
                    val angleRad = Math.toRadians(midAngle.toDouble())
                    
                    val offsetX = (Math.cos(angleRad) * radiusDp.value).dp
                    val offsetY = (Math.sin(angleRad) * radiusDp.value).dp
                    
                    Surface(
                        modifier = Modifier
                            .offset(x = offsetX, y = offsetY)
                            .size(56.dp),
                        shape = CircleShape,
                        shadowElevation = 4.dp,
                        color = Color.White
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "${segment.percent}%", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text(text = segment.label, fontSize = 10.sp, color = TextSecondary)
                        }
                    }
                    
                    startAngleForLabel += sweepAngle
                }
            }
        }
    }
}

@Composable
fun LifestyleImpactCard() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = CardBgColor
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Correlation Strength", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                Text(text = "Months ˅", fontSize = 12.sp, color = TextSecondary)
            }
            Spacer(modifier = Modifier.height(24.dp))
            
            val columns = 9
            RowItem("Sleep", LifestylePurple, columns, listOf(0.2f, 0.4f, 0.6f, 0.8f, 1f, 0.4f, 0.3f, 0.2f, 0.1f))
            Spacer(modifier = Modifier.height(12.dp))
            RowItem("Hydration", LifestyleRed, columns, listOf(1f, 0.8f, 0.6f, 0.4f, 0.2f, 0.1f, 0.1f, 0.1f, 0.1f))
            Spacer(modifier = Modifier.height(12.dp))
            RowItem("Caffeine", LifestyleGreen, columns, listOf(0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 0.5f, 0.3f, 0.2f))
            Spacer(modifier = Modifier.height(12.dp))
            RowItem("Exercise", LifestyleGray, columns, listOf(0.2f, 0.2f, 0.3f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f))
        }
    }
}

@Composable
fun RowItem(label: String, color: Color, count: Int, alphas: List<Float>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 12.sp, color = TextSecondary, modifier = Modifier.width(64.dp))
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            for (i in 0 until count) {
                Box(
                    modifier = Modifier
                        .height(16.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(color.copy(alpha = alphas.getOrElse(i) { 0.2f }))
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    Surface(
        color = BottomNavBg,
        shadowElevation = 16.dp,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem("Home", isSelected = false)
            BottomNavItem("Track", isSelected = false)
            BottomNavItem("Insights", isSelected = true)
            
            // FAB Placeholder
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(BgColor),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "+", fontSize = 24.sp, color = TextPrimary)
            }
        }
    }
}

@Composable
fun BottomNavItem(label: String, isSelected: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(if (isSelected) TextPrimary else TextSecondary.copy(alpha = 0.2f))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            color = if (isSelected) TextPrimary else TextSecondary,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InsightsScreenPreview() {
    MaterialTheme {
        InsightsScreen()
    }
}
