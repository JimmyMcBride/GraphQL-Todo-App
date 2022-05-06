package com.fireninja.lib_graphql.domain.models

data class Task(
  val id: Int,
  var title: String,
  var description: String?,
  val completed: Boolean?,
)
