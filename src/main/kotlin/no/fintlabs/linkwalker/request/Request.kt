package no.fintlabs.linkwalker.request

data class Link(val href: String)

data class Entry(val _links: Map<String, List<Link>>)

data class Embedded(val _entries: List<Entry>)

data class Request(val _embedded: Embedded, val total_items: Int)
