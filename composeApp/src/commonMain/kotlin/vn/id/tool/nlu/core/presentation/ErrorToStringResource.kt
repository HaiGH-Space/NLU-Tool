package vn.id.tool.nlu.core.presentation

import org.jetbrains.compose.resources.stringResource
import vn.id.tool.nlu.core.network.Error
import vn.id.tool.nlu.core.network.NetworkError
import vn.id.tool.nlu.core.network.NetworkErrorExt
import vn.id.tool.nlu.res.Res
import vn.id.tool.nlu.res.error_no_internet
import vn.id.tool.nlu.res.error_request_time_out
import vn.id.tool.nlu.res.error_serialization
import vn.id.tool.nlu.res.error_too_many_request
import vn.id.tool.nlu.res.error_unknown

fun Error.toUiText(): UiText {
    return when(this) {
        is NetworkError -> this.toUiText()
        is NetworkErrorExt -> UiText.DynamicString(this.message)
        else -> UiText.StringResourceId(Res.string.error_unknown)
    }
}
fun Error.toText(): String {
    return when(this) {
        is NetworkErrorExt -> this.message
        else -> ""
    }
}
fun NetworkError.toUiText(): UiText {
    val stringRes = when(this) {
        NetworkError.REQUEST_TIMEOUT -> Res.string.error_request_time_out
        NetworkError.UNAUTHORIZED -> Res.string.error_unknown
        NetworkError.CONFLICT -> Res.string.error_unknown
        NetworkError.TOO_MANY_REQUESTS -> Res.string.error_too_many_request
        NetworkError.NO_INTERNET -> Res.string.error_no_internet
        NetworkError.PAYLOAD_TOO_LARGE -> Res.string.error_unknown
        NetworkError.SERVER_ERROR -> Res.string.error_unknown
        NetworkError.SERIALIZATION ->Res.string.error_serialization
        NetworkError.UNKNOWN -> Res.string.error_unknown
    }
    return UiText.StringResourceId(stringRes)
}