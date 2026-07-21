package command


open class SetVelocityCommand(
	private val actuator : RobotActuator,
	private val left : Double,
	private val right : Double,
) : Command {
	private var prevLeft : Double = 0.0;
	private var prevRight : Double = 0.0;

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
