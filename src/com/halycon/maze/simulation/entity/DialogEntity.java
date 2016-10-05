package com.halycon.maze.simulation.entity;

import com.halycon.maze.core.Vector;
import com.halycon.maze.simulation.dialog.Dialog;

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
