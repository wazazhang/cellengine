package com.g2d.studio.scene.editor;

import java.io.File;

import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.quest.ability.QuestAccepter;
import com.cell.rpg.quest.ability.QuestPublisher;
import com.cell.rpg.quest.ability.QuestTrigger;
import com.g2d.editor.DisplayObjectEditor;
import com.g2d.game.rpg.Unit;
import com.g2d.studio.Studio;
import com.g2d.studio.quest.QuestNode;
import com.g2d.studio.res.Res;
import com.g2d.studio.rpg.AbilityPanel;
import com.g2d.studio.rpg.RPGObjectPanel;
import com.g2d.studio.rpg.AbilityPanel.AbilityCellEditAdapter;
import com.g2d.studio.scene.script.TriggerGeneratorPanel;
import com.g2d.studio.scene.script.TriggersEditor;
import com.g2d.studio.scene.units.SceneUnitTag;

@SuppressWarnings("serial")
public class SceneUnitTagEditor extends DisplayObjectEditor<Unit>
{
	private static final long serialVersionUID = 1L;

	TriggerGeneratorPanel triggers_editor;
	
	public SceneUnitTagEditor(
			SceneEditor se,
			SceneUnitTag<?> unit, 
			AbilityCellEditAdapter<?> ... adapters)
	{
		super(unit.getGameUnit(),
				new RPGObjectPanel(unit.getUnit()), 
				new AbilityPanel(unit.getUnit(), adapters){
			@Override
			protected String getListAbilityText(AbstractAbility ability) {
				if (ability instanceof QuestTrigger) {
					String text = AbstractAbility.getEditName(ability.getClass());
					int qid = ((QuestTrigger) ability).quest_id;
					QuestNode qn = Studio.getInstance().getQuestManager().getQuest(qid);
					if (ability instanceof QuestAccepter) {
						text += "<!>";
					}	
					else if (ability instanceof QuestPublisher) {
						text += "<?>";
					}
					if (qn != null) {
						return text + " : " + qn.getListName();
					} else {
						return text + " : unknow("+qid+")";
					}
				} else {
					return super.getListAbilityText(ability);
				}
			}
		});
		super.setSize(800, 500);
		this.setIconImage(Res.icon_edit);
		
		this.triggers_editor = new TriggerGeneratorPanel(
				unit.getUnit().getBindedTriggers(),
				se.getSceneNode().getData().getTriggersPackage(),
				unit.getUnit().getTriggerObjectType());
		this.table.addTab("事件触发器", this.triggers_editor);
	}
}
