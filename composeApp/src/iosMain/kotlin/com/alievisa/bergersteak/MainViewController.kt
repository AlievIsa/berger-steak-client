package com.alievisa.bergersteak

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIRectEdgeBottom
import platform.UIKit.UIRectEdgeTop
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    val controller = ComposeUIViewController { App() }

    controller.edgesForExtendedLayout = UIRectEdgeTop or UIRectEdgeBottom
    controller.extendedLayoutIncludesOpaqueBars = true

    return controller
}