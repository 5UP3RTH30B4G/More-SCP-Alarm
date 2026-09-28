package net.theo.scpalarm.client.gui;

import net.theo.scpalarm.world.inventory.AlarmGUIMenu;
import net.theo.scpalarm.network.MoreScpAlarmModVariables;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.util.Mth;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;

public class AlarmGUIScreen extends AbstractContainerScreen<AlarmGUIMenu> {
	private final static HashMap<String, Object> guistate = AlarmGUIMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private final List<ResourceLocation> sounds = AlarmGUIMenu.getAvailableSounds();
	private int scrollOffset;
	private boolean globalMode;
	private boolean draggingScrollbar;
	private Button modeButton;
	private final List<Button> soundButtons = new ArrayList<>();
	private String selectedSound;
	private static final int LIST_X = 12;
	private static final int LIST_Y = 43;
	private static final int LIST_WIDTH = 270;
	private static final int LIST_HEIGHT = 122;
	private static final int ROW_HEIGHT = 12;

	public AlarmGUIScreen(AlarmGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.globalMode = container.isGlobalMode();
		this.selectedSound = container.getSelectedAlarmSound();
		this.imageWidth = 300;
		this.imageHeight = 200;
	}

	private static final ResourceLocation texture = new ResourceLocation("more_scp_alarm:textures/screens/alarm_gui.png");

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
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, "ALARM SOUND", 12, 8, 0x404040, false);
		guiGraphics.drawString(this.font, this.selectedSound.isEmpty() ? "Selected: none" : "Selected: " + soundName(this.selectedSound), 12, 25, 0x404040, false);
		int visibleRows = LIST_HEIGHT / ROW_HEIGHT;
		int maxOffset = Math.max(0, this.sounds.size() - visibleRows);
		int trackX = LIST_X + LIST_WIDTH - 7;
		guiGraphics.fill(trackX, LIST_Y, trackX + 5, LIST_Y + LIST_HEIGHT, 0xFF555555);
		int thumbHeight = this.sounds.isEmpty() ? LIST_HEIGHT : Math.max(18, LIST_HEIGHT * visibleRows / this.sounds.size());
		int thumbTravel = LIST_HEIGHT - thumbHeight;
		int thumbY = LIST_Y + (maxOffset == 0 ? 0 : thumbTravel * this.scrollOffset / maxOffset);
		guiGraphics.fill(trackX, thumbY, trackX + 5, thumbY + thumbHeight, this.globalMode ? 0xFF777777 : 0xFFDDDDDD);
	}

	@Override
	public void init() {
		super.init();
		this.modeButton = Button.builder(Component.literal(this.globalMode ? "Mode: GLOBAL" : "Mode: LOCAL"), button -> {
			this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 0);
			this.globalMode = !this.globalMode;
			button.setMessage(Component.literal(this.globalMode ? "Mode: GLOBAL" : "Mode: LOCAL"));
			this.refreshSoundButtons();
		}).bounds(this.leftPos + 12, this.topPos + 174, 110, 20).build();
		this.addRenderableWidget(this.modeButton);
		this.refreshSoundButtons();
	}

	private void refreshSoundButtons() {
		for (Button button : this.soundButtons)
			this.removeWidget(button);
		this.soundButtons.clear();
		int visibleRows = LIST_HEIGHT / ROW_HEIGHT;
		int end = Math.min(this.sounds.size(), this.scrollOffset + visibleRows);
		for (int index = this.scrollOffset; index < end; index++) {
			ResourceLocation sound = this.sounds.get(index);
			int rowY = LIST_Y + (index - this.scrollOffset) * ROW_HEIGHT;
			String label = soundName(sound.toString());
			label = this.font.plainSubstrByWidth(label, LIST_WIDTH - 18);
			int soundIndex = index;
			Button soundButton = Button.builder(Component.literal(label), button -> {
				if (!this.globalMode) {
					this.selectedSound = this.sounds.get(soundIndex).toString();
					this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, soundIndex + 1);
					this.debug("Clic son index=" + soundIndex + ": " + this.selectedSound);
				}
			}).bounds(this.leftPos + LIST_X, this.topPos + rowY, LIST_WIDTH - 10, ROW_HEIGHT).build();
			soundButton.active = !this.globalMode;
			this.soundButtons.add(soundButton);
			this.addRenderableWidget(soundButton);
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (super.mouseClicked(mouseX, mouseY, button))
			return true;
		int listX = this.leftPos + LIST_X;
		int listY = this.topPos + LIST_Y;
		if (button == 0 && mouseX >= listX && mouseX < listX + LIST_WIDTH && mouseY >= listY && mouseY < listY + LIST_HEIGHT) {
			if (mouseX >= listX + LIST_WIDTH - 8) {
				this.draggingScrollbar = true;
				this.setScrollFromMouse(mouseY);
				this.refreshSoundButtons();
				this.debug("Debut du deplacement de l'ascenseur");
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
		if (this.draggingScrollbar) {
			this.setScrollFromMouse(mouseY);
			this.refreshSoundButtons();
			return true;
		}
		return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		this.draggingScrollbar = false;
		return super.mouseReleased(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
		if (mouseX >= this.leftPos + LIST_X && mouseX < this.leftPos + LIST_X + LIST_WIDTH
				&& mouseY >= this.topPos + LIST_Y && mouseY < this.topPos + LIST_Y + LIST_HEIGHT) {
			this.scrollOffset = Mth.clamp(this.scrollOffset - (int) Math.signum(delta), 0, Math.max(0, this.sounds.size() - LIST_HEIGHT / ROW_HEIGHT));
			this.refreshSoundButtons();
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
