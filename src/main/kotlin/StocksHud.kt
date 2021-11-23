import com.google.gson.Gson
import com.lambda.client.LambdaMod
import com.lambda.client.util.TickTimer
import com.lambda.client.util.TimeUnit
import com.lambda.client.util.WebUtils
import com.lambda.client.util.text.MessageSendHelper
import com.lambda.client.event.SafeClientEvent
import com.lambda.client.plugin.api.PluginLabelHud
import com.lambda.client.util.threads.defaultScope
import kotlinx.coroutines.launch

internal object StocksHud: PluginLabelHud(
    name = "Stocks",
    category = Category.MISC,
    description = "Live updating stock price",
    pluginMain = PluginStocks
) {
    private var symbol by setting("Symbol", "TSLA")
    private val tickDelay by setting("Delay", 30, 20..120, 1)
    private var token by setting("Token", "Set your token with the command ;set Stocks Token (token)")
    private val tickTimer = TickTimer(TimeUnit.SECONDS)
    private var url = "https://finnhub.io/api/v1/quote?symbol=$symbol&token=$token"
    private var price = 0.0
    private var sentWarning = false

    override fun SafeClientEvent.updateText() {
        if (!sentWarning) {
            sendWarning()
        }

        if (tickTimer.tick(tickDelay) && !token.startsWith("Set")) {
            defaultScope.launch {
                try {
                    url = "https://finnhub.io/api/v1/quote?symbol=${symbol.uppercase()}&token=$token"
                    price = Gson().fromJson(WebUtils.getUrlContents(url), StockData::class.java).c
                } catch (e: Exception) {
                    LambdaMod.LOG.error("Failed to connect to finnhub api", e)
                }
            }
        }
        displayText.add("Current Price of ${symbol.uppercase()} is", primaryColor)
        displayText.add("$price", secondaryColor)
    }


    private fun sendWarning() {
        MessageSendHelper.sendWarningMessage(
            "This module uses an external API, finnhub.io, which requires an api token to use. " +
                "Go to https://finnhub.io/dashboard and sign up for an account and copy your api token. " +
                "Once you have gotten your api token, you can run this command: " +
                ";set Stocks Token (paste token here)"
        )
        sentWarning = true
    }

    private class StockData(
        val c: Double
    )
}