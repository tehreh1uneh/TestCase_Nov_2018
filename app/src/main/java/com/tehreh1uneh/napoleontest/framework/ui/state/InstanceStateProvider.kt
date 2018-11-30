package com.tehreh1uneh.napoleontest.framework.ui.state

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable
import java.util.*
import kotlin.reflect.KProperty

const val SAVABLE_STATE_DEFAULT_NAME = "savable_state_default_name"

/**
 * Implementation of property delegation. Allows to save properties to [Bundle] using its names as a keys.
 */
open class InstanceStateProvider<T>(protected val bundle: Bundle) {

    protected var cache: T? = null

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        cache = value
        if (value == null) return
        when (value) {
            is Boolean -> bundle.putBoolean(property.name, value)
            is Int -> bundle.putInt(property.name, value)
            is Long -> bundle.putLong(property.name, value)
            is Float -> bundle.putFloat(property.name, value)
            is String -> bundle.putString(property.name, value)
            is Bundle -> bundle.putBundle(property.name, value)
            is IntArray -> bundle.putIntArray(property.name, value)
            is LongArray -> bundle.putLongArray(property.name, value)
            is Parcelable -> bundle.putParcelable(property.name, value)
            else -> {
                when {
                    value is List<*> && value.firstOrNull() is Parcelable -> {
                        @Suppress("UNCHECKED_CAST")
                        val parcelables = value as List<Parcelable>
                        bundle.putParcelableArrayList(property.name, ArrayList(parcelables))
                    }
                    value is Array<*> && value.firstOrNull() is Parcelable -> {
                        @Suppress("UNCHECKED_CAST")
                        val parcelables = value as Array<Parcelable>
                        bundle.putParcelableArray(property.name, parcelables)
                    }
                    value is Serializable -> bundle.putSerializable(property.name, value)
                }
            }
        }
    }

    class Nullable<T>(savable: Bundle) : InstanceStateProvider<T>(savable) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
            if (cache != null) return cache
            if (!bundle.containsKey(property.name)) return null
            @Suppress("UNCHECKED_CAST")
            return bundle.get(property.name) as? T
        }
    }

    class NotNull<T>(savable: Bundle, private val defaultValue: T) : InstanceStateProvider<T>(savable) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
            @Suppress("UNCHECKED_CAST")
            return cache ?: bundle.get(property.name) as? T ?: defaultValue
        }
    }
}
