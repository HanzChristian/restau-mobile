package com.example.restau.Model.JenisMenu

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class JenisMenuResponseDeserializer : JsonDeserializer<JenisMenuResponse> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): JenisMenuResponse {
        val jsonObject = json.asJsonObject

        val dataElement = jsonObject.get("data")
        val data: List<JenisMenu> = if (dataElement.isJsonArray) {
            context.deserialize(dataElement, object : TypeToken<List<JenisMenu>>() {}.type)
        } else {
            listOf(context.deserialize(dataElement, JenisMenu::class.java))
        }

        return JenisMenuResponse(data)
    }
}