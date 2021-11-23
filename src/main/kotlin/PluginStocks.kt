import com.lambda.client.plugin.api.Plugin

internal object PluginStocks: Plugin() {

    override fun onLoad() {
        // Load any modules, commands, or HUD elements here
        hudElements.add(StocksHud)
    }

    override fun onUnload() {
        // Here you can unregister threads etc...
    }
}
