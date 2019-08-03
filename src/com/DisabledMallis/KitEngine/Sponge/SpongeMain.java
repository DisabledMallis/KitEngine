package com.DisabledMallis.KitEngine.Sponge;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import com.DisabledMallis.HybridLib.Hybrid;
import com.DisabledMallis.KitEngine.Log;

@Plugin(id = "kitengine", name = "KitEngine", version = "1.4.1", description = "An open sourced kit plugin")
public class SpongeMain {
	@Listener
	public void onServerStart(GameStartedServerEvent e) {
		new Log("Sponge support is unfinished");
		Hybrid.enableLib();
	}
}
