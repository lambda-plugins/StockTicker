import com.lambda.client.event.SafeClientEvent
import com.lambda.client.module.Category
import com.lambda.client.plugin.api.PluginModule
import com.lambda.client.util.text.MessageSendHelper.sendChatMessage
import com.lambda.client.util.threads.onMainThreadSafe
import kotlinx.coroutines.runBlocking
import net.minecraft.entity.Entity
import net.minecraft.network.play.client.CPacketUseEntity
import net.minecraft.util.EnumHand

internal object GridWalk: PluginModule(
    name = "GridWalk",
    category = Category.MOVEMENT,
    description = "annoying",
    pluginMain = PluginExample
) {
    private enum class AutoWalkMode(override val displayName: String) : DisplayEnum {
    FORWARD("Forward"),
    BACKWARD("Backward")
    }
    init {
        listener<ConnectionEvent.Disconnect> {
            if (disableOnDisconnect) disable()
                listener<InputUpdateEvent>(6969) {
            if (LagNotifier.paused && LagNotifier.pauseAutoWalk) return@listener

            if (it.movementInput !is MovementInputFromOptions) return@listener

            when (mode.value.displayName) {
                AutoWalkMode.FORWARD -> {
                    it.movementInput.moveForward = 1.0f
                    }    
                }
            }
        }    
    }
}
