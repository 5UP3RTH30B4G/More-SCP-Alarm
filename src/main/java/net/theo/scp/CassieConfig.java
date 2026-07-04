package net.theo.scp;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class CassieConfig {
	private static final Gson gson = new Gson();
	private static boolean useActionBar = true;
	private static int actionBarIntervalMs = 150;

	public static void load() {
		try {
			File cfgFile = new File("config/cassie.json");
			if (cfgFile.exists()) {
				try (FileReader fr = new FileReader(cfgFile)) {
					Map<?, ?> root = gson.fromJson(fr, Map.class);
					if (root != null) {
						Object v = root.get("use_action_bar");
						if (v instanceof Boolean) useActionBar = (Boolean) v;
						Object iv = root.get("action_bar_interval_ms");
						if (iv instanceof Number) actionBarIntervalMs = ((Number) iv).intValue();
						else if (iv instanceof String) {
							try { actionBarIntervalMs = Integer.parseInt((String) iv); } catch (Exception ex) {}
						}
					}
				}
			} else {
				// Attempt to copy a bundled resource named /assets/cassie/cassie.json into config/cassie.json
				java.io.InputStream res = CassieConfig.class.getResourceAsStream("/assets/theo_scp/cassie.json");
				if (res != null) {
					File cfgDir = new File("config");
					if (!cfgDir.exists()) cfgDir.mkdirs();
					try (java.io.FileOutputStream fos = new java.io.FileOutputStream(cfgFile)) {
						byte[] buf = new byte[4096];
						int r;
						while ((r = res.read(buf)) != -1) fos.write(buf, 0, r);
						fos.flush();
					} catch (Exception ex) {
						System.out.println("[Cassie][WARN] Failed to copy bundled cassie.json: " + ex.getMessage());
					} finally {
						try { res.close(); } catch (Exception ignore) {}
					}
				} else {
					// create config dir and default file as fallback
					File cfgDir = new File("config");
					if (!cfgDir.exists()) cfgDir.mkdirs();
					Map<String, Object> defaults = new HashMap<>();
					defaults.put("use_action_bar", Boolean.valueOf(false));
					defaults.put("action_bar_interval_ms", Integer.valueOf(actionBarIntervalMs));
					try (FileWriter fw = new FileWriter(cfgFile)) {
						gson.toJson(defaults, fw);
						fw.flush();
					}
				}
			}
		} catch (Exception e) {
			// If anything goes wrong, keep defaults
			System.out.println("[Cassie][WARN] Failed to load/create config/cassie.json: " + e.getMessage());
		}
	}

	public static boolean isUseActionBar() {
		return useActionBar;
	}

	public static int getActionBarIntervalMs() {
		return actionBarIntervalMs;
	}
}
