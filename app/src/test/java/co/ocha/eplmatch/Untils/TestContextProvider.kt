package co.ocha.eplmatch.Untils

import kotlinx.coroutines.experimental.Unconfined
import org.junit.Assert.*
import kotlin.coroutines.experimental.CoroutineContext

class TestContextProvider: CoroutinesContextProvider(){
    override val main: CoroutineContext = Unconfined
}