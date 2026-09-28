package net.theo.scpalarm.client.gui;

import net.theo.scpalarm.world.inventory.AlarmGlobalPanelMenu;
import net.theo.scpalarm.world.inventory.AlarmGUIMenu;
import net.theo.scpalarm.network.MoreScpAlarmModVariables;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.util.Mth;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;

public class AlarmGlobalPanelScreen extends AbstractContainerScreen<AlarmGlobalPanelMenu> {
	private final List<ResourceLocation> sounds = AlarmGUIMenu.getAvailableSounds();
	private int scrollOffset;
	private SoundScrollBar scrollBar;
	private final List<Button> soundButtons = new ArrayList<>();
	private String selectedSound;
	private int renderedScrollOffset = -1;
	private static final int LIST_X = 12;
	private static final int LIST_Y = 43;
	private static final int LIST_WIDTH = 270;
	private static final int LIST_HEIGHT = 122;
	private static final int ROW_HEIGHT = 12;

	public AlarmGlobalPanelScreen(AlarmGlobalPanelMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.selectedSound = container.getGlobalAlarmSound();
		this.imageWidth = 300;
		this.imageHeight = 200;
	}

	private static final ResourceLocation texture = new ResourceLocation("more_scp_alarm:textures/screens/alarm_gui.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
		if (this.renderedScrollOffset != this.scrollOffset)
			this.refreshSoundButtons();
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
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, "GLOBAL ALARM SOUND", 12, 8, 0x202020, false);
		guiGraphics.drawString(this.font, this.selectedSound.isEmpty() ? "Selected: none" : "Selected: " + soundName(this.selectedSound), 12, 25, 0x404040, false);
	}

	@Override
	public void init() {
		super.init();
		this.scrollBar = new SoundScrollBar(this.leftPos + LIST_X + LIST_WIDTH - 8, this.topPos + LIST_Y);
		this.addRenderableWidget(this.scrollBar);
		this.addRenderableWidget(Button.builder(Component.literal("Test global sound"), button -> {
			this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, this.sounds.size());
			this.debug("Test du son global: " + this.selectedSound);
		}).bounds(this.leftPos + 12, this.topPos + 174, 150, 20).build());
		this.refreshSoundButtons();
	}

	private void refreshSoundButtons() {
		for (Button button : this.soundButtons)
			this.removeWidget(button);
		this.soundButtons.clear();
		this.renderedScrollOffset = this.scrollOffset;
		int visibleRows = LIST_HEIGHT / ROW_HEIGHT;
		int end = Math.min(this.sounds.size(), this.scrollOffset + visibleRows);
		for (int index = this.scrollOffset; index < end; index++) {
			ResourceLocation sound = this.sounds.get(index);
			int rowY = LIST_Y + (index - this.scrollOffset) * ROW_HEIGHT;
			String label = soundName(sound.toString());
			label = this.font.plainSubstrByWidth(label, LIST_WIDTH - 18);
			int soundIndex = index;
			Button soundButton = Button.builder(Component.literal(label), button -> {
				this.selectedSound = this.sounds.get(soundIndex).toString();
				this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, soundIndex);
				this.debug("Clic son global index=" + soundIndex + ": " + this.selectedSound);
			}).bounds(this.leftPos + LIST_X, this.topPos + rowY, LIST_WIDTH - 10, ROW_HEIGHT).build();
			this.soundButtons.add(soundButton);
			this.addRenderableWidget(soundButton);
		}
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
		if (mouseX >= this.leftPos + LIST_X && mouseX < this.leftPos + LIST_X + LIST_WIDTH
				&& mouseY >= this.topPos + LIST_Y && mouseY < this.topPos + LIST_Y + LIST_HEIGHT) {
			this.scrollOffset = Mth.clamp(this.scrollOffset - (int) Math.signum(delta), 0, Math.max(0, this.sounds.size() - LIST_HEIGHT / ROW_HEIGHT));
			return true;
		}
		return super.mouseScrolled(mouseX, mouseY, delta);
	}

	private void setScrollFromMouse(double mouseY) {
		int visibleRows = LIST_HEIGHT / ROW_HEIGHT;
		int maxOffset = Math.max(0, this.sounds.size() - visibleRows);
		int thumbHeight = this.sounds.isEmpty() ? LIST_HEIGHT : Math.max(18, LIST_HEIGHT * visibleRows / this.sounds.size());
		int thumbTravel = LIST_HEIGHT - thumbHeight;
		if (maxOffset > 0 && thumbTravel > 0) {
			double trackY = this.topPos + LIST_Y;
			this.scrollOffset = Mth.clamp((int) ((mouseY - trackY - thumbHeight / 2.0) * maxOffset / thumbTravel), 0, maxOffset);
		}
	}

	private class SoundScrollBar extends AbstractWidget {
		private SoundScrollBar(int x, int y) {
			super(x, y, 8, LIST_HEIGHT, Component.empty());
		}

		@Override
		protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
			int visibleRows = LIST_HEIGHT / ROW_HEIGHT;
			int maxOffset = Math.max(0, sounds.size() - visibleRows);
			int thumbHeight = sounds.isEmpty() ? LIST_HEIGHT : Math.max(18, LIST_HEIGHT * visibleRows / sounds.size());
			int thumbTravel = LIST_HEIGHT - thumbHeight;
			int thumbY = maxOffset == 0 ? 0 : thumbTravel * scrollOffset / maxOffset;
			guiGraphics.fill(this.getX() + 1, this.getY(), this.getX() + 6, this.getY() + LIST_HEIGHT, 0xFF555555);
			guiGraphics.fill(this.getX() + 1, this.getY() + thumbY, this.getX() + 6, this.getY() + thumbY + thumbHeight,
					0xFFDDDDDD);
		}

		@Override
		public void onClick(double mouseX, double mouseY) {
			setScrollFromMouse(mouseY);
			debug("Ascenseur: position=" + scrollOffset);
		}

		@Override
		protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
			setScrollFromMouse(mouseY);
		}

		@Override
		protected void updateWidgetNarration(NarrationElementOutput narration) {
			this.defaultButtonNarrationText(narration);
		}
	}

	private void debug(String message) {
		if (MoreScpAlarmModVariables.debug && this.minecraft.player != null)
			this.minecraft.player.displayClientMessage(Component.literal("[Alarm debug] " + message), false);
	}

	private static String soundName(String soundId) {
		int separator = soundId.indexOf(':');
		String path = separator >= 0 ? soundId.substring(separator + 1) : soundId;
		return path.replace('_', ' ').replace('/', ' ');
	}
}
