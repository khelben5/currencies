package com.eduardodev.currencies.data.extension

import com.eduardodev.currencies.data.network.RatesEntity
import com.eduardodev.currencies.presentation.model.Rate
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaField


fun RatesEntity.getRates(): List<Rate> = this::class.declaredMemberProperties.map {
    val annotations = it.javaField?.declaredAnnotations
    val gsonAnnotation = annotations?.firstOrNull { annotation ->
        annotation is SerializedName
    } as? SerializedName
    val serializedName = gsonAnnotation?.value
    Rate(
            Currency.getInstance(serializedName),
            it.call(this) as Double
    )
}