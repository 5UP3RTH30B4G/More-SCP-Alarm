package net.mcreator.scpalarm.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.scpalarm.world.inventory.AvaralarmguiMenu;
import net.mcreator.scpalarm.network.AvaralarmguiButtonMessage;
import net.mcreator.scpalarm.MoreScpAlarmMod;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class AvaralarmguiScreen extends AbstractContainerScreen<AvaralarmguiMenu> {
	private final static HashMap<String, Object> guistate = AvaralarmguiMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	Button button_go_back_to_normal;

	public AvaralarmguiScreen(AvaralarmguiMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 160;
		this.imageHeight = 106;
	}

	private static final ResourceLocation texture = new ResourceLocation("more_scp_alarm:textures/screens/avaralarmgui.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	public void containerTick() {
		super.containerTick();
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.more_scp_alarm.avaralarmgui.label_the_alarm_sound_is_set"), 25, 4, -65536, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.more_scp_alarm.avaralarmgui.label_varalarm1"), 52, 40, -16776961, false);
	}

	@Override
	public void init() {
		super.init();
		button_go_back_to_normal = Button.builder(Component.translatable("gui.more_scp_alarm.avaralarmgui.button_go_back_to_normal"), e -> {
			if (true) {
				MoreScpAlarmMod.PACKET_HANDLER.sendToServer(new AvaralarmguiButtonMessage(0, x, y, z));
				AvaralarmguiButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 25, this.topPos + 76, 110, 20).build();
		guistate.put("button:button_go_back_to_normal", button_go_back_to_normal);
		this.addRenderableWidget(button_go_back_to_normal);
	}
}
