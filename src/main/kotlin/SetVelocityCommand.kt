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
	val prevLeft : Double
	val prevRight : Double
    fun execute()
    {
	    prevLeft = left
	    prevRight = right
	    actuator.setTrackVelocities(left, right);
    }
    fun undo()
    {
	    actuator.setTrackVelocities(prevLeft, p:evRight);
    }
}
