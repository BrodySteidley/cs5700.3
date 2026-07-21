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
class FindHeatProgram() : RobotProgram {
    override val name: String = "Find Heat"

    override fun startProgram(robot: RobotApi)
    {
	    robot.sensors.lineCenter.subscribe(
		Observer<Boolean> { seesLine -> robot.perform(SetVelocityCommand(robot.actuator, 50.0, 50.0)) }
	    )
	    robot.sensors.lineRight.subscribe(
		Observer<Boolean> { seesLine -> if (!seesLine) robot.perform(SetVelocityCommand(robot.actuator, 0.0, 90.0)) }
	    )
    }

    override fun stopProgram(robot: RobotApi)
    {
	    //robot.lineLeft.unsubscribe(onLeft)
    }
}
