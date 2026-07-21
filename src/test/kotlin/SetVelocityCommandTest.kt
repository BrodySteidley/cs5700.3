
import command.RobotActuator
import command.SetVelocityCommand
import kotlin.test.Test
import kotlin.test.assertEquals

class SetVelocityCommandTest {

    private class DummyActuator() : RobotActuator
    {
        override var leftTrackVelocity: Double = 0.0
        override var rightTrackVelocity: Double = 0.0
        override fun setTrackVelocities(left: Double, right: Double)
        {
            leftTrackVelocity = left
            rightTrackVelocity = right
        }
    }

    @Test
    fun `SetVelocityCommand sets velocity`()
    {
        val da = DummyActuator()
        val v = SetVelocityCommand(da, 5.0, 10.0)
        v.execute()
        assertEquals(5.0, da.leftTrackVelocity)
        assertEquals(10.0, da.rightTrackVelocity)
    }

    @Test
    fun `SetVelocityCommand undo sets to previous velocity`()
    {
        val da = DummyActuator()
        da.setTrackVelocities(1.0, 1.0)
        val v = SetVelocityCommand(da, 5.0, 10.0)
        v.execute()
        v.undo()
        assertEquals(1.0, da.leftTrackVelocity)
        assertEquals(1.0, da.rightTrackVelocity)
    }
}