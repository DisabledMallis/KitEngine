package com.DisabledMallis.KitEngine.IconGUI;

import org.bukkit.inventory.Inventory;

public class Page {
	private Inventory inventory;
	private int pageNum;
	public Page(Inventory i, int p) {this.inventory=i;this.pageNum=p;};
	public Inventory getInventory() {return inventory;}
	public int getPageNumber() {return pageNum;}
}
