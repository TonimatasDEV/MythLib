package dev.tonimatas.mythlib.util;

import com.google.gson.JsonArray;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.Ingredient;

public class RecipesUtil {
    public static NonNullList<Ingredient> deserializeIngredients(JsonArray json) {
        NonNullList<Ingredient> ingredients = NonNullList.create();

        for (int i = 0; i < json.size(); i++) {
            Ingredient ingredient = Ingredient.fromJson(json.get(i));

            if (!ingredient.isEmpty()) ingredients.add(ingredient);
        }

        return ingredients;
    }
}
