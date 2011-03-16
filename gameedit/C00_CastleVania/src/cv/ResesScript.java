package cv;

import com.cell.IImages;
import com.cell.game.CMap;
import com.cell.game.CSprite;


public interface ResesScript {

	public IImages 	createImages(String key);
	public CMap 	createMap(String key, IImages tiles, boolean isAnimate, boolean isCyc);
	public CSprite 	createSprite(String key, IImages tiles);
		
	public void 	initWorld(String name,LevelManager level);

}
