package api

import observer.Observer
import command.SetVelocityCommand
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
class FindHeatProgram() : AbstractProgram() {
    override val name: String = "Find Heat"

    override fun createSensorSubscriptions(robot : RobotApi) : List<SensorSubscription<*>>
    {
	    var searching : Boolean = false
	    var honing : Boolean = false
	    /* moving = !searching && !honing */

	    var count : Int = 0
	    var maxTemp : Double = 0.0
	    var tooCloseToObstacle : Boolean = false;
	    val onTemperature = Observer<Double> { 
		    if (searching)
		    {
			    if (count >= 100)
			    {
				searching = false
				honing = true
				count = 0
			    }
			    else
			    {
				    if (it > maxTemp && !tooCloseToObstacle)
					maxTemp = it

				    count++
			    }
		    }
		    else if (honing)
		    {
			    if (it >= maxTemp)
				    honing = false;
		    }
		    else /* moving */
		    {
			    if (count >= 100 || tooCloseToObstacle)
			    {
				    maxTemp = 0.0
				    count = 0
				    searching = true
				    robot.perform(SetVelocityCommand(robot.actuator, 100.0, -30.0))
			    }
			    else
			    {
				    count++;
				    robot.perform(SetVelocityCommand(robot.actuator, 100.0, 100.0))
			    }
		    }
	    }

	    val onSonar = Observer<Double> { tooCloseToObstacle = (it <= 100.0) }

	    return listOf(
		    SensorSubscription<Double>(robot.sensors.temperature, onTemperature),
		    SensorSubscription<Double>(robot.sensors.sonar, onSonar)
	    )
    }
}
