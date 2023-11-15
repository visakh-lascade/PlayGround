package com.test.pseudoid

import com.google.gson.annotations.SerializedName

data class CountriesResponse(

	@field:SerializedName("features")
	val features: List<FeaturesItem?>? = null,

	@field:SerializedName("type")
	val type: String? = null
)

data class Geometry(

	@field:SerializedName("coordinates")
	val coordinates: List<List<List<Any?>?>?>? = null,

	@field:SerializedName("type")
	val type: String? = null
)

data class Properties(

	@field:SerializedName("name")
	val name: String? = null
)

data class FeaturesItem(

	@field:SerializedName("geometry")
	val geometry: Geometry? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("properties")
	val properties: Properties? = null
)
