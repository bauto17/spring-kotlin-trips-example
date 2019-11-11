package com.miaguila.trips.entities

class Page(val pageNumber: Int, val url: String) {
    companion object {
        fun new(pages: Map<Int, Long>, params: TripSearchParams): List<Page> {
            val pagesValue = pages.map{"${it.key}_${it.value}"} .joinToString(",")
            return pages.map { Page(it.key,"${params.url}?page=${it.key}&index=$pagesValue${params.getGetParams()}") }
        }
    }
}