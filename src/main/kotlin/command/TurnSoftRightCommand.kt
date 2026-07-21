package command


class TurnSoftRightCommand(private val actuator : RobotActuator) : SetVelocityCommand(actuator, 100.0, -30.0)

