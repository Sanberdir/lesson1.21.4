package ru.sanberdir.lesson1_21_4.items.entity.client;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.AbstractBoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.AbstractBoat;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;
import ru.sanberdir.lesson1_21_4.items.entity.ModBoatEntityUsual;
import ru.sanberdir.lesson1_21_4.items.entity.ModChestBoatEntityUsual;

import java.util.Map;
import java.util.stream.Stream;

public class ModUsualBoatRenderer extends AbstractBoatRenderer {

    private final Map<ModBoatEntityUsual.Type, ResourceLocation> textures;
    private final Map<ModBoatEntityUsual.Type, EntityModel<BoatRenderState>> models;
    private final boolean chestBoat;

    private ModBoatEntityUsual.Type currentType = ModBoatEntityUsual.Type.USUAL;

    public ModUsualBoatRenderer(EntityRendererProvider.Context context, boolean chestBoat) {
        super(context);
        this.chestBoat = chestBoat;

        this.textures = Stream.of(ModBoatEntityUsual.Type.values())
                .collect(ImmutableMap.toImmutableMap(
                        type -> type,
                        type -> ResourceLocation.fromNamespaceAndPath(
                                Lesson1_21_4.MODID,
                                chestBoat
                                        ? "textures/entity/chest_boat/" + type.getName() + ".png"
                                        : "textures/entity/boat/" + type.getName() + ".png"
                        )
                ));

        this.models = Stream.of(ModBoatEntityUsual.Type.values())
                .collect(ImmutableMap.toImmutableMap(
                        type -> type,
                        type -> {
                            ModelLayerLocation layer = chestBoat
                                    ? createChestBoatModelName(type)
                                    : createBoatModelName(type);
                            // В 1.21.4 оба типа лодок используют BoatModel
                            return new BoatModel(context.bakeLayer(layer));
                        }
                ));
    }

    @Override
    public void extractRenderState(AbstractBoat boat, BoatRenderState state, float partialTick) {
        super.extractRenderState(boat, state, partialTick);
        if (boat instanceof ModBoatEntityUsual modBoat) {
            currentType = modBoat.getModVariant();
        } else if (boat instanceof ModChestBoatEntityUsual modChestBoat) {
            currentType = modChestBoat.getModVariant();
        }
    }

    @Override
    protected EntityModel<BoatRenderState> model() {
        return models.getOrDefault(currentType, models.values().iterator().next());
    }

    @Override
    protected RenderType renderType() {
        ResourceLocation texture = textures.getOrDefault(currentType,
                textures.values().iterator().next());
        return model().renderType(texture);
    }

    public static ModelLayerLocation createBoatModelName(ModBoatEntityUsual.Type type) {
        return new ModelLayerLocation(
                ResourceLocation.fromNamespaceAndPath(Lesson1_21_4.MODID, "boat/" + type.getName()), "main");
    }

    public static ModelLayerLocation createChestBoatModelName(ModBoatEntityUsual.Type type) {
        return new ModelLayerLocation(
                ResourceLocation.fromNamespaceAndPath(Lesson1_21_4.MODID, "chest_boat/" + type.getName()), "main");
    }
}