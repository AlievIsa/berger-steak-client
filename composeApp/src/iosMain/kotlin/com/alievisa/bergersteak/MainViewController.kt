package com.alievisa.bergersteak

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import io.ktor.client.engine.darwin.Darwin
import platform.UIKit.UIRectEdgeBottom
import platform.UIKit.UIRectEdgeTop
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    val controller = ComposeUIViewController {
        App(
            baseUrl = "http://127.0.0.1:8080",
            engine = remember { Darwin.create() },
        )
    }

    controller.edgesForExtendedLayout = UIRectEdgeTop or UIRectEdgeBottom
    controller.extendedLayoutIncludesOpaqueBars = true

    return controller
}