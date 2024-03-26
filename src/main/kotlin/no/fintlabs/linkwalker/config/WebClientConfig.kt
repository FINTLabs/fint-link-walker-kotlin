package no.fintlabs.linkwalker.config

import io.netty.channel.ChannelOption
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.time.Duration

@Configuration
class WebClientConfig {

    @Bean
    fun webClient(): WebClient {
        return WebClient.builder()
                .clientConnector(createClientConnector())
                .exchangeStrategies(createExchangeStratergy())
                .build()
    }

    fun createClientConnector(): ReactorClientHttpConnector {
        return ReactorClientHttpConnector(HttpClient.create(
                ConnectionProvider
                        .builder("laidback")
                        .maxConnections(100)
                        .pendingAcquireMaxCount(-1)
                        .pendingAcquireTimeout(Duration.ofMinutes(15))
                        .maxLifeTime(Duration.ofMinutes(30))
                        .maxIdleTime(Duration.ofMinutes(5))
                        .build())
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 900000)
                .responseTimeout(Duration.ofMinutes(10))
        )
    }

    fun createExchangeStratergy(): ExchangeStrategies {
        return ExchangeStrategies.builder()
                .codecs { configurer -> configurer.defaultCodecs().maxInMemorySize(-1) }
                .build()
    }

}