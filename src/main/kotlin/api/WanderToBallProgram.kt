package api

import observer.Observer
import command.ActuatorCommandFactory
import sensor.Sensor
import javafx.scene.paint.Color

import kotlin.random.Random

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
class WanderToBallProgram() : AbstractProgram() {
    override val name: String = "Wander to ball"

    override fun createSensorSubscriptions(robot : RobotApi) : List<SensorSubscription<*>>
    {
	    var searchedForBall : Boolean = false 
	    var count : Int = 0
	    var searchTime : Int = 0
	    var colliding : Boolean = false

	    val onCollide = Observer<Boolean> { 
		    colliding = it

		    if (!searchedForBall)
		    {
			    if (count < searchTime)
			    	count++
			    else
			    {
			        ActuatorCommandFactory.performForwardCommand(robot)
			    	searchedForBall = true
			    }
		    }
		    else if (colliding)
		    {
			    ActuatorCommandFactory.performTurnHardRightCommand(robot)
			    count = 0
			    searchTime = Random.nextInt(70, 140)
			    searchedForBall = false
		    }
	    }

	    val onVision = Observer<Color> {
		    if (it == Color.web("0xe5342bff"))
		    {
			    if (!colliding)
			    {
			        ActuatorCommandFactory.performForwardCommand(robot)
				searchedForBall = true
			    }
		    }
	    }

	    return listOf(
		    SensorSubscription<Boolean>(robot.sensors.collision, onCollide),
		    SensorSubscription<Color>(robot.sensors.vision, onVision)
	    )
    }
}
