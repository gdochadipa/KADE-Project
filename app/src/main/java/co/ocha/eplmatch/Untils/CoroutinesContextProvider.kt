package co.ocha.eplmatch.Untils

import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

open class CoroutinesContextProvider{
    open val main: CoroutineContext by lazy { UI }
}