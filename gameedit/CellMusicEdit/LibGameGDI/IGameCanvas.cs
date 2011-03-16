using System;

namespace Cell.LibGame
{



	public interface IGameCanvas
	{
		void Init();
		void Logic();
		void Render();
		//void Exit();
	};
}
