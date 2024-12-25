package me.avenhon.maycryVillagerSystem.listeners;

import me.avenhon.maycryVillagerSystem.services.VillagerService;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class VillagerListener implements Listener {
    private final VillagerService villagerService;

    public VillagerListener(VillagerService villagerService) {
        this.villagerService = villagerService;
    }

    @EventHandler
    public void onPlayerInteractWithVillager(PlayerInteractAtEntityEvent e) {
        if (!(e.getRightClicked() instanceof Villager)) return;

        Villager villager = (Villager) e.getRightClicked();
        villagerService.updateVillagerRecipes(villager);
    }
}
