package com.miaguila.trips.utils

import com.google.common.io.Resources
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component

@Component
class CustomFileManager {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var resourceLoader: ResourceLoader

    val map: HashMap<String, String> = hashMapOf()

    fun resolveSQL(fileName: String): String {
        var query = map[fileName]
        if (query == null) {
            logger.info("Caching query: {}", fileName)
            query = Resources.toString(resourceLoader.getResource(fileName).url, Charsets.UTF_8)!!
            map[fileName] = query
        }

        return query
    }
}
