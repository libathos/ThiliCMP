package compose.thili.demo.sharemanager

import androidx.compose.runtime.Composable

actual class ShareManager {
    actual fun share(text: String){
        //TODO("Not yet implemented")
    }
}

@Composable
actual fun rememberShareManager(): ShareManager {
    return ShareManager()
}