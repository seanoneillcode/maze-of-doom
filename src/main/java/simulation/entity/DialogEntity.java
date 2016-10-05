package simulation.entity;

import core.Vector;
import simulation.dialog.Dialog;

public class DialogEntity extends Entity {

	private Dialog dialog;
	
	public DialogEntity(Vector position, Vector size, Dialog dialog) {
		super(position, size);
		this.dialog = dialog;
	}
	
	public DialogEntity(DialogEntity dialogEntity) {
		super(dialogEntity.getPosition(), dialogEntity.getSize());
		this.dialog = dialogEntity.getDialog();
	}
	
	public Dialog getDialog() {
		return dialog;
	}
}
