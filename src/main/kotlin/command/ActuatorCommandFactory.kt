package command

object ActuatorCommandFactory()
{
	fun performSetVelocityCommand(val robot : RobotApi, val left : Double, val right : Double) = robot.perform(SetVelocityCommand(robot.actuator, left, right))
	fun performForwardCommand(val robot : RobotApi) = robot.perform(ForwardCommand(robot.actuator))
	fun performTurnSoftRightCommand(val robot : RobotApi) = robot.perform(TurnSoftRightCommand(robot.actuator))
	fun performTurnHardRightCommand(val robot : RobotApi) = robot.perform(TurnHardRightCommand(robot.actuator))
	fun performStopCommand(val robot : RobotApi) = robot.perform(StopCommand(robot.actuator))
}
