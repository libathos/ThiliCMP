package compose.thili.demo.sharemanager

import androidx.compose.runtime.Composable
import java.awt.Desktop
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import java.net.URI


@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ShareManager {
    actual fun share(text: String) {
        val clipboard: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
        val stringSelection = StringSelection(text)
        clipboard.setContents(stringSelection, null)
        openWebpage(URI("http://www.facebook.com"))
    }

    private fun openWebpage(uri: URI?): Boolean {
        val desktop = if (Desktop.isDesktopSupported()) Desktop.getDesktop() else null
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri)
                return true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return false
    }
}

@Composable
actual fun rememberShareManager(): ShareManager {
    return ShareManager()
}

