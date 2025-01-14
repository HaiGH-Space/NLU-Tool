package vn.id.tool.nlu.api.model

import kotlinx.serialization.Serializable

@Serializable
data class FilteringCondition(val gia_tri: String, val mieu_ta: String, val is_mac_dinh: String) {
    override fun toString(): String {
        return "FilteringCondition{" +
                "gia_tri='" + gia_tri + '\'' +
                ", mieu_ta='" + mieu_ta + '\'' +
                ", is_mac_dinh='" + is_mac_dinh + '\'' +
                '}'
    }
}