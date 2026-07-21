package command


class StopCommand(private val actuator : RobotActuator) : SetVelocityCommand(actuator, 0.0, 0.0)

