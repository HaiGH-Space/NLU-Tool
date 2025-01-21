package vn.id.tool.nlu.data.nlu.network

object RequestUtil {
    var root: String = Constant.getInstance().root
    var prefix: String = Constant.getInstance().prefix
    fun getRequestURLFromParams(root: String, prefix: String, api: String): String {
        return root + prefix + api
    }

    fun requestWithAPI(api: String): String {
        return prefix + api
    }

    fun baseUrl(): String {
        return root + prefix
    }

    fun requestWithAPIUrlPublic(api: String): String {
        return "$root/public/api/$api"
    }

    fun requestWithAPIUrlDKMH(api: String): String {
        return "$root/dkmh/api/$api"
    }

    fun requestWithAPIUrl(api: String): String {
        return root + prefix + api
    }

}