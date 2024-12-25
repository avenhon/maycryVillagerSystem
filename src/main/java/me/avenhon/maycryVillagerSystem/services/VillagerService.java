package me.avenhon.maycryVillagerSystem.services;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import java.util.ArrayList;
import java.util.List;

public class VillagerService {
    public void updateVillagerRecipes(Villager villager) {
        List<MerchantRecipe> recipes = new ArrayList<MerchantRecipe>(villager.getRecipes());

        List<MerchantRecipe> updatedRecipes = new ArrayList<>();

        for (MerchantRecipe recipe : recipes) {
            ItemStack result = recipe.getResult();

            if (!recipe.getIngredients().getFirst().getType().equals(Material.EMERALD_BLOCK) &&
                    (result.getType().toString().contains("DIAMOND") || result.getType().equals(Material.ENCHANTED_BOOK)) &&
                    result.hasItemMeta() && (result.getItemMeta().hasEnchants() || hasStoredEnchantments(result))) {

                updatedRecipes.add(createCustomRecipe(result, recipe));
            } else {
                updatedRecipes.add(recipe);
            }
        }

        villager.setRecipes(updatedRecipes);
    }

    private MerchantRecipe createCustomRecipe(ItemStack result, MerchantRecipe originalRecipe) {
        final EnchantmentStorageMeta im = (EnchantmentStorageMeta) result.getItemMeta();
        ItemStack emeraldBlock;

        if (im.hasStoredEnchant(Enchantment.MENDING)) {
            emeraldBlock = new ItemStack(Material.EMERALD_BLOCK, Math.max(16, originalRecipe.getIngredients().getFirst().getAmount() / 3));
        } else {
            emeraldBlock = new ItemStack(Material.EMERALD_BLOCK, Math.max(1, originalRecipe.getIngredients().getFirst().getAmount() / 3));
        }

        List<ItemStack> ingredients = new ArrayList<>();
        ingredients.add(emeraldBlock);

        if (!originalRecipe.getIngredients().getLast().equals(originalRecipe.getIngredients().getFirst())) {
            ingredients.add(originalRecipe.getIngredients().getLast());
        }

        MerchantRecipe newRecipe = new MerchantRecipe(result, 10);
        newRecipe.setIngredients(ingredients);
        newRecipe.setExperienceReward(true);
        return newRecipe;
    }

    private boolean hasStoredEnchantments(ItemStack item) {
        if (item.getType().equals(Material.ENCHANTED_BOOK)) {
            if (item.getItemMeta() instanceof EnchantmentStorageMeta meta) {
                return !meta.getStoredEnchants().isEmpty();
            }
        }
        return false;
    }
}
