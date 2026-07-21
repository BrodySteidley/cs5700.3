package api

import observer.Observer
import command.ActuatorCommandFactory
import sensor.Sensor

/**
 * A program that drives the robot. Students implement this.
 *
 * The program is an Observer: in [startProgram] it subscribes to whichever [RobotApi.sensors] it
 * needs and issues commands (via the [RobotApi]) in response to their readings; in [stopProgram]
 * it unsubscribes and typically stops the robot. There is no per-tick callback — a subscribed
 * sensor notifies every tick, so the sensor stream is the program's control loop.
 *
 * Register an instance with a [ProgramRegistry] to make it selectable in the UI's program dropdown.
 */
abstract class AbstractProgram() : RobotProgram {
    abstract override val name: String

    protected class SensorSubscription<T>(private val sensor: Sensor<T>, private val observer: Observer<T>) {
        fun subscribe() = sensor.subscribe(observer)
        fun unsubscribe() = sensor.unsubscribe(observer)
    }

    private val robotSubscriptions = mutableMapOf<RobotApi, List<SensorSubscription<*>>>()

    protected abstract fun createSensorSubscriptions(robot: RobotApi): List<SensorSubscription<*>>

    override fun startProgram(robot: RobotApi) {
        if (robotSubscriptions.containsKey(robot))
            return

        val sensorSubscriptions = createSensorSubscriptions(robot)

        for (sensorSubscription in sensorSubscriptions)
            sensorSubscription.subscribe()

        robotSubscriptions[robot] = sensorSubscriptions
    }

    override fun stopProgram(robot: RobotApi) {
        val robotSubscription = robotSubscriptions[robot]
        if (robotSubscription != null) {
            for (sensorSubscription in robotSubscription)
                sensorSubscription.unsubscribe()

            ActuatorCommandFactory.performStopCommand(robot)
            robotSubscriptions.remove(robot)
        }

    }
}
