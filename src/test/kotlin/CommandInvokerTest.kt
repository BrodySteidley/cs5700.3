import command.Command
import command.CommandInvoker
import kotlin.test.Test
import kotlin.test.assertEquals

class CommandInvokerTest {
    private class DummyCommand(var state: Int = 0) : Command {
        override fun execute() {
            state = 1
        }

        override fun undo() {
            state = 2
        }
    }

    @Test
    fun `run executes command`() {
        val c = CommandInvoker()
        val dc = DummyCommand()
        c.run(dc)
        assertEquals(1, dc.state)
    }

    @Test
    fun `run updates undo stack`() {
        val c = CommandInvoker()

        assertEquals(false, c.canUndo());
        c.run(DummyCommand())
        assertEquals(true, c.canUndo());
    }

    @Test
    fun `run clears redo stack`() {
        val c = CommandInvoker()
        c.run(DummyCommand())
        c.run(DummyCommand())
        c.undo()
        c.undo()
        assertEquals(true, c.canRedo())
        c.run(DummyCommand())
        assertEquals(false, c.canRedo())
    }

    @Test
    fun `undo calls undo on command`() {
        val c = CommandInvoker()
        val dc = DummyCommand()
        c.run(dc)
        c.undo()
        assertEquals(2, dc.state)
    }

    @Test
    fun `undo updates redo stack`() {
        val c = CommandInvoker()
        c.run(DummyCommand())
        c.undo()
        assertEquals(true, c.canRedo())
    }

    @Test
    fun `undo calls run on command`() {
        val c = CommandInvoker()
        val dc = DummyCommand()
        c.run(dc)
        c.undo()
        c.redo()
        assertEquals(1, dc.state)
    }

    @Test
    fun `redo updates undo stack`() {
        val c = CommandInvoker()
        c.run(DummyCommand())
        c.undo()
        assertEquals(false, c.canUndo())
        c.redo()
        assertEquals(true, c.canUndo())
    }
}