package no.fintlabs.linkwalker.client

import lombok.Builder
import lombok.Getter
import lombok.Setter

class Client {

    val dn: String? = null
    val name: String? = null
    val isManaged = false
    val shortDescription: String? = null
    val assetId: String? = null
    val asset: String? = null
    val note: String? = null
    val password: String? = null
    val clientSecret: String? = null
    val publicKey: String? = null
    val clientId: String? = null
    val components: MutableList<String> = ArrayList()
    val accessPackages: MutableList<String> = ArrayList()

    fun addComponent(componentDn: String) {
        if (components.stream().noneMatch { anotherString: String? ->
                componentDn.equals(
                    anotherString,
                    ignoreCase = true
                )
            }) {
            components.add(componentDn)
        }
    }

    fun removeComponent(componentDn: String?) {
        components.removeIf { component: String ->
            component.equals(
                componentDn,
                ignoreCase = true
            )
        }
    }

    fun setAccessPackage(accessPackageDn: String) {
        accessPackages.clear()
        accessPackages.add(accessPackageDn)
    }

}