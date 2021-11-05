import baritone.api.pathing.goals.GoalXZ
import com.lambda.client.event.SafeClientEvent
import com.lambda.client.event.events.BaritoneCommandEvent
import com.lambda.client.event.events.ConnectionEvent
import com.lambda.client.module.Category
import com.lambda.client.module.Module
import com.lambda.client.module.modules.player.LagNotifier
import com.lambda.client.util.BaritoneUtils
import com.lambda.client.util.TickTimer
import com.lambda.client.util.TimeUnit
import com.lambda.client.util.math.Direction
import com.lambda.client.util.text.MessageSendHelper
import com.lambda.client.util.threads.runSafe
import com.lambda.client.util.threads.safeListener
import com.lambda.commons.extension.floorToInt
import com.lambda.commons.interfaces.DisplayEnum
import com.lambda.event.listener.listener
import net.minecraft.util.MovementInputFromOptions
import net.minecraftforge.client.event.InputUpdateEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

internal object ModuleExample: PluginModule(
    name = "PluginModule",
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
            if ((LagNotifier.paused && LagNotifier.pauseAutoWalk)
                  || it.movementInput !is MovementInputFromOptions) return@listener
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
