package command

/**
 * The Command pattern: an action encapsulated as an object. [execute] performs it;
 * [undo] reverses whatever state [execute] changed.
 */
class SetVelocityCommand(
	val actuator : RobotActuator,
	val left : Double,
	val right : Double,
) : Command {
	var prevLeft : Double = 0.0;
	var prevRight : Double = 0.0;

    override fun execute()
    {
	    prevLeft = actuator.leftTrackVelocity
	    prevRight = actuator.rightTrackVelocity
	    actuator.setTrackVelocities(left, right);
    }
    override fun undo()
    {
	    actuator.setTrackVelocities(prevLeft, prevRight);
    }
}
