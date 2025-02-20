package compose.thili.demo.sharemanager

import androidx.compose.runtime.Composable


expect class ShareManager {
    fun share(text:String)
}


@Composable
expect fun rememberShareManager(): ShareManager
