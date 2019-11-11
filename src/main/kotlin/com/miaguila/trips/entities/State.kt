package com.miaguila.trips.entities

import com.fasterxml.jackson.annotation.JsonProperty

enum class State private constructor(state: String) {
    @JsonProperty("onWay")
    onWay("onWay"),
    @JsonProperty("near")
    near("near"),
    @JsonProperty("started")
    started("started");

    private val state: String

    init {
        this.state = state
    }
}