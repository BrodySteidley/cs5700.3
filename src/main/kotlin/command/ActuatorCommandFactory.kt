package command
import api.RobotApi

object ActuatorCommandFactory
{
	fun performSetVelocityCommand(robot : RobotApi,  left : Double,  right : Double) = robot.perform(SetVelocityCommand(robot.actuator, left, right))
	fun performForwardCommand( robot : RobotApi) = robot.perform(ForwardCommand(robot.actuator))
	fun performTurnSoftRightCommand( robot : RobotApi) = robot.perform(TurnSoftRightCommand(robot.actuator))
	fun performTurnHardRightCommand( robot : RobotApi) = robot.perform(TurnHardRightCommand(robot.actuator))
	fun performStopCommand( robot : RobotApi) = robot.perform(StopCommand(robot.actuator))
}
