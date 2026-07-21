package command

/**
 * The Invoker. It runs commands and keeps an undo/redo history — this is yours to implement.
 *
 * Until you implement these, performing a command (from a button or a program) does nothing.
 */
class CommandInvoker {
    private val undoStack = ArrayDeque<Command>()
    private val redoStack = ArrayDeque<Command>()

    fun run(command: Command) {
        command.execute()
        undoStack.addLast(command)
        redoStack.clear()
    }

    fun undo() {
        if (canUndo()) {
            val command: Command = undoStack.removeLast()
            command.undo()
            redoStack.addLast(command)
        }
    }

    fun redo() {
        if (canRedo()) {
            val command: Command = redoStack.removeLast()
            command.execute()
            undoStack.addLast(command)
        }
    }

    fun canUndo() = undoStack.isNotEmpty()
    fun canRedo() = redoStack.isNotEmpty()
}
