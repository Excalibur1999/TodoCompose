package com.example.todocompose.data.models


import androidx.compose.ui.graphics.Color
import com.example.todocompose.ui.theme.HighPriorityColor
import com.example.todocompose.ui.theme.LowPriorityColor
import com.example.todocompose.ui.theme.MediumPriorityColor
import com.example.todocompose.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}
//sealed class Priority(val name:String,val color: Color){
//    class HIGH:Priority("HIGH",HighPriorityColor)
//   class MEDIUM:Priority("MEDIUM",MediumPriorityColor)
//    class LOW:Priority("LOW",LowPriorityColor)
//   class NONE:Priority("NONE",NonePriorityColor)
//}
