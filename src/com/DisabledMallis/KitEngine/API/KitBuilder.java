package com.DisabledMallis.KitEngine.API;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

/*
 * Kit builder
 * Make kits programatically & safely
 */
public class KitBuilder {
	ArrayList<ItemStack> ali = new ArrayList<>();
	String name;
	public KitBuilder() {
	}
	public KitBuilder(String name) {
		this.name = name;
	}
	public KitBuilder setName(String name) {
		this.name = name;
		return this;
	}
	public KitBuilder add(ItemStack i) {
		ali.add(i);
		return this;
	}
	public KitBuilder addAll(ArrayList<ItemStack> i) {
		ali.addAll(i);
		return this;
	}
	public Kit build() {
		Kit r = new Kit(name);
		r.saveContents(ali);
		return r;
	}
}
