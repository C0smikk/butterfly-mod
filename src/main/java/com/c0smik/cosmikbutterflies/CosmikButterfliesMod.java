package com.c0smik.cosmikbutterflies;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.BatEntity;  // Use BatEntity as a placeholder
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.server.MinecraftServer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class CosmikButterfliesMod implements ModInitializer {

    @Override
    public void onInitialize() {
        // Register the spawn butterfly command using CommandRegistrationCallback
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(
                    LiteralArgumentBuilder.<ServerCommandSource>literal("spawnbutterfly")
                            .executes(context -> {
                                ServerCommandSource source = context.getSource();
                                MinecraftServer server = source.getServer();
                                World world = server.getWorld(World.OVERWORLD);  // Use the world where the command is run
                                Vec3d posVec = source.getPosition();  // Get the position as a Vec3d

                                // Convert Vec3d to BlockPos (casting coordinates to int)
                                BlockPos pos = new BlockPos((int) posVec.x, (int) posVec.y, (int) posVec.z);

                                // Use BatEntity as a placeholder for the butterfly
                                BatEntity butterfly = new BatEntity(EntityType.BAT, world);
                                butterfly.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);

                                // Spawn the butterfly at the specified position
                                world.spawnEntity(butterfly);

                                // Send feedback to the player
                                source.sendFeedback(Text.of("Butterfly spawned!"), false);

                                return Command.SINGLE_SUCCESS;
                            })
            );
        });
    }
}
