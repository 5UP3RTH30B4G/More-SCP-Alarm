package net.theo.scp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber
public class CassieCommand {
    private static final Gson gson = new Gson();
    private static final Map<String, Float> wordDurations = new HashMap<>();
    private static final Queue<Announcement> queue = new ConcurrentLinkedQueue<>();
    private static boolean isCassiePlaying = false;
    private static Thread cassieThread = null;
    private static String currentBgName = null;

    // Logging helpers (uniform, leveled — no timestamp)
    private static void log(String level, String message) {
        System.out.println(String.format("[Cassie][%s] %s", level, message));
    }
    // word_clean map + config
    private static final Map<String, String> wordCleanMap = new HashMap<>();
    private static boolean cfgReplaceUnderscores = true;
    private static boolean cfgTitleCaseEachWord = true;
    private static boolean cfgDotSplitsDisplay = true;
    private static String cfgNumberPrefix = "number_";
    // use CassieConfig for runtime config values

    // Affiche un message dans la barre d'action d'un joueur (ou de tous)
    private static void displayActionBar(ServerPlayer player, String message) {
        if (player == null) return;
        String out = applyCassiePrefix(message);
        player.sendSystemMessage(Component.literal(out), true);
    }

    private static void sendChat(ServerPlayer player, String message) {
        if (player == null) return;
        String out = applyCassiePrefix(message);
        player.sendSystemMessage(Component.literal(out), false);
    }

    private static void sendToPlayer(ServerPlayer player, String message) {
        if (player == null) return;
        if (CassieConfig.isUseActionBar()) displayActionBar(player, message);
        else sendChat(player, message);
    }

    private static void sendToAll(Collection<ServerPlayer> players, String message) {
        if (ServerLifecycleHooks.getCurrentServer() != null) {
            for (ServerPlayer p : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
                sendToPlayer(p, message);
            }
        } else {
            for (ServerPlayer p : players) {
                sendToPlayer(p, message);
            }
        }
    }

    // Translate '&' color codes to Minecraft section sign and prepend the blue CASSIE prefix
    private static String translateColorCodes(String s) {
        if (s == null) return "";
        return s.replace('&', '\u00A7');
    }

    private static String applyCassiePrefix(String message) {
        String prefix = "\u00A79CASSIE" + "\u00A7r : ";
        String translated = translateColorCodes(message);
        return prefix + translated;
    }

    private static void logInfo(String message) { log("INFO", message); }
    private static void logWarn(String message) { log("WARN", message); }
    private static void logError(String message) { log("ERROR", message); }

    // Convert a sound key to a readable subtitle; consult exceptions map first
    private static String prettyWord(String key) {
        if (key == null) return "";
        if (wordCleanMap.containsKey(key)) return wordCleanMap.get(key);
        String s = key;
        if (s.startsWith(cfgNumberPrefix)) return s.substring(cfgNumberPrefix.length());
        if (s.contains(".") && !s.contains("_")) {
            return s.toUpperCase();
        }
        if (cfgReplaceUnderscores) s = s.replace('_', ' ');
        s = s.replace('.', ' ').trim();
        if (s.isEmpty()) return s;
        if (cfgTitleCaseEachWord) {
            String[] parts = s.split(" ");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < parts.length; i++) {
                String p = parts[i];
                if (p.length() == 0) continue;
                sb.append(p.substring(0, 1).toUpperCase());
                if (p.length() > 1) sb.append(p.substring(1));
                if (i < parts.length - 1) sb.append(' ');
            }
            return sb.toString();
        }
        return s.substring(0, 1).toUpperCase() + (s.length() > 1 ? s.substring(1) : "");
    }

    // Return segments to display; if dot-splits are enabled, split on '.'
    private static String[] getPrettySegments(String key) {
        String pretty = prettyWord(key);
        if (cfgDotSplitsDisplay && pretty.contains(".")) {
            String[] parts = pretty.split("\\.");
            List<String> out = new ArrayList<>();
            for (String p : parts) {
                String t = p.trim(); if (!t.isEmpty()) out.add(t);
            }
            return out.toArray(new String[0]);
        }
        return new String[] { pretty };
    }

    static {
        try {
            ResourceLocation fileLocation = new ResourceLocation("theo_scp", "word_durations.json");
            InputStreamReader reader = new InputStreamReader(CassieCommand.class.getResourceAsStream("/assets/theo_scp/" + fileLocation.getPath()));
            wordDurations.putAll(gson.fromJson(reader, new TypeToken<Map<String, Float>>() {}.getType()));
            reader.close();
            logInfo("Fichier des durées des mots chargé avec succès !");
        } catch (Exception e) {
            logError("Impossible de charger le fichier des durées des mots !");
            e.printStackTrace();
        }
        // Load word_exception.json (defaults + map)
        try {
            ResourceLocation exceptionLoc = new ResourceLocation("theo_scp", "word_exception.json");
            InputStreamReader r2 = new InputStreamReader(CassieCommand.class.getResourceAsStream("/assets/theo_scp/" + exceptionLoc.getPath()));
            Map<?,?> root = gson.fromJson(r2, Map.class);
            r2.close();
            if (root != null) {
                Object defaultsObj = root.get("defaults");
                if (defaultsObj instanceof Map) {
                    Map<?,?> defs = (Map<?,?>) defaultsObj;
                    Object v;
                    v = defs.get("replace_underscores"); if (v instanceof Boolean) cfgReplaceUnderscores = (Boolean) v;
                    v = defs.get("title_case_each_word"); if (v instanceof Boolean) cfgTitleCaseEachWord = (Boolean) v;
                    v = defs.get("dot_splits_display"); if (v instanceof Boolean) cfgDotSplitsDisplay = (Boolean) v;
                    v = defs.get("number_prefix"); if (v instanceof String) cfgNumberPrefix = (String) v;
                }
                Object mapObj = root.get("map");
                if (mapObj instanceof Map) {
                    Map<?,?> mmap = (Map<?,?>) mapObj;
                    for (Map.Entry<?,?> e : mmap.entrySet()) {
                        Object kk = e.getKey(); Object vv = e.getValue();
                        if (kk instanceof String && vv instanceof String) {
                            wordCleanMap.put((String) kk, (String) vv);
                        }
                    }
                }
            }
            logInfo("Fichier de nettoyage des mots (word_exception.json) chargé : " + wordCleanMap.size() + " entrées.");
        } catch (Exception ex) {
            logWarn("Impossible de charger word_exception.json : " + ex.getMessage());
        }

        // Load external config via CassieConfig helper (creates default config if missing)
        try {
            CassieConfig.load();
            logInfo("Config loaded: use_action_bar=" + CassieConfig.isUseActionBar() + ", action_bar_interval_ms=" + CassieConfig.getActionBarIntervalMs());
        } catch (Throwable t) {
            logWarn("Failed to load CassieConfig: " + t.getMessage());
        }
    }

    // Method for suggestions of available voice lines
    private static CompletableFuture<Suggestions> suggestWords(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        String input = builder.getInput();
        // Extract just the argument part (after "cassie " or "cassiesl ")
        int commandEnd = Math.max(input.indexOf("cassie ") + 7, input.indexOf("cassiesl ") + 9);
        if (commandEnd <= 7) commandEnd = input.length(); // fallback if not found
        
        String currentText = input.length() > commandEnd ? input.substring(commandEnd) : "";
        int lastSpaceInCurrent = currentText.lastIndexOf(' ');
        String prefix = lastSpaceInCurrent >= 0 ? currentText.substring(0, lastSpaceInCurrent + 1) : "";
        String lastWord = lastSpaceInCurrent >= 0 ? currentText.substring(lastSpaceInCurrent + 1) : currentText;
        
        String remaining = lastWord.toLowerCase(Locale.ROOT);
        // Suggest all available words that start with the current partial word, preserving previous words
        wordDurations.keySet().stream()
            .filter(word -> word.toLowerCase(Locale.ROOT).startsWith(remaining))
            .sorted()
            .forEach(word -> builder.suggest(prefix + word));
        return builder.buildFuture();
    }

    @SubscribeEvent
    public static void onRegisterCommand(RegisterCommandsEvent event) {
        // Command to list available voice lines
        event.getDispatcher().register(
            Commands.literal("cassiehelp")
                .executes(context -> {
                    ServerPlayer player = null;
                    if (context.getSource().getEntity() instanceof ServerPlayer) {
                        player = (ServerPlayer) context.getSource().getEntity();
                    }
                    
                    List<String> words = new ArrayList<>(wordDurations.keySet());
                    words.sort(String::compareTo);
                    
                    // Group words for display (max 50 chars per line)
                    StringBuilder sb = new StringBuilder();
                    sb.append("\u00A79=== CASSIE VOICE LINES ===\u00A7r\n");
                    int charCount = 0;
                    for (String word : words) {
                        if (charCount + word.length() + 1 > 50) {
                            sb.append("\n");
                            charCount = 0;
                        }
                        if (charCount > 0) sb.append(" ");
                        sb.append(word);
                        charCount += word.length() + 1;
                    }
                    
                    if (player != null) {
                        player.sendSystemMessage(Component.literal(sb.toString()), false);
                    }
                    logInfo("Available voice lines: " + words.size());
                    return Command.SINGLE_SUCCESS;
                })
        );

        event.getDispatcher().register(
            Commands.literal("cassie")
                .then(Commands.argument("text", StringArgumentType.greedyString())
                    .suggests(CassieCommand::suggestWords)
                    .executes(context -> {
                        String txt = StringArgumentType.getString(context, "text").toLowerCase(Locale.ROOT);
                        queue.add(new Announcement(context.getSource(), txt, true));
                        startQueueProcessor();
                        return Command.SINGLE_SUCCESS;
                    })
                )
        );

        event.getDispatcher().register(
            Commands.literal("cassiesl")
                .then(Commands.argument("text", StringArgumentType.greedyString())
                    .suggests(CassieCommand::suggestWords)
                    .executes(context -> {
                        String txt = StringArgumentType.getString(context, "text").toLowerCase(Locale.ROOT);
                        queue.add(new Announcement(context.getSource(), txt, false));
                        startQueueProcessor();
                        return Command.SINGLE_SUCCESS;
                    })
                )
        );

        event.getDispatcher().register(
            Commands.literal("cassiestop")
                .executes(context -> {
                        queue.clear();
                    stopCassie();
                    logInfo("Annonce arrêtée et file vidée.");
                    isCassiePlaying = false;
                    return Command.SINGLE_SUCCESS;
                })
        );
    }

    private static void startQueueProcessor() {
        if (!isCassiePlaying && !queue.isEmpty()) {
            Announcement announcement = queue.poll();
            isCassiePlaying = true;
            executeCassie(announcement.source, announcement.message, announcement.isCassie);
        }
    }

private static void executeCassie(CommandSourceStack source, String message, boolean isCassie) {
    Level world = source.getLevel();
    if (!(source.getEntity() instanceof ServerPlayer)) {
        // Allow execution from console/command-block: will broadcast to all players
        logInfo("Commande exécutée depuis une source non-joueur — utilisation du mode broadcast.");
    }

    final ServerPlayer[] playerRef = new ServerPlayer[] { null };
    if (source.getEntity() instanceof ServerPlayer) {
        playerRef[0] = (ServerPlayer) source.getEntity();
    }
    String[] words = message.split(" ");
    // Precompute fullPretty message (used when sending to chat once)
    StringBuilder fullPrettySb = new StringBuilder();
    for (String w : words) {
        if (w.startsWith("pitch_") || w.equals(".")) continue;
        String pw = prettyWord(w);
        if (pw == null || pw.isEmpty()) continue;
        if (fullPrettySb.length() > 0) fullPrettySb.append(' ');
        fullPrettySb.append(pw);
    }
    String fullPretty = fullPrettySb.toString();
    final float[] pitch = {1.0f};  // Valeur de pitch par défaut
    float estimatedTextDuration = 0.0f;
float currentPitch = 1.0f;

for (String word : words) {
            if (word.startsWith("pitch_")) {
        try {
            float parsedPitch = Float.parseFloat(word.substring(6));
            currentPitch = Math.max(0.5f, Math.min(parsedPitch, 2.0f));
            logInfo("Pitch ajusté à : " + currentPitch);
        } catch (NumberFormatException e) {
            logWarn("Pitch invalide. Ignoré.");
        }
    } else if (!word.equals(".")) {
        float baseDuration = wordDurations.getOrDefault(word, 1.0f);
        float adjustedDuration = baseDuration / currentPitch;
        estimatedTextDuration = adjustedDuration + estimatedTextDuration;
    logInfo("Mot : " + word + " | Durée base: " + baseDuration + "s | Pitch: " + currentPitch + " | Durée ajustée: " + adjustedDuration + "s");
    }
}

// Calcul du temps total ajusté
int textDuration = Math.round(estimatedTextDuration);
int adjustedTextDuration =   (textDuration < 20) ? textDuration + 2
                           : (textDuration < 40) ? textDuration + 3
                           : textDuration;
// Enforce minimum bg index of 4
if (adjustedTextDuration < 4) {
    logInfo("Ajustement du fond sonore à la valeur minimale de 4 (valeur calculée: " + adjustedTextDuration + ")");
    adjustedTextDuration = 4;
}
logInfo("Durée finale estimée (avec pitchs) : " + estimatedTextDuration + "s => fond sonore : bg_" + adjustedTextDuration);

    // Lecture du fond sonore si activé
    if (isCassie) {
        String bgSoundName = "bg_" + adjustedTextDuration;
        ResourceLocation bgSoundID = new ResourceLocation("theo_scp", bgSoundName);
        SoundEvent bgSound = ForgeRegistries.SOUND_EVENTS.getValue(bgSoundID);

    if (bgSound != null) {
        currentBgName = bgSoundName;
        String startDisplay = "...";

        if (source.getServer() != null) {
            Collection<ServerPlayer> players = source.getServer().getPlayerList().getPlayers();
            if (CassieConfig.isUseActionBar()) sendToAll(players, startDisplay);
            else sendToAll(players, fullPretty);
            for (ServerPlayer p : players) {
            playSound(world, p, bgSoundName, 1.0f);
            }
        }
        logInfo("Lecture du fond sonore (broadcast forcé) : " + bgSoundName);
        } else {
            logWarn("Fond sonore introuvable : " + bgSoundName);
        }
    }

    // Thread de lecture de l’annonce
    cassieThread = new Thread(() -> {
        try {
            Thread.sleep(2500); // Petite pause avant l’annonce
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (String word : words) {
                if (!isCassiePlaying) {
                logInfo("Lecture interrompue.");
                break;
            }

                // Server-side only: do not wait for client pause states

            if (word.startsWith("pitch_")) {
                try {
                    float parsedPitch = Float.parseFloat(word.substring(6));
                    parsedPitch = Math.max(0.5f, Math.min(parsedPitch, 2.0f));
                    pitch[0] = parsedPitch;
                    logInfo("Pitch ajusté à : " + pitch[0]);
                } catch (NumberFormatException e) {
                    logWarn("Pitch invalide dans la boucle. Ignoré.");
                }
                continue;
            }

                if (word.equals(".")) {
                logInfo("Pause.");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    logError("Interrupted during pause: " + e.getMessage());
                }
                continue;
            }

            float wordDuration = wordDurations.getOrDefault(word, 1.0f);
            ResourceLocation soundID = new ResourceLocation("theo_scp", word);
            SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(soundID);

            logInfo("Lecture du mot : " + word + " (Durée: " + wordDuration + "s, Pitch: " + pitch[0] + ")");

            if (sound != null) {
                if (playerRef[0] != null) {
                    playSound(world, playerRef[0], word, pitch[0]);
                } else {
                    if (source.getServer() != null) {
                        Collection<ServerPlayer> players = source.getServer().getPlayerList().getPlayers();
                        for (ServerPlayer p : players) {
                            playSound(world, p, word, pitch[0]);
                        }
                    }
                }
            }

            // Display pretty segments (split by '.') as action-bar subtitles, spaced across the word duration
            String[] segments = getPrettySegments(word);
            long totalMillis = (long) ((wordDuration / pitch[0]) * 1000);
            if (segments.length <= 1) {
                // single segment: repeatedly display the subtitle during the whole word duration
                String segText = segments.length == 0 ? "" : segments[0];
                long remaining = totalMillis;
                if (CassieConfig.isUseActionBar()) {
                    long interval = Math.max(50, CassieConfig.getActionBarIntervalMs()); // resend interval from config (min 50ms)
                    while (remaining > 0 && isCassiePlaying) {
                        if (playerRef[0] != null) {
                            sendToPlayer(playerRef[0], segText);
                        } else if (source.getServer() != null) {
                            for (ServerPlayer p : source.getServer().getPlayerList().getPlayers()) {
                                sendToPlayer(p, segText);
                            }
                        }
                        long sleep = Math.min(interval, remaining);
                        try {
                            Thread.sleep(sleep);
                        } catch (InterruptedException e) {
                            logError("Interrupted during word sleep: " + e.getMessage());
                            break;
                        }
                        remaining -= sleep;
                    }
                } else {
                    // already sent full announcement in chat at start; just wait the duration
                    try {
                        Thread.sleep(remaining);
                    } catch (InterruptedException e) {
                        logError("Interrupted during word sleep: " + e.getMessage());
                    }
                }
            } else {
                long part = Math.max(100, totalMillis / segments.length);
                for (int si = 0; si < segments.length; si++) {
                    String seg = segments[si];
                    if (!isCassiePlaying) break;
                    long remainingPart = part;
                    if (CassieConfig.isUseActionBar()) {
                        long interval = Math.max(50, CassieConfig.getActionBarIntervalMs());
                        while (remainingPart > 0 && isCassiePlaying) {
                            if (playerRef[0] != null) {
                                sendToPlayer(playerRef[0], seg);
                            } else if (source.getServer() != null) {
                                for (ServerPlayer p : source.getServer().getPlayerList().getPlayers()) {
                                    sendToPlayer(p, seg);
                                }
                            }
                            long sleep = Math.min(interval, remainingPart);
                            try {
                                Thread.sleep(sleep);
                            } catch (InterruptedException e) {
                                logError("Interrupted during segment sleep: " + e.getMessage());
                                break;
                            }
                            remainingPart -= sleep;
                        }
                    } else {
                        try {
                            Thread.sleep(remainingPart);
                        } catch (InterruptedException e) {
                            logError("Interrupted during segment sleep: " + e.getMessage());
                        }
                    }
                }
            }
        }

        isCassiePlaying = false;
        logInfo("Annonce terminée.");
        // stop background when announcement finished
        startQueueProcessor();
    });

    isCassiePlaying = true;
    cassieThread.start();
}

    private static void playSound(Level world, ServerPlayer player, String soundName, float pitch) {
        if (player == null) {
            logWarn("Impossible de jouer le son '" + soundName + "' : joueur null");
            return;
        }
        ResourceLocation soundID = new ResourceLocation("theo_scp", soundName);
        SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(soundID);
        if (sound != null) {
            world.playSound(null, player.getX(), player.getY(), player.getZ(), sound, SoundSource.VOICE, 1.0F, pitch);
        } else {
            logWarn("Son introuvable : " + soundName);
        }
    }

    private static void stopCassie() {
        if (cassieThread != null && cassieThread.isAlive()) {
            isCassiePlaying = false;
            cassieThread.interrupt();
            logInfo("Cassie thread interrompu.");
        }
        // Stop background sound for all players or specific bg if known
        try {
            Collection<ServerPlayer> players = null;
            if (ServerLifecycleHooks.getCurrentServer() != null) {
                players = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers();
            }
            if (players != null) {
                for (ServerPlayer p : players) {
                    if (currentBgName != null) {
                        logInfo("Stopping bg for player " + p.getName().getString() + " currentBgName=" + currentBgName);
                        ResourceLocation rl = new ResourceLocation("theo_scp", currentBgName);
                        p.connection.send(new ClientboundStopSoundPacket(rl, SoundSource.VOICE));
                        p.connection.send(new ClientboundStopSoundPacket(rl, SoundSource.MUSIC));
                        p.connection.send(new ClientboundStopSoundPacket(rl, SoundSource.AMBIENT));
                        p.connection.send(new ClientboundStopSoundPacket(rl, SoundSource.MASTER));
                    } else {
                        p.connection.send(new ClientboundStopSoundPacket(null, SoundSource.VOICE));
                        p.connection.send(new ClientboundStopSoundPacket(null, SoundSource.MUSIC));
                        p.connection.send(new ClientboundStopSoundPacket(null, SoundSource.AMBIENT));
                        p.connection.send(new ClientboundStopSoundPacket(null, SoundSource.MASTER));
                    }
                }
            }
        } catch (Exception e) {
            logWarn("Échec de l'envoi des paquets d'arrêt de son: " + e.getMessage());
        }
        currentBgName = null;
    }

    private static class Announcement {
        CommandSourceStack source;
        String message;
        boolean isCassie;

        public Announcement(CommandSourceStack source, String message, boolean isCassie) {
            this.source = source;
            this.message = message;
            this.isCassie = isCassie;
        }
    }
}
