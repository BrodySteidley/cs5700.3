package command


class TurnHardRightCommand(private val actuator : RobotActuator) : SetVelocityCommand(actuator, 100.0, -100.0)

