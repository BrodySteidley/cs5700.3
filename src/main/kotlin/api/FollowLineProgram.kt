package api

import observer.Observer
import command.SetVelocityCommand

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
class FollowLineProgram() : RobotProgram {
    override val name: String = "Follow Line"

    private lateinit var onLeft: (Boolean) -> Unit
    private lateinit var onRight: (Boolean) -> Unit

    override fun startProgram(robot: RobotApi)
    {
	    onLeft = { robot.perform(SetVelocityCommand(robot.actuator, if (it) 100.0 else 10.0, robot.actuator.rightTrackVelocity)) }
	    onRight = { robot.perform(SetVelocityCommand(robot.actuator, robot.actuator.leftTrackVelocity, if (it) 100.0 else 10.0)) }
	    
	    robot.sensors.lineLeft.subscribe(onLeft)
	    robot.sensors.lineRight.subscribe(onRight)
    }

    override fun stopProgram(robot: RobotApi)
    {
	    robot.sensors.lineLeft.unsubscribe(onLeft)
	    robot.sensors.lineRight.unsubscribe(onRight)
	    robot.perform(SetVelocityCommand(robot.actuator, 0.0, 0.0))
    }
}
