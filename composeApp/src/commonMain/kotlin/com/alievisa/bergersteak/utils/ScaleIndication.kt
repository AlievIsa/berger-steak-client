package com.alievisa.bergersteak.utils

import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.Stable
import androidx.compose.ui.node.DelegatableNode

@Stable
object ScaleIndication : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode {
        println("ScaleNode created")
        return ScaleNode(interactionSource)
    }

    override fun equals(other: Any?): Boolean = other === ScaleIndication
    override fun hashCode() = 100
}