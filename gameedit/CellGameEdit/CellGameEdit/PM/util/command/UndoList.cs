using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CellGameEdit.PM.util.command
{
    public class UnDoRedo
    {
        private Stack<ICommand> _Undocommands = new Stack<ICommand>();
        private Stack<ICommand> _Redocommands = new Stack<ICommand>();

        public void redo(int levels)
        {
            for (int i = 1; i <= levels; i++)
            {
                if (_Redocommands.Count != 0)
                {
                    ICommand command = _Redocommands.Pop();
                    command.Execute();
                    _Undocommands.Push(command);
                }

            }
        }

        public void undo(int levels)
        {
            for (int i = 1; i <= levels; i++)
            {
                if (_Undocommands.Count != 0)
                {
                    ICommand command = _Undocommands.Pop();
                    command.UnExecute();
                    _Redocommands.Push(command);
                }

            }
        }

        public void add(ICommand cmd)
        {
            _Undocommands.Push(cmd); 
            _Redocommands.Clear();
        }
    }

    public interface ICommand
    {
        void Execute();
        void UnExecute();
    }
}
