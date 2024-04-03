package no.fintlabs.linkwalker.client

class Client(
    val dn: String? = null,
    val name: String? = null,
    val isManaged: Boolean = false,
    val shortDescription: String? = null,
    val assetId: String? = null,
    val asset: String? = null,
    val note: String? = null,
    val password: String? = null,
    val clientSecret: String? = null,
    val publicKey: String? = null,
    val clientId: String? = null,
    val components: MutableList<String> = mutableListOf(),
    val accessPackages: MutableList<String> = mutableListOf()
) {

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