package no.fintlabs.linkwalker.request

import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class RequestService(val webClient: WebClient) {

    fun getStatus(url: String, token: String): Mono<HttpStatusCode> {
        return setUrlAndHeader(url, token).exchangeToMono {
            Mono.just(it.statusCode())
        }
    }

    fun getRequest(url: String, token: String): Mono<Request> {
        return setUrlAndHeader(url, token)
                .retrieve()
                .bodyToMono(Request::class.java)
    }

    private fun setUrlAndHeader(url: String, token: String): WebClient.RequestHeadersSpec<*> {
        return webClient.get()
                .uri(url)
                .headers { headers -> headers.setBearerAuth(token) }
    }

}