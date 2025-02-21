package compose.thili.demo.sharemanager

import androidx.compose.runtime.Composable


@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class ShareManager {
    fun share(text:String)
}


@Composable
expect fun rememberShareManager(): ShareManager
