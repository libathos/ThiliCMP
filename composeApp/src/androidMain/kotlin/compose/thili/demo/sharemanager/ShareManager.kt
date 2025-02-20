package compose.thili.demo.sharemanager

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ShareManager(private val context: Context) {

    actual fun share(text:String){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        context.startActivity(Intent.createChooser(intent, "Share with"))
    }

}

@Composable
actual fun rememberShareManager(): ShareManager {
    val context = LocalContext.current
    return  remember {
        ShareManager(context)
    }

}