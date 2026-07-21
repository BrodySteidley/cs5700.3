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
class FollowLineProgram() : AbstractProgram() {
    override val name: String = "Follow Line"

    override fun createSensorSubscriptions(robot : RobotApi) : List<SensorSubscription<*>>
    {
	    val onLeft  = Observer<Boolean> { ActuatorCommandFactory.performSetVelocityCommand(robot, if (it) 100.0 else 10.0, robot.actuator.rightTrackVelocity) }
	    val onRight = Observer<Boolean> { ActuatorCommandFactory.performSetVelocityCommand(robot, robot.actuator.leftTrackVelocity, if (it) 100.0 else 10.0) }

	    return listOf(
		    SensorSubscription<Boolean>(robot.sensors.lineLeft, onLeft),
		    SensorSubscription<Boolean>(robot.sensors.lineRight, onRight)
	    )
    }
}
