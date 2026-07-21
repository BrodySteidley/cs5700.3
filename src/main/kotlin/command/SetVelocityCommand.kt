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
    override fun execute()
    {
	    prevLeft = left
	    prevRight = right
	    actuator.setTrackVelocities(left, right);
    }
    override fun undo()
    {
	    actuator.setTrackVelocities(-left, -right);
    }
}
