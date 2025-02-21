package compose.thili.demo.sharemanager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ShareManager {

    actual fun share(text:String) {
        val activityViewController = UIActivityViewController(listOf(text), null)
        UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
            activityViewController, animated = true, completion = null
        )
    }
}

@Composable
actual fun rememberShareManager(): ShareManager {
    return remember { ShareManager() }
}