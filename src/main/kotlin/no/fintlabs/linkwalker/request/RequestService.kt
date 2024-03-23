package no.fintlabs.linkwalker.request

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class RequestService(val webClient: WebClient) {

    fun get(url: String, token: String): Mono<Request> {
        return webClient.get()
                .uri(url)
                .headers { headers -> headers.setBearerAuth(token) }
                .retrieve()
                .bodyToMono(Request::class.java)
    }

}