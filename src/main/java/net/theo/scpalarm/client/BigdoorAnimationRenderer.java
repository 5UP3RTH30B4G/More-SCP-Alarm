package net.theo.scpalarm.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.theo.scpalarm.block.NewdoorBlock;
import net.theo.scpalarm.network.BigdoorAnimationMessage;
import net.theo.scpalarm.init.MoreScpAlarmModBlocks;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "more_scp_alarm", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class BigdoorAnimationRenderer {
    private static final List<Animation> ANIMATIONS = new ArrayList<>();

    private BigdoorAnimationRenderer() {
    }

    public static void start(BigdoorAnimationMessage message) {
        ANIMATIONS.removeIf(animation -> animation.base().equals(message.base()));
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level != null) {
            ANIMATIONS.add(new Animation(message, minecraft.level.getGameTime()));
        }
    }

    public static boolean isAnimating(BlockPos pos) {
        removeFinishedAnimations();
        for (Animation animation : ANIMATIONS) {
            if (animation.contains(pos)) {
                return true;
            }
        }
        return false;
    }

    @SubscribeEvent
    public static void render(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS) {
            return;
        }
        removeFinishedAnimations();
        if (ANIMATIONS.isEmpty()) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            return;
        }
        BlockRenderDispatcher dispatcher = minecraft.getBlockRenderer();
        MultiBufferSource.BufferSource buffers = minecraft.renderBuffers().bufferSource();
        Vec3 camera = event.getCamera().getPosition();
        PoseStack poseStack = event.getPoseStack();

        for (Animation animation : ANIMATIONS) {
            float progress = animation.progress(minecraft.level.getGameTime(), event.getPartialTick());
            float slide = animation.fromSlide() + (animation.targetSlide() - animation.fromSlide()) * progress;
            Direction right = animation.facing().getCounterClockWise();
            double x = animation.base().getX() + right.getStepX() * slide - camera.x;
            double y = animation.base().getY() - camera.y;
            double z = animation.base().getZ() + right.getStepZ() * slide - camera.z;
            BlockState state = animation.renderState();
                BlockPos lightPosition = BlockPos.containing(
                    animation.base().getX() + right.getStepX() * slide,
                    animation.base().getY(),
                    animation.base().getZ() + right.getStepZ() * slide);

            poseStack.pushPose();
            poseStack.translate(x, y, z);
                dispatcher.renderSingleBlock(state, poseStack, buffers,
                    LevelRenderer.getLightColor(minecraft.level, lightPosition), OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
        buffers.endBatch();
    }

    private static void removeFinishedAnimations() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            ANIMATIONS.clear();
            return;
        }
        Iterator<Animation> iterator = ANIMATIONS.iterator();
        while (iterator.hasNext()) {
            Animation animation = iterator.next();
            if (animation.progress(minecraft.level.getGameTime(), 0) >= 1.0f) {
                iterator.remove();
            }
        }
    }

    private record Animation(BigdoorAnimationMessage message, long startTime) {
        private BlockPos base() {
            return message.base();
        }

        private Direction facing() {
            return message.facing();
        }

        private int fromSlide() {
            return message.fromSlide();
        }

        private int targetSlide() {
            return message.targetSlide();
        }

        private float progress(long gameTime, float partialTick) {
            float linear = Math.min(1.0f, Math.max(0.0f, (gameTime - startTime + partialTick) / message.duration()));
            return linear * linear * (3.0f - 2.0f * linear);
        }

        private boolean contains(BlockPos pos) {
            Direction right = facing().getCounterClockWise();
            int fromX = base().getX() + right.getStepX() * fromSlide();
            int fromZ = base().getZ() + right.getStepZ() * fromSlide();
            int targetX = base().getX() + right.getStepX() * targetSlide();
            int targetZ = base().getZ() + right.getStepZ() * targetSlide();
            return pos.getY() == base().getY() && ((pos.getX() == fromX && pos.getZ() == fromZ) || (pos.getX() == targetX && pos.getZ() == targetZ));
        }

        private BlockPos renderPosition() {
            Direction right = facing().getCounterClockWise();
            return base().relative(right, Math.round(fromSlide()));
        }

        private BlockState renderState() {
            return MoreScpAlarmModBlocks.NEWDOOR.get().defaultBlockState()
                    .setValue(NewdoorBlock.FACING, facing())
                    .setValue(NewdoorBlock.ANIMATING, false);
        }
    }
}
